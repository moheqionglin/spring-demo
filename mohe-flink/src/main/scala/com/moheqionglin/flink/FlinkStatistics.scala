package com.moheqionglin.flink

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneOffset}
import java.util.Properties

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.state.MapStateDescriptor
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}
import org.apache.flink.streaming.api.datastream.BroadcastStream
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.flink.util.Collector

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * //近半分钟 并发链接数 是不是用connectionID更合适？
 * lx-core -> kafka[Point] -> flinkSource ->  timeWindow(30S) -> distinct -> count(eid)
 *
 * //近一分钟 GGA在线数
 *
 * lx-core -> kafka[Point] -> flinkSource ->  timeWindow(30S) -> distinct -> count(point)
 *
 * */
object FlinkStatistics {


  var os: ZoneOffset = ZoneOffset.ofHours(8)
  var interval = 60
  val FLINK_CONFIG_PREFIX = "P2G_"
  val CASTER_PRODUCT_NAME = "CASTER";
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  case class InitParams(ruleId: Long, tableName: String, consumerTopic: String, consumerBrokers: String, consumerGroup: String, productTopic: String, productBrokers: String, configTopic: String, configConsumerGroup: String, product2GidMap: mutable.Map[String, Long])
  case class SimplePoint(productName: String, eid: Long, gnssTime: Long, gid: Long)

  var initParams: InitParams = _

  def initParams(args: Array[String]): Unit = {
    if(args.length != 10){
      println("flink-1.8.0/bin/flink run -p 3 -c com.moheqionglin.flink.FlinkStatistics  flink-job/flinek-1.0-SNAPSHOT.jar ruleId reportPGTableName consumerTopic consumerBrokers consumerGroup productTopic productBrokers configTopic configConsumerGroup productGroupIdConfig")
      throw new RuntimeException("参数不正确")
    }
    val product2GidMap: mutable.HashMap[String, Long] = new mutable.HashMap[String, Long]();

    val product2GidMapTmp = args(9).split(",").map(str => (str.split(":")(0), str.split(":")(1).toLong)).toMap[String, Long];
    for((k, v) <- product2GidMapTmp){
      product2GidMap.put(k, v)
    }
    initParams = InitParams(args(0).toLong, args(1), args(2), args(3), args(4), args(5), args(6), args(7), args(8), product2GidMap)
  }

  def bizProductName(p: FlinkInputBean): String = {
    if(p.getProduct != null){
      p.getProduct
    }else{
      "_EMPTY_"
    }
  }

  def main(args: Array[String]): Unit = {
    initParams(args)

    val jobName = "moheqionglin statistics point 1m"
    println(s"Start Job $jobName , ruleId = ${initParams.ruleId}, " +
      s"tableName=${initParams.tableName}, consumerTopic=${initParams.consumerTopic}, " +
      s"consumerBrokers=${initParams.consumerBrokers}, consumerGroup=${initParams.consumerGroup}, " +
      s"productTopic=${initParams.productTopic}, productBrokers=${initParams.productBrokers}, " +
      s"configTopic=${initParams.configTopic}, configConsumerGroup=${initParams.configConsumerGroup}, " +
      s"product2GroupIdMap=${initParams.product2GidMap}")

    //kafka
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val pointKafkaConsumer = flinkKafkaPointConsumer(initParams.consumerTopic, initParams.consumerBrokers, initParams.consumerGroup)
    val configKafkaConsumer = flinkKafkaConfigConsumer(initParams.configTopic, initParams.consumerBrokers, initParams.configConsumerGroup)

    //proess time field
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //checkpoint
    env.enableCheckpointing(interval * 1000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)
    env.getCheckpointConfig.enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)


    //set global configuration
    val parMap = new java.util.HashMap[String, String]()
    parMap.put("ruleId", initParams.ruleId.toString)
    parMap.put("tableName", initParams.tableName)
    for((pname, gid) <- initParams.product2GidMap){
      parMap.put(FLINK_CONFIG_PREFIX+pname, gid.toString)
    }

    env.getConfig.setGlobalJobParameters(ParameterTool.fromMap(parMap))

    val mapStateDes = new MapStateDescriptor[String, mutable.Set[Long]]("filterGidsState", createTypeInformation[String], createTypeInformation[mutable.Set[Long]])

    //config stream
    //    val socketStream: DataStream[LocationxFlinkConfig] =
    val socketStream: BroadcastStream[LocationxFlinkConfig] =
    env.addSource(configKafkaConsumer)
      .name(jobName)
      .filter(c => jobName.equals(c.jodName))
      .broadcast(mapStateDes)
    //      .broadcast

    //data stream
    val dataStream : DataStream[SimplePoint] = env
      .addSource(pointKafkaConsumer).name("point-kafka-datasource")
      .map(p => new SimplePoint(p.getProduct, p.getEntityId.longValue(), p.getGnssTime, p.getGroupId.longValue()))


    //connection stream
    val eidCountStream: DataStream[String] =
      dataStream
        .connect(socketStream)
        .process(new GidFilterRichCoProcess(mapStateDes))
        .assignTimestampsAndWatermarks(new GnssTimeExtractor)
        .keyBy(_.gid)
        .timeWindow(Time.seconds(interval))
        .process(new EidCountProcess())

    eidCountStream.addSink(flinkKafkaProducer(initParams.productTopic, initParams.productBrokers))
    env.execute(jobName)
  }


  class GidFilterRichCoProcess(msd: MapStateDescriptor[String, mutable.Set[Long]]) extends BroadcastProcessFunction[SimplePoint, LocationxFlinkConfig, SimplePoint](){
    var mapStateDescriptor = msd
    var cnt: Byte = 0;
    val productName2GidMap: mutable.HashMap[String, Long] = new mutable.HashMap[String, Long]();

    override def processElement(value: SimplePoint, ctx: BroadcastProcessFunction[SimplePoint, LocationxFlinkConfig, SimplePoint]#ReadOnlyContext, out: Collector[SimplePoint]): Unit = {
      val gidsState = ctx.getBroadcastState(mapStateDescriptor).get("gids")
      if(CASTER_PRODUCT_NAME.equalsIgnoreCase(value.productName) && productName2GidMap.contains(CASTER_PRODUCT_NAME)){
        //save network
        out.collect(new SimplePoint(value.productName, value.eid, value.gnssTime, productName2GidMap.get(CASTER_PRODUCT_NAME).get))
      }

      if(gidsState != null && gidsState.contains(value.gid.toLong)){
        cnt = (cnt + 1).toByte
        if(cnt == 0){
          print("-")
        }
        out.collect(value)
      }
    }

    override def processBroadcastElement(value: LocationxFlinkConfig, ctx: BroadcastProcessFunction[SimplePoint, LocationxFlinkConfig, SimplePoint]#Context, out: Collector[SimplePoint]): Unit = {
      var mapState = ctx.getBroadcastState(mapStateDescriptor);
      var gidsSets = mapState.get("gids");
      if(gidsSets == null){
        gidsSets = mutable.Set[Long]()
        mapState.put("gids", gidsSets)
      }

      val oldFilterGidsStr = gidsSets.toString();
      if(value != null && value.getProperties.containsKey("filterGroupIds") && value.getProperties.get("filterGroupIds") != null){
        val gidsStr:String = value.getProperties.get("filterGroupIds")
        val gids: Seq[Long] = gidsStr.replaceAll("\\[|\\]", "").split(",").map(_.toLong).toSeq
        for(id <- gids){
          gidsSets += id
        }
      }
      println(s"update config => from [${oldFilterGidsStr}] to [${ctx.getBroadcastState(mapStateDescriptor).get("gids")}]")
    }

    override def open(parameters: Configuration): Unit = {
      super.open(parameters)
      val parMap: java.util.Map[String, String] = super.getRuntimeContext().getExecutionConfig().getGlobalJobParameters().toMap;
      for((pName, gid) <- parMap){
        if(pName.startsWith(FLINK_CONFIG_PREFIX)){
          productName2GidMap.put(pName.replaceAll(FLINK_CONFIG_PREFIX, ""), gid.toLong)
        }
      }
      println("GidFilterRichCoProcess init productName2GidMap " + productName2GidMap)
    }
  }

  def convertTime = (time: Long) =>{

    val ldt: LocalDateTime = LocalDateTime.ofEpochSecond(time / 1000, 0, os)
    ldt.format(formatter)
  }

  class GnssTimeExtractor extends AssignerWithPeriodicWatermarks[SimplePoint] with Serializable {
    override def getCurrentWatermark: Watermark = {
      new Watermark(System.currentTimeMillis - interval * 1000)
    }
    override def extractTimestamp(t: SimplePoint, l: Long): Long = {
      t.gnssTime
    }
  }

  def flinkKafkaPointConsumer = (consumerTopic: String, consumerBrokers: String, kafkaConsumerGroup: String) => {
    val properties = new Properties

    properties.setProperty("bootstrap.servers", consumerBrokers)
    properties.setProperty("group.id", kafkaConsumerGroup)
    new FlinkKafkaConsumer[FlinkInputBean](consumerTopic, new FlinkInputBeanSchema, properties)
  }

  def flinkKafkaConfigConsumer = (consumerTopic: String, consumerBrokers: String, kafkaConsumerGroup: String) => {
    val properties = new Properties
    properties.setProperty("bootstrap.servers", consumerBrokers)
    properties.setProperty("group.id", kafkaConsumerGroup)
    new FlinkKafkaConsumer[LocationxFlinkConfig](consumerTopic, new FlinkConfigStreamSchema, properties)
  }

  def flinkKafkaProducer = (productTopic: String, productBrokers: String) => {
    val properties = new Properties
    properties.setProperty("bootstrap.servers", productBrokers)

    new FlinkKafkaProducer[String](productTopic, new SimpleStringSchema, properties)

  }

  class EidCountProcess extends ProcessWindowFunction[SimplePoint, String, Long, TimeWindow]{

    override def process(key: Long, context: Context, input: Iterable[SimplePoint], out: Collector[String]): Unit = {

      if(input.size <= 0){
        return
      }
      val parMap: java.util.Map[String, String] = super.getRuntimeContext().getExecutionConfig().getGlobalJobParameters().toMap;
      val rid: Long = parMap.get("ruleId").toLong
      val tName: String = parMap.get("tableName")

      //<gid, set<eid>> 一个组的并发链接数统计
      lazy val eidSet: mutable.HashSet[Long] = new mutable.HashSet()
      //<gid, pointcnt> gga并发连接数
      var pointCnt: Long = 0L
      val productName: String = input.head.productName


      println("==> " + rid + " " + tName)
      input.foreach(in => {
        eidSet += in.eid
        pointCnt += 1
      })

      val dtsPackage :DTSPackage = generateReportBean(rid, key, context.window.getStart, pointCnt, eidSet.size.toLong, productName, tName);
      val str = JSON.toJSONString(dtsPackage, SerializerFeature.QuoteFieldNames)

      out.collect(str)
    }


    def generateReportBean(rid : Long, key : Long, startTime : Long, pointCnt : Long, activityCount: Long, productName: String, tName:String) : DTSPackage = {
      val dtsPackage = new DTSPackage()
      dtsPackage.setRuleId(rid)
      val jsonObject = new JSONObject
      dtsPackage.setData(jsonObject)

      val report: FlinkSinkOutputBean = new FlinkSinkOutputBean
      report.setType("group")
      report.setTypeId(key.toLong)

      report.setMinute(convertTime(startTime))
      report.setBizTime(startTime)
      report.setCount(pointCnt)
      report.setActivityCount(activityCount)

      jsonObject.put("product", productName)
      jsonObject.put("table", tName)
      jsonObject.put("data", JSON.toJSONString(report, SerializerFeature.QuoteFieldNames))
      dtsPackage
    }
  }
}

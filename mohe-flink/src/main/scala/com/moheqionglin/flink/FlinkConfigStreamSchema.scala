package com.moheqionglin.flink

import java.util

import com.fasterxml.jackson.annotation.{JsonAnyGetter, JsonAnySetter}
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor

import scala.beans.BeanProperty

class FlinkConfigStreamSchema extends DeserializationSchema[LocationxFlinkConfig]{

  protected val objectMapper: ObjectMapper = new ObjectMapper

  private var reader = objectMapper.readerFor(classOf[LocationxFlinkConfig])

  override def deserialize(bytes: Array[Byte]): LocationxFlinkConfig = {
    this.reader.readValue(bytes)
  }

  override def isEndOfStream(t: LocationxFlinkConfig): Boolean = {
    false;
  }

  override def getProducedType: TypeInformation[LocationxFlinkConfig] =  TypeExtractor.getForClass(classOf[LocationxFlinkConfig])


}

class LocationxFlinkConfig extends Serializable {
  @BeanProperty
  var jodName: String = _
  @BeanProperty
  var streamName: String = _
  @BeanProperty
  @JsonAnyGetter
  @JsonAnySetter
  var properties: util.Map[String,String] = new util.HashMap();

  override def toString: String = "jodName : " + jodName + ", streamName :" + streamName + ", properties: " + properties
}

package com.moheqionglin.flink

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor


class FlinkInputBeanSchema extends DeserializationSchema[FlinkInputBean]{


  protected val objectMapper: ObjectMapper = new ObjectMapper

  private var reader = objectMapper.readerFor(classOf[FlinkInputBean])

  override def deserialize(bytes: Array[Byte]): FlinkInputBean = {
     this.reader.readValue(bytes)
  }

  override def isEndOfStream(t: FlinkInputBean): Boolean = {
     false;
  }

  override def getProducedType: TypeInformation[FlinkInputBean] =  TypeExtractor.getForClass(classOf[FlinkInputBean])
}

package com.moheqionglin.kafka.Serializer.person;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public interface MoheqionglinJsonToBytesFeature {
    public static final SerializerFeature[] features = {
            SerializerFeature.DisableCircularReferenceDetect,//打开循环引用检测，JSONField(serialize = false)不循环
            SerializerFeature.WriteDateUseDateFormat,//默认使用系统默认 格式日期格式化
            SerializerFeature.WriteMapNullValue, //输出空置字段
            SerializerFeature.WriteNullListAsEmpty,//list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty//字符类型字段如果为null，输出为""，而不是null
    };


}

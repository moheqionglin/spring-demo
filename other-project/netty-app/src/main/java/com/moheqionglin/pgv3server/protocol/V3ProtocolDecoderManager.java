package com.moheqionglin.pgv3server.protocol;

import com.moheqionglin.pgv3server.protocol.decoderForFront.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName : V3ProtocolDecoderManager
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-25 14:09
 */
public class V3ProtocolDecoderManager {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<BaseProtocolDecoder> decoders = new ArrayList<>();

    private final static V3ProtocolDecoderManager v3ProtocolDecoderManager = new V3ProtocolDecoderManager();

    private V3ProtocolDecoderManager(){
//        Reflections reflections = new Reflections("com.moheqionglin.pgv3server.protocol.decoder");
//        Set<Class<? extends BaseProtocolDecoder>> decoderClasses = reflections.getSubTypesOf(BaseProtocolDecoder.class);
        Set<Class<? extends BaseProtocolDecoder>> decoderClasses = new HashSet<>();
        decoderClasses.add(V3Bind.class);
        decoderClasses.add(V3CancelRequest.class);
        decoderClasses.add(V3Parse.class);
        decoderClasses.add(V3PasswordMessage.class);
        decoderClasses.add(V3Query.class);
        decoderClasses.add(V3SSLRequest.class);
        decoderClasses.add(V3StartupMessage.class);
        decoderClasses.add(V3Terminate.class);
        for(Class clazz : decoderClasses) {
            try {
                decoders.add((BaseProtocolDecoder) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("init class " + clazz.getName() + " error", e);
            }
        }
    }

    public static V3ProtocolDecoderManager getInstance(){
        return v3ProtocolDecoderManager;
    }

    public List<BaseProtocolDecoder> getDecoders() {
        return decoders;
    }
}
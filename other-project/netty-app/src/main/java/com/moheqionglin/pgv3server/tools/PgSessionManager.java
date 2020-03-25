package com.moheqionglin.pgv3server.tools;

import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.RandomUtils;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PgSessionManager {
    public final static String MD5_SALT = "md5Salt";
    public static final Random random = new Random();
    private static final HashMap<ChannelId, HashMap<String, String>> session2ValueMap = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(PgSessionManager.class);

    static {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(()->{
            log.info("session value : {}", session2ValueMap);
        }, 10, 15, TimeUnit.SECONDS);
    }

    private PgSessionManager(){}

    public static synchronized HashMap<String, String> getAllSessionVal(ChannelId channelId){
        return session2ValueMap.getOrDefault(channelId, new HashMap<>(1));
    }
    public static synchronized String getSessionVal(ChannelId channelId, String key, String defaultVal){
        return session2ValueMap.containsKey(channelId) ? session2ValueMap.get(channelId).getOrDefault(key, defaultVal)
                : defaultVal;
    }

    public synchronized static void setSessionVal(ChannelId channelId, String key, String val) {
        session2ValueMap.putIfAbsent(channelId, new HashMap());
        session2ValueMap.get(channelId).put(key, val);
    }

    public synchronized static void clearAllSessions(){
        session2ValueMap.clear();
    }

    public static synchronized void initSession( ChannelId channelId){
        session2ValueMap.putIfAbsent(channelId, new HashMap());
        int md5 = RandomUtils.nextInt(100000, 10000000);
        //确保仅且仅初始化一次。
        session2ValueMap.get(channelId).putIfAbsent(MD5_SALT, md5 + "");
    }
    public synchronized static void clearSession(ChannelId channelId) {
        session2ValueMap.remove(channelId);
    }

}

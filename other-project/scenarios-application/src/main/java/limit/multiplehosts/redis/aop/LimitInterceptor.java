package limit.multiplehosts.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author wanli.zhou
 * @description
 * @time 17/12/2018 11:13 AM
 */
@Aspect
@Component
public class LimitInterceptor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String limitLuaScript = null;
     @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @PostConstruct
    public void init(){
        try {
            String filePath = "limit/multiplehosts/redis/aop/limit.lua";
            ClassPathResource resource = new ClassPathResource(filePath);
            Path path = Paths.get(resource.getURI());
            limitLuaScript = Files.lines(path).collect(Collectors.joining("\n"));
            log.info(limitLuaScript);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Around("execution(* limit.multiplehosts.redis.bzi.*.*(..)) && @annotation(limit)")
    public Object interceptor(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
        //default redis key is method name
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String limitRedisKey = signature.getName();
        switch (limit.limitType()){
            case IP:
                limitRedisKey = getClientIp();
                break;
            case CUSTOM_DEFINED:
                limitRedisKey = limit.apiName();
                break;

        }
        limitRedisKey += "->" + limit.moduleName();


        RedisScript<Number> redisScript = new DefaultRedisScript<>(limitLuaScript, Number.class);

        Number execute = redisTemplate.execute(redisScript, Arrays.asList(new String[]{limitRedisKey}), limit.permitsMaxCount(), limit.period());

        if(execute != null && execute.intValue() >= 0){
            return joinPoint.proceed();
        }
        throw new RuntimeException("Too many request! please wait a moment and try again!");

    }

    private String getClientIp() {
        //get ip address logic
        return "127.0.0.1";
    }

}
package limit.multiplehosts.redis.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 必填字段， 模块名字
     * @return
     *  moduleName + apiName 作为唯一key放在redis中
     */
    String moduleName() default "";

    /**
     * 要限流的api名字， 模块名字
     * @return
     * moduleName + apiName 作为唯一key放在redis中
     */
    String apiName() default "";

    /**
     * permitsMaxCount / permitsMaxCount = 平均调用次数， 比如一分钟允许调用 10次， 那么
     * period = 60， period = 10
     * @return
     * 单位 s
     */
    int period() default 0;

    /**
     *
     * @return
     */
    int permitsMaxCount() default 0;


    LimitType limitType() default LimitType.CUSTOM_DEFINED;
}

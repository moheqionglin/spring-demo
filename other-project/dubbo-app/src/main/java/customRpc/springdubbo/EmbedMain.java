package customRpc.springdubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import customRpc.api.Another;
import customRpc.api.AnotherImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wanli.zhou
 * @description
 * @time 07/01/2019 4:08 PM
 */
public class EmbedMain {
    public static void main(String[] args) throws InterruptedException {
        Main1.main(null);
        Main2.main(null);

    }



}
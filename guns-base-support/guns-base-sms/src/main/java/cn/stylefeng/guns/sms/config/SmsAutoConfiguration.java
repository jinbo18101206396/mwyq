package cn.stylefeng.guns.sms.config;

import cn.stylefeng.guns.sms.core.cache.impl.MapSignManager;
import cn.stylefeng.guns.sms.core.sms.service.AliyunSmsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * aliyun短信发送的配置
 *
 * @author fengshuonan
 * @Date 2018/7/6 下午3:24
 */
@Configuration
public class SmsAutoConfiguration {

    /**
     * 短信发送管理器
     *
     * @author fengshuonan
     * @Date 2018/9/21 上午10:07
     */
    @Bean
    public AliyunSmsManager aliyunSmsManager() {
        return new AliyunSmsManager();
    }

    /**
     * 缓存的管理
     *
     * @author fengshuonan
     * @Date 2018/9/21 上午10:59
     */
    @Bean
    public MapSignManager mapSignManager() {
        return new MapSignManager();
    }

}
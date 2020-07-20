package cn.stylefeng.guns.sms.config;

import cn.stylefeng.guns.sms.core.db.SmsInfoInitizlizer;
import cn.stylefeng.guns.sms.core.sms.SmsManager;
import cn.stylefeng.guns.sms.core.sms.service.AliyunSmsManager;
import cn.stylefeng.guns.sms.modular.provider.SmsServiceProvider;
import cn.stylefeng.guns.sms.modular.service.SmsInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信服务的配置
 *
 * @author fengshuonan
 * @Date 2018/7/6 下午3:24
 */
@Configuration
public class ServiceAutoConfiguration {

    @Bean
    public SmsServiceProvider smsServiceProvider() {
        return new SmsServiceProvider();
    }

    @Bean
    public SmsInfoService smsInfoService() {
        return new SmsInfoService();
    }

    @Bean
    public SmsManager smsManager() {
        return new AliyunSmsManager();
    }

    @Bean
    public SmsInfoInitizlizer smsInfoInitizlizer() {
        return new SmsInfoInitizlizer();
    }

}
package cn.stylefeng.guns.email.provider;


import cn.stylefeng.guns.email.core.mail.MailManager;
import cn.stylefeng.guns.email.core.model.SendMailParam;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 短信通知接口
 *
 * @author stylefeng
 * @Date 2018/7/5 21:05
 */
public class MailServiceProvider implements MailServiceApi {

    private static Logger logger = LoggerFactory.getLogger(MailServiceProvider.class);

    @Autowired
    private MailManager mailManager;

    @Override
    public void sendMail(SendMailParam sendMailParam) {
        logger.info("email调用入参：" + JSONObject.toJSON(sendMailParam).toString());
        mailManager.sendMail(sendMailParam);
    }
}

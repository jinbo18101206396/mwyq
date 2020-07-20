package cn.stylefeng.guns.email.provider;

import cn.stylefeng.guns.email.core.model.SendMailParam;

/**
 * 邮件发送的api
 *
 * @author fengshuonan
 * @date 2018-07-08-下午3:47
 */
public interface MailServiceApi {

    /**
     * 发送邮件
     *
     * @author fengshuonan
     * @Date 2018/7/8 下午6:28
     */
    void sendMail(SendMailParam sendMailParam);
}

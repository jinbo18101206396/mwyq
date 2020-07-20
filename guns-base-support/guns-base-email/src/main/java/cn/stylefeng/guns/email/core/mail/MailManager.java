package cn.stylefeng.guns.email.core.mail;

import cn.stylefeng.guns.email.core.model.SendMailParam;

/**
 * 邮件收发统一接口
 *
 * @author fengshuonan
 * @date 2018-07-08-下午3:26
 */
public interface MailManager {

    /**
     * 发送普通邮件
     *
     * @author fengshuonan
     * @Date 2018/7/8 下午3:34
     */
    void sendMail(SendMailParam sendMailParam);
}

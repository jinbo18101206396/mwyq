package cn.stylefeng.guns.email.core.mail.service;

import cn.stylefeng.guns.email.core.enums.MailSendResultEnum;
import cn.stylefeng.guns.email.core.mail.MailManager;
import cn.stylefeng.guns.email.core.model.SendMailParam;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * java默认的邮件发送方式实现
 *
 * @author fengshuonan
 * @date 2018-07-08-下午3:34
 */
@Service
@Slf4j
public class JavaMailManager implements MailManager {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendMail(SendMailParam sendMailParam) {

        //校验发送邮件的参数
        assertSendMailParams(sendMailParam);
        log.info("参数校验成功。。。。");
        //spring发送邮件
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(sendMailParam.getTo());
            mimeMessageHelper.setSubject(sendMailParam.getTitle());
            mimeMessageHelper.setText(sendMailParam.getContent(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("发送邮件异常", e);
            throw new ServiceException(MailSendResultEnum.MAIL_SEND_ERROR);
        }
    }

    /**
     * 校验发送邮件的请求参数
     *
     * @author fengshuonan
     * @Date 2018/7/8 下午6:41
     */
    private void assertSendMailParams(SendMailParam sendMailParam) {
        if (sendMailParam == null ||
                ValidateUtil.isOneEmpty(
                        sendMailParam.getTo(), sendMailParam.getTitle(), sendMailParam.getContent())) {
            throw new RequestEmptyException();
        }
    }
}

package cn.stylefeng.guns.sms.modular.model;

import cn.stylefeng.guns.sms.core.enums.MessageType;
import cn.stylefeng.guns.sms.core.enums.SmsSendSource;
import lombok.Data;

import java.util.Map;

/**
 * 发送短信的参数
 *
 * @author fengshuonan
 * @date 2018-07-05 21:19
 */
@Data
public class SendMessageParam {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 模板号
     */
    private String templateCode;

    /**
     * 模板中的参数
     */
    private Map<String, Object> params;

    /**
     * sms发送源
     */
    private SmsSendSource smsSendSource = SmsSendSource.PC;

    /**
     * 消息类型，1=验证码，2=消息，默认不传为验证码
     */
    private MessageType messageType = MessageType.SMS;

}

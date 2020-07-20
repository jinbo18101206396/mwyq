package cn.stylefeng.guns.sms.modular.provider;


import cn.stylefeng.guns.sms.core.enums.SmsSendStatus;
import cn.stylefeng.guns.sms.core.enums.SmsVerifyResult;
import cn.stylefeng.guns.sms.modular.model.SendMessageParam;
import cn.stylefeng.guns.sms.modular.model.VerifySMSParam;


/**
 * 短信通知接口
 *
 * @author stylefeng
 * @Date 2018/7/5 21:05
 */
public interface SmsServiceApi {

    /**
     * 发送短信
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午4:32
     */
    Boolean sendShortMessage(SendMessageParam sendMessageParam);


    /**
     * 验证短信
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午4:30
     */
    SmsVerifyResult verifyShortMessage(VerifySMSParam verifySMSParam);

    /**
     * <pre>
     * 查看短信发送状态
     *
     * 0=未发送，1=发送成功，2=发送失败
     * </pre>
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午4:32
     */
    SmsSendStatus getMessageSendStatus(Integer smsId);

}

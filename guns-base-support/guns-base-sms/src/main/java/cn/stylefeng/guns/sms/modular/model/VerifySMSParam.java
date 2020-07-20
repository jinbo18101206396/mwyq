package cn.stylefeng.guns.sms.modular.model;

import cn.stylefeng.guns.sms.core.enums.SmsSendSource;
import lombok.Data;

/**
 * 发送短信的参数
 *
 * @author fengshuonan
 * @date 2018-07-05 21:19
 */
@Data
public class VerifySMSParam {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 验证码
     */
    private String code;

    /**
     * 模板号
     */
    private String templateCode;

    /**
     * 来源
     */
    private SmsSendSource smsSendSource;

}

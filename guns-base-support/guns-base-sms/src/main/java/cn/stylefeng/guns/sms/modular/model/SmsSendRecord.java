package cn.stylefeng.guns.sms.modular.model;

import lombok.Data;

/**
 * 消息发送记录
 *
 * @author fengshuonan
 * @date 2018-07-06-下午4:07
 */
@Data
public class SmsSendRecord {

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 发送状态 1：等待回执，2：发送失败，3：发送成功
     */
    private Long sendStatus;

    /**
     * 运营商短信错误码
     */
    private String errCode;

    /**
     * 模板ID
     */
    private String templateCode;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String sendDate;

    /**
     * 接收时间
     */
    private String receiveDate;

    /**
     * 外部流水扩展字段
     */
    private String outId;
}

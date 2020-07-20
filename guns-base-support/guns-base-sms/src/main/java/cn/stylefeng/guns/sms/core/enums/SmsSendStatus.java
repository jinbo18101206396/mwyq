package cn.stylefeng.guns.sms.core.enums;

/**
 * 短信发送状态枚举
 *
 * @author stylefeng
 * @Date 2018年5月6日 12:30:48
 */
public enum SmsSendStatus {

    WAITING(0, "未发送"),

    SUCCESS(1, "发送成功"),

    INVALID(3, "失效"),

    FAILED(2, "发送失败");

    private Integer code;

    private String desc;

    SmsSendStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

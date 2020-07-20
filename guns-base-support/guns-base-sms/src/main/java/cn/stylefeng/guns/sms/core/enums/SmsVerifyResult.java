package cn.stylefeng.guns.sms.core.enums;

/**
 * 短信验证结果
 *
 * @author stylefeng
 * @Date 2018年5月6日 12:30:48
 */
public enum SmsVerifyResult {

    SUCCESS(10, "验证成功"),

    ERROR(20, "验证码错误"),

    EXPIRED(30, "验证码超时"),

    TIMES_UP(40, "超过验证次数");

    private Integer code;

    private String desc;

    SmsVerifyResult(Integer code, String desc) {
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

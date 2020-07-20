package cn.stylefeng.guns.sms.core.enums;

/**
 * 短信发送业务枚举
 *
 * @author stylefeng
 * @Date 2018年5月6日 12:30:48
 */
public enum SmsSendSource {

    WEB(0),

    PC(1),

    OTHER(2);

    private Integer code;

    SmsSendSource(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static SmsSendSource toEnum(Integer type) {
        if (type == null) {
            return null;
        } else {
            for (SmsSendSource item : SmsSendSource.values()) {
                if (item.getCode().equals(type)) {
                    return item;
                }
            }
            return null;
        }
    }
}

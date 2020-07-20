package cn.stylefeng.guns.sms.core.enums;

/**
 * 消息类型
 *
 * @author stylefeng
 * @Date 2018年5月6日 12:30:48
 */
public enum MessageType {

    SMS(1, "验证类消息"),

    MESSAGE(2, "纯发送消息");

    private Integer code;

    private String desc;

    MessageType(Integer code, String desc) {
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

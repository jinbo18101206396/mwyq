package cn.stylefeng.guns.email.core.enums;


import cn.stylefeng.roses.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 邮件发送失败的异常枚举
 *
 * @author stylefeng
 * @Date 2018年07月08日18:39:47
 */
public enum MailSendResultEnum implements AbstractBaseExceptionEnum {

    MAIL_SEND_ERROR(500, "邮件发送失败");

    MailSendResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package cn.stylefeng.guns.sms.core.exception;

/**
 * 短信发送异常
 *
 * @author fengshuonan
 * @date 2018-07-06-下午3:00
 */
public class SmsException extends RuntimeException {

    private String code;

    private String errorMessage;

    public SmsException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

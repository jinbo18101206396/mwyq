package cn.stylefeng.guns.modular.mwyq.enums;

import lombok.Getter;

/**
 * 微博类型
 * @author ethan
 */
@Getter
public enum SentimentType {

    NEUTRAL(1,"中性"),
    SENSITIVE(2,"负向"),
    FORWARD(3,"正向");

    private Integer code;
    private String message;

    SentimentType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getDescription(Integer code){
        if (code == null){
            return "";
        } else {
            for(SentimentType s : SentimentType.values()) {
                if (s.getCode() == code){
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}

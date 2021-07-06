package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ethan
 */
@Data
public class WeiboTrendResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dataTime;

    private String neuNum;

    private String senNum;

    private String forNum;

    private String total;
}

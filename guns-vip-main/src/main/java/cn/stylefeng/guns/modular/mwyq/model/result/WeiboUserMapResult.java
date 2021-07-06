package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ethan
 */
@Data
public class WeiboUserMapResult implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 博主所在省份
     */
    private String province;

    /**
     * 所在省份博主数量
     */
    private Integer userCount;
}

package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-12-16
 */
@Data
public class WeiboAuthorResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 博主类型
     */
    private String authorType;

    /**
     * 粉丝量
     */
    private Integer fansCount;

    /**
     * 关注量
     */
    private Integer attentionCount;

    /**
     * 创建时间
     */
    private Date createTime;

}

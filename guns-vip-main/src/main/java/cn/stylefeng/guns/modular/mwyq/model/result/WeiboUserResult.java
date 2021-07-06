package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-12-16
 */
@Data
public class WeiboUserResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 姓名
     */
    private String authorName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 微博数量
     */
    private Integer weiboCount;

    /**
     * 关注量
     */
    private Integer followCount;

    /**
     * 粉丝量
     */
    private Integer followersCount;

    /**
     * 博主描述
     */
    private String description;

    /**
     * 博主所在地
     */
    private String location;

    /**
     * 博主微博认证
     */
    private String verfiedReason;

    /**
     * 博主所在民族
     */
    private String ethnic;

}

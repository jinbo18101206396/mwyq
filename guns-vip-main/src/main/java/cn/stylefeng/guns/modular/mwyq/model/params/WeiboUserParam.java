package cn.stylefeng.guns.modular.mwyq.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class WeiboUserParam implements Serializable, BaseValidatingParam {

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
    private Long weiboCount;

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
     * 博主认证
     */
    private String verifiedReason;

    /**
     * 博主所在民族
     */
    private String ethnic;

    @Override
    public String checkParam() {
        return null;
    }

}

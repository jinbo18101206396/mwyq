package cn.stylefeng.guns.modular.mwyq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author jinbo
 * @since 2020-12-16
 */
@TableName("weibo_user")
public class WeiboUser implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 姓名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 微博数量
     */
    @TableField("weibo_count")
    private Long weiboCount;

    /**
     * 关注量
     */
    @TableField("follow_count")
    private Long followCount;

    /**
     * 粉丝量
     */
    @TableField("followers_count")
    private Integer followersCount;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 所在地
     */
    @TableField("location")
    private String location;

    /**
     * 微博用户认证
     */
    @TableField("verfied_reason")
    private String verfiedReason;

    /**
     * 微博用户所属民族
     */
    @TableField("ethnic")
    private String ethnic;

    @Override
    public String toString() {
        return "WeiboUser{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", gender='" + gender + '\'' +
                ", weiboCount=" + weiboCount +
                ", followCount=" + followCount +
                ", followersCount=" + followersCount +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", verfiedReason='" + verfiedReason + '\'' +
                ", ethnic='" + ethnic + '\'' +
                '}';
    }
}

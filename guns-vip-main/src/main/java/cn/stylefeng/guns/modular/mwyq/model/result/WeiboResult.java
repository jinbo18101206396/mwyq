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
public class WeiboResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    /**
     * 博主id
     */
    private Long authorId;

    /**
     * 博主姓名
     */
    private String authorName;

    /**
     * 博主认证
     */
    private String verifiedReason;

    /**
     * 微博内容
     */
    private String content;

    /**
     * 译文内容
     */
    private String translateContent;

    /**
     * 博文地址
     */
    private String articleUrl;

    /**
     * 发布位置
     */
    private String location;

    /**
     * 阅读量
     */
    private Integer readCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 点赞量
     */
    private Integer likeCount;


    /**
     * 转发量
     */
    private Integer transmitCount;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 情感类型
     */
    private Integer sentiment;

    /**
     * 微博语言
     */
    private String lang;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer num;

}

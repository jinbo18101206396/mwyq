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


    private Long id;

    /**
     * 微博内容
     */
    private String content;

    /**
     * 博主id
     */
    private Long authorId;

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

}

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
public class WeiboCommentResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 评论类容
     */
    private String comments;

    /**
     * 微博id
     */
    private Long weiboId;

    /**
     * 评论人
     */
    private String commentor;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 回复量
     */
    private Integer replyCount;

    /**
     * 创建时间
     */
    private Date createTime;

}

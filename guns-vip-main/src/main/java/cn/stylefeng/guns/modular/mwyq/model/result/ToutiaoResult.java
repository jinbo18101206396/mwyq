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
 * @author 金波
 * @since 2023-05-18
 */
@Data
public class ToutiaoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 文章id
     */
    private String id;

    /**
     * 作者id
     */
    private String authorId;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 文章内部关键词
     */
    private String keyword;

    /**
     * 摘要
     */
    private String description;

    /**
     * 头条内容
     */
    private String content;

    /**
     * 文章地址
     */
    private String articleUrl;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 情感类型
     */
    private Integer sentiment;

    /**
     * 语言语种
     */
    private String lang;

    /**
     * 搜索关键词
     */
    private String Queryword;

}

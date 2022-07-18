package cn.stylefeng.guns.modular.mwyq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
@TableName("weibo")
public class Weibo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 博主id
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 博主姓名
     */
    @TableField("author_name")
    private Long authorName;

    /**
     * 微博内容
     */
    @TableField("content")
    private String content;

    @TableField("article_url")
    private String articleUrl;

    @TableField("location")
    private String location;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 点赞量
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 转发量
     */
    @TableField("transmit_count")
    private Integer transmitCount;

    /**
     * 评论量
     */
    @TableField("comment_count")
    private Integer commentCount;


    /**
     * 情感类型
     */
    @TableField("sentiment")
    private Integer sentiment;

    /**
     * 微博语言
     */
    @TableField("lang")
    private String lang;

    @TableField(exist = false)
    private String translateContent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAuthorName() {
        return authorName;
    }

    public void setAuthorName(Long authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getTransmitCount() {
        return transmitCount;
    }

    public void setTransmitCount(Integer transmitCount) {
        this.transmitCount = transmitCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getSentiment() {
        return sentiment;
    }

    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslateContent() {
        return translateContent;
    }

    public void setTranslateContent(String translateContent) {
        this.translateContent = translateContent;
    }

    @Override
    public String toString() {
        return "Weibo{" +
                "id='" + id + '\'' +
                ", authorId=" + authorId +
                ", authorName=" + authorName +
                ", content='" + content + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", location='" + location + '\'' +
                ", createTime=" + createTime +
                ", likeCount=" + likeCount +
                ", transmitCount=" + transmitCount +
                ", commentCount=" + commentCount +
                ", sentiment=" + sentiment +
                ", lang='" + lang + '\'' +
                ", translateContent='" + translateContent + '\'' +
                '}';
    }
}

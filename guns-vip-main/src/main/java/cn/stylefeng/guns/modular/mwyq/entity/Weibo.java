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
     * 微博内容
     */
    @TableField("content")
    private String content;

    @TableField("article_url")
    private String articleUrl;

    @TableField("original")
    private Integer original;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;

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

    public Weibo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
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
                ", content='" + content + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", original=" + original +
                ", createTime=" + createTime +
                ", deviceType='" + deviceType + '\'' +
                ", likeCount=" + likeCount +
                ", transmitCount=" + transmitCount +
                ", commentCount=" + commentCount +
                ", sentiment=" + sentiment +
                ", lang='" + lang + '\'' +
                ", translateContent='" + translateContent + '\'' +
                '}';
    }
}

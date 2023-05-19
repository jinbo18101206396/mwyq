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
 * @author 金波
 * @since 2023-05-18
 */
@TableName("toutiao")
public class Toutiao implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文章id
     */
      @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 作者id
     */
    @TableField("author_id")
    private String authorId;

    /**
     * 作者姓名
     */
    @TableField("author_name")
    private String authorName;

    /**
     * 文章内部关键词
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 摘要
     */
    @TableField("description")
    private String description;

    /**
     * 头条内容
     */
    @TableField("content")
    private String content;

    /**
     * 文章地址
     */
    @TableField("article_url")
    private String articleUrl;

    /**
     * create_time
     */
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 点赞量
     */
    @TableField("like_count")
    private Integer likeCount;

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
     * 语言语种
     */
    @TableField("lang")
    private String lang;

    /**
     * 搜索关键词
     */
    @TableField("Queryword")
    private String Queryword;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getQueryword() {
        return Queryword;
    }

    public void setQueryword(String Queryword) {
        this.Queryword = Queryword;
    }

    @Override
    public String toString() {
        return "Toutiao{" +
        "id=" + id +
        ", authorId=" + authorId +
        ", authorName=" + authorName +
        ", keyword=" + keyword +
        ", description=" + description +
        ", content=" + content +
        ", articleUrl=" + articleUrl +
        ", createTime=" + createTime +
        ", likeCount=" + likeCount +
        ", commentCount=" + commentCount +
        ", sentiment=" + sentiment +
        ", lang=" + lang +
        ", Queryword=" + Queryword +
        "}";
    }
}

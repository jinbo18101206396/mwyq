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
 * @since 2020-06-14
 */
@TableName("news")
public class News implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "news_id", type = IdType.AUTO)
    private Integer newsId;

    @TableField("news_token")
    private String newsToken;

    @TableField("words_num")
    private Integer wordsNum;

    @TableField("generated_keywords")
    private String generatedKeywords;

    @TableField("news_category")
    private String newsCategory;

    @TableField("news_source")
    private String newsSource;

    @TableField("news_title")
    private String newsTitle;

    @TableField("title_token")
    private String titleToken;

    @TableField("title_lading")
    private String titleLading;

    @TableField("inborn_keywords")
    private String inbornKeywords;

    @TableField("news_time")
    private Date newsTime;

    @TableField("news_url")
    private String newsUrl;

    @TableField("news_author")
    private String newsAuthor;

    @TableField("news_content")
    private String newsContent;

    @TableField(exist = false)
    private String translateContent;

    @TableField("key_words")
    private String keyWords;

    @TableField("content_lading")
    private String contentLading;

    @TableField("lang_type")
    private String langType;

    @TableField("crawl_source")
    private String crawlSource;

    @TableField("crawl_time")
    private Date crawlTime;

    @TableField("news_pictures")
    private String newsPictures;

    @TableField("news_video")
    private String newsVideo;

    @TableField("news_encode")
    private String newsEncode;

    @TableField("news_tendency")
    private String newsTendency;

    @TableField("is_seged")
    private String isSeged;

      @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("has_video")
    private String hasVideo;

    @TableField("is_sensitive")
    private Integer isSensitive;

    @TableField("sensitive_words")
    private String sensitiveWords;

    @TableField("sensitive_category")
    private Integer sensitiveCategory;

    @TableField("websitename")
    private String websitename;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsToken() {
        return newsToken;
    }

    public void setNewsToken(String newsToken) {
        this.newsToken = newsToken;
    }

    public Integer getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(Integer wordsNum) {
        this.wordsNum = wordsNum;
    }

    public String getGeneratedKeywords() {
        return generatedKeywords;
    }

    public void setGeneratedKeywords(String generatedKeywords) {
        this.generatedKeywords = generatedKeywords;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getTitleToken() {
        return titleToken;
    }

    public void setTitleToken(String titleToken) {
        this.titleToken = titleToken;
    }

    public String getTitleLading() {
        return titleLading;
    }

    public void setTitleLading(String titleLading) {
        this.titleLading = titleLading;
    }

    public String getInbornKeywords() {
        return inbornKeywords;
    }

    public void setInbornKeywords(String inbornKeywords) {
        this.inbornKeywords = inbornKeywords;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getContentLading() {
        return contentLading;
    }

    public void setContentLading(String contentLading) {
        this.contentLading = contentLading;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getCrawlSource() {
        return crawlSource;
    }

    public void setCrawlSource(String crawlSource) {
        this.crawlSource = crawlSource;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }

    public String getNewsPictures() {
        return newsPictures;
    }

    public void setNewsPictures(String newsPictures) {
        this.newsPictures = newsPictures;
    }

    public String getNewsVideo() {
        return newsVideo;
    }

    public void setNewsVideo(String newsVideo) {
        this.newsVideo = newsVideo;
    }

    public String getNewsEncode() {
        return newsEncode;
    }

    public void setNewsEncode(String newsEncode) {
        this.newsEncode = newsEncode;
    }

    public String getNewsTendency() {
        return newsTendency;
    }

    public void setNewsTendency(String newsTendency) {
        this.newsTendency = newsTendency;
    }

    public String getIsSeged() {
        return isSeged;
    }

    public void setIsSeged(String isSeged) {
        this.isSeged = isSeged;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(String hasVideo) {
        this.hasVideo = hasVideo;
    }

    public Integer getIsSensitive() {
        return isSensitive;
    }

    public void setIsSensitive(Integer isSensitive) {
        this.isSensitive = isSensitive;
    }

    public String getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(String sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getSensitiveCategory() {
        return sensitiveCategory;
    }

    public void setSensitiveCategory(Integer sensitiveCategory) {
        this.sensitiveCategory = sensitiveCategory;
    }

    public String getTranslateContent() {
        return translateContent;
    }

    public void setTranslateContent(String translateContent) {
        this.translateContent = translateContent;
    }

    @Override
    public String toString() {
        return "News{" + "newsId=" + newsId + ", newsToken='" + newsToken + '\'' + ", wordsNum=" + wordsNum + ", generatedKeywords='" + generatedKeywords + '\'' + ", newsCategory='" + newsCategory + '\'' + ", newsSource='" + newsSource + '\'' + ", newsTitle='" + newsTitle + '\'' + ", titleToken='" + titleToken + '\'' + ", titleLading='" + titleLading + '\'' + ", inbornKeywords='" + inbornKeywords + '\'' + ", newsTime=" + newsTime + ", newsUrl='" + newsUrl + '\'' + ", newsAuthor='" + newsAuthor + '\'' + ", newsContent='" + newsContent + '\'' + ", keyWords='" + keyWords + '\'' + ", contentLading='" + contentLading + '\'' + ", langType='" + langType + '\'' + ", crawlSource='" + crawlSource + '\'' + ", crawlTime=" + crawlTime + ", newsPictures='" + newsPictures + '\'' + ", newsVideo='" + newsVideo + '\'' + ", newsEncode='" + newsEncode + '\'' + ", newsTendency='" + newsTendency + '\'' + ", isSeged='" + isSeged + '\'' + ", updateTime=" + updateTime + ", hasVideo='" + hasVideo + '\'' + ", isSensitive=" + isSensitive + ", sensitiveWords='" + sensitiveWords + '\'' + ", sensitiveCategory=" + sensitiveCategory + ", websitename='" + websitename + '\'' + '}';
    }
}

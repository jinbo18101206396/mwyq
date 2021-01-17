package cn.stylefeng.guns.modular.mwyq.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-07-01
 */
@TableName("topic")
public class Topic implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("topic_id")
    private String topicId;

    @TableField("topwords")
    private String topwords;

    @TableField("topic_label_candidate")
    private String topicLabelCandidate;

    @TableField("topic_name")
    private String topicName;

    @TableField("topic_label")
    private String topicLabel;

    @TableField("producedtime")
    private Date producedtime;

    @TableField("lang_type")
    private String langType;

    @TableField("keywords")
    private String keywords;

    @TableField("topic_dir")
    private String topicDir;

    @TableField("topic_index")
    private Double topicIndex;

    @TableField("news_count")
    private Double newsCount;

    @TableField("news_time")
    private Date newsTime;

    @TableField("news_content")
    private String newsContent;

    @TableField("summarize")
    private String summarize;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopwords() {
        return topwords;
    }

    public void setTopwords(String topwords) {
        this.topwords = topwords;
    }

    public String getTopicLabelCandidate() {
        return topicLabelCandidate;
    }

    public void setTopicLabelCandidate(String topicLabelCandidate) {
        this.topicLabelCandidate = topicLabelCandidate;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicLabel() {
        return topicLabel;
    }

    public void setTopicLabel(String topicLabel) {
        this.topicLabel = topicLabel;
    }

    public Date getProducedtime() {
        return producedtime;
    }

    public void setProducedtime(Date producedtime) {
        this.producedtime = producedtime;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTopicDir() {
        return topicDir;
    }

    public void setTopicDir(String topicDir) {
        this.topicDir = topicDir;
    }

    public Double getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(Double topicIndex) {
        this.topicIndex = topicIndex;
    }

    public Double getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Double newsCount) {
        this.newsCount = newsCount;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    @Override
    public String toString() {
        return "Topic{" +
        "topicId=" + topicId +
        ", topwords=" + topwords +
        ", topicLabelCandidate=" + topicLabelCandidate +
        ", topicName=" + topicName +
        ", topicLabel=" + topicLabel +
        ", producedtime=" + producedtime +
        ", langType=" + langType +
        ", keywords=" + keywords +
        ", topicDir=" + topicDir +
        ", topicIndex=" + topicIndex +
        ", newsCount=" + newsCount +
        ", newsTime=" + newsTime +
        ", newsContent=" + newsContent +
                ", summarize=" + summarize +
        "}";
    }
}

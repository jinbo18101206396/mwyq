package cn.stylefeng.guns.modular.mwyq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("topic_document_relation")
public class TopicDocumentRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("id")
    private Double id;

    @TableField("topic_id")
    private String topicId;

    @TableField("news_id")
    private Double newsId;

    @TableField("relation")
    private Double relation;


    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Double getNewsId() {
        return newsId;
    }

    public void setNewsId(Double newsId) {
        this.newsId = newsId;
    }

    public Double getRelation() {
        return relation;
    }

    public void setRelation(Double relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "TopicDocumentRelation{" +
        "id=" + id +
        ", topicId=" + topicId +
        ", newsId=" + newsId +
        ", relation=" + relation +
        "}";
    }
}

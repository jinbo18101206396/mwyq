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
@TableName("topicminingdistribution")
public class Topicminingdistribution implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("id")
    private Double id;

    @TableField("topic_id")
    private String topicId;

    @TableField("entity_id")
    private Double entityId;

    @TableField("entity_type")
    private String entityType;

    @TableField("distribution")
    private Double distribution;


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

    public Double getEntityId() {
        return entityId;
    }

    public void setEntityId(Double entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Double getDistribution() {
        return distribution;
    }

    public void setDistribution(Double distribution) {
        this.distribution = distribution;
    }

    @Override
    public String toString() {
        return "Topicminingdistribution{" +
        "id=" + id +
        ", topicId=" + topicId +
        ", entityId=" + entityId +
        ", entityType=" + entityType +
        ", distribution=" + distribution +
        "}";
    }
}

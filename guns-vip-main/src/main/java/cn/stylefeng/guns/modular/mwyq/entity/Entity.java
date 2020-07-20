package cn.stylefeng.guns.modular.mwyq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("entity")
public class Entity implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "entity_id", type = IdType.AUTO)
    private Integer entityId;

    @TableField("entity_type")
    private String entityType;

    @TableField("entity_key")
    private String entityKey;

    @TableField("lang_type")
    private String langType;

    @TableField("count")
    private Integer count;


    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Entity{" +
        "entityId=" + entityId +
        ", entityType=" + entityType +
        ", entityKey=" + entityKey +
        ", langType=" + langType +
        ", count=" + count +
        "}";
    }
}

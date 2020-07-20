package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-07-05
 */
@Data
public class EntityTopicRelationResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private Integer entityId;

    private String entityType;

    private String topicId;

    private Double relation;

}

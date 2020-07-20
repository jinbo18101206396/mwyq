package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
@Data
public class EntityNewsRelationResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private Integer entityId;

    private String entityType;

    private Integer newsId;

    private Integer relation;

}

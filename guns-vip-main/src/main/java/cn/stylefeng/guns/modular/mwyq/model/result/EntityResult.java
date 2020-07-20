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
public class EntityResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer entityId;

    private String entityType;

    private String entityKey;

    private String langType;

    private Integer count;

}

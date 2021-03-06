package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-07-01
 */
@Data
public class TopicminingdistributionResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Double id;

    private String topicId;

    private Double entityId;

    private String entityType;

    private Double distribution;

}

package cn.stylefeng.guns.modular.mwyq.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;

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
public class EntityTopicRelationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private Integer entityId;

    private String entityType;

    private String topicId;

    private Double relation;

    @Override
    public String checkParam() {
        return null;
    }

}

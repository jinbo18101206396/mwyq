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
 * @since 2020-07-01
 */
@Data
public class TopicDocumentRelationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Double id;

    private String topicId;

    private Double newsId;

    private Double relation;

    @Override
    public String checkParam() {
        return null;
    }

}

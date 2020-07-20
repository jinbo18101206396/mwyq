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
 * @since 2020-06-14
 */
@Data
public class EntityParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer entityId;

    private String entityType;

    private String entityKey;

    private String langType;

    private Integer count;

    @Override
    public String checkParam() {
        return null;
    }

}

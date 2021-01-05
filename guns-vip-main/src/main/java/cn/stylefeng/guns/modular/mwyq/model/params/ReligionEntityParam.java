package cn.stylefeng.guns.modular.mwyq.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2021-01-04
 */
@Data
public class ReligionEntityParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String entity;

    private Integer entityId;

    private String lang;

    @Override
    public String checkParam() {
        return null;
    }

}

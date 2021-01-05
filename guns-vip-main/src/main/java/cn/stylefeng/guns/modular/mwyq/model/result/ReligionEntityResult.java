package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;
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
public class ReligionEntityResult implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String entity;

    private Integer entityId;

    private String lang;

}

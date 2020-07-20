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
public class NewsTrendResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dataTime;

    private String neuNum;

    private String senNum;

    private String forNum;

    private String total;

}

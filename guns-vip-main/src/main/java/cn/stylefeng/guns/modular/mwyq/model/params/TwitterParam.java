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
 * @since 2021-06-22
 */
@Data
public class TwitterParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String content;

    private String name;

    private Date time;

    private String sentiment;

    private String timeLimit;
    private String beginTime;
    private String endTime;

    private int top;



    @Override
    public String checkParam() {
        return null;
    }

}

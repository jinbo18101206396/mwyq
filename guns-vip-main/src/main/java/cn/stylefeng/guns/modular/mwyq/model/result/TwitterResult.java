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
 * @since 2021-06-22
 */
@Data
public class TwitterResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String content;

    private String name;

    private String lang;

    private String location;

    private Date time;

    private String sentiment;

    private int twitterCount;

    private int sentimentCount;

}

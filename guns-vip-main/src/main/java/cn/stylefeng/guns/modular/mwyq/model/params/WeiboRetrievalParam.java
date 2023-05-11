package cn.stylefeng.guns.modular.mwyq.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
@Data
public class WeiboRetrievalParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private String keyword;
    private String blogger;
    private String scope;
    private String lang;
    private String srclang;
    private String tgtlang;
    private String sensitive;
    private String cycle;


    @Override
    public String checkParam() {
        return null;
    }

}

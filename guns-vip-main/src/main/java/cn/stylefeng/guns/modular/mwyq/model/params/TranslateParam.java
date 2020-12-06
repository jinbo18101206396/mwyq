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
public class TranslateParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    private String sourceLang;
    private String targetLang;
    private String sourceContent;
    private String targetContent;
    private String transModel;

    @Override
    public String checkParam() {
        return null;
    }

}

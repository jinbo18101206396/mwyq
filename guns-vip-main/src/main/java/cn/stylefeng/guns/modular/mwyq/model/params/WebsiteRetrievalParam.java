package cn.stylefeng.guns.modular.mwyq.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class WebsiteRetrievalParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    private String keyword;
    private String lang;
    private String srclang;
    private String tgtlang;
    private String sensitive;
    private String cycle;
    private String type;

    @Override
    public String checkParam() {
        return null;
    }
}

package cn.stylefeng.guns.generator.generator.guns.js;

import cn.stylefeng.guns.generator.generator.base.AbstractCustomGenerator;
import org.beetl.core.Template;

import java.io.File;
import java.util.Map;

/**
 * Guns主页面js生成器
 *
 * @author jinbo
 * @date 2018-12-13-2:20 PM
 */
public class GunsPageIndexJsGenerator extends AbstractCustomGenerator {

    public GunsPageIndexJsGenerator(Map<String, Object> tableContext) {
        super(tableContext);
    }

    @Override
    public void bindingOthers(Template template) {
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/page.js.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/js/" + lowerEntity + "/" + lowerEntity + ".js");
        return file.getAbsolutePath();
    }
}

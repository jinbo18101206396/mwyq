package cn.stylefeng.guns.generator.generator.guns.html;

import cn.stylefeng.guns.generator.generator.base.AbstractCustomGenerator;
import org.beetl.core.Template;

import java.io.File;
import java.util.Map;

/**
 * Guns添加页面生成器
 *
 * @author jinbo
 * @date 2018-12-13-2:20 PM
 */
public class GunsPageAddGenerator extends AbstractCustomGenerator {

    public GunsPageAddGenerator(Map<String, Object> tableContext) {
        super(tableContext);
    }

    @Override
    public void bindingOthers(Template template) {
        super.bindingInputsParams(template);
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/page_add.html.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/html/" + lowerEntity + "/" + lowerEntity + "_add.html");
        return file.getAbsolutePath();
    }
}

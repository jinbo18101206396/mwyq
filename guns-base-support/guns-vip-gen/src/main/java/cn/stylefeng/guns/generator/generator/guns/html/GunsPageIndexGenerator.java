package cn.stylefeng.guns.generator.generator.guns.html;

import cn.stylefeng.guns.generator.generator.base.AbstractCustomGenerator;

import java.io.File;
import java.util.Map;

/**
 * Guns主页面生成器
 *
 * @author jinbo
 * @date 2018-12-13-2:20 PM
 */
public class GunsPageIndexGenerator extends AbstractCustomGenerator {

    public GunsPageIndexGenerator(Map<String, Object> tableContext) {
        super(tableContext);
    }

    @Override
    public String getTemplateResourcePath() {
        return "/gunsTemplates/page.html.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/html/" + lowerEntity + "/" + lowerEntity + ".html");
        return file.getAbsolutePath();
    }
}

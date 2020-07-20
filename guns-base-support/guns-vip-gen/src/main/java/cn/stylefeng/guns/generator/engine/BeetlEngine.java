package cn.stylefeng.guns.generator.engine;

import cn.stylefeng.guns.generator.util.TemplateUtil;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.IOException;
import java.util.Properties;

/**
 * beetl模板引擎的实例
 *
 * @author
 * @date
 */
public class BeetlEngine {

    private static BeetlEngine beetlEngine = new BeetlEngine();

    private static GroupTemplate groupTemplate;

    private BeetlEngine() {
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        org.beetl.core.Configuration cfg = null;

        try {
            cfg = new org.beetl.core.Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        groupTemplate = new GroupTemplate(resourceLoader, cfg);
        groupTemplate.registerFunctionPackage("tool", new TemplateUtil());
    }

    public static GroupTemplate getInstance() {
        return groupTemplate;
    }

}

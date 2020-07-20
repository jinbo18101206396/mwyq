package cn.stylefeng.guns.generator.generator.base;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.generator.engine.BeetlEngine;
import cn.stylefeng.guns.generator.util.OsUtil;
import cn.stylefeng.guns.generator.util.TemplateUtil;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器规范
 *
 * @author jinbo
 * @date 2018-12-12-2:41 PM
 */
@Slf4j
public abstract class AbstractCustomGenerator extends Generator {

    protected Map<String, Object> tableContext;

    public AbstractCustomGenerator(Map<String, Object> tableContext) {
        this.tableContext = tableContext;
    }

    /**
     * 执行代码生成
     *
     * @author jinbo
     * @Date 2018/12/12 3:13 PM
     */
    @Override
    public void doGeneration() {

        //获取beetl模板引擎，初始化模板
        GroupTemplate groupTemplate = BeetlEngine.getInstance();
        Template template = groupTemplate.getTemplate(this.getTemplateResourcePath());

        //绑定默认参数
        template.binding("context", contextParam);

        //获取table的注释，
        TableInfo table = (TableInfo) tableContext.get("table");
        tableContext.put("tableComment", TemplateUtil.cleanWhite(table.getComment()));

        //实体名称的首字母小写的名称
        tableContext.put("lowerEntity", TemplateUtil.lowerFirst((String) tableContext.get("entity")));

        //获取主键的字段名称
        String keyPropertyName = "";
        for (TableField field : table.getFields()) {
            if (field.isKeyFlag()) {
                keyPropertyName = field.getPropertyName();
            }
        }
        tableContext.put("keyPropertyName", keyPropertyName);

        //首字母大写的主键名称
        tableContext.put("bigKeyPropertyName", StrUtil.upperFirst(keyPropertyName));

        //绑定mp的参数
        template.binding(tableContext);

        //绑定模板参数
        this.bindingOthers(template);

        //获取文件生成的路径
        String filePath = getGenerateFilePath();

        //执行代码生成
        if (OsUtil.windowsFlag()) {
            filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        } else {
            filePath = filePath.replaceAll("/+|\\\\+", "/");
        }
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            template.renderTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            log.error("代码生成出错！", e);
        } finally {
            IoUtil.close(fileOutputStream);
        }

    }

    /**
     * 绑定参数
     *
     * @author jinbo
     * @Date 2018/12/13 9:49 AM
     */
    public void bindingOthers(Template template) {

    }

    /**
     * 绑定添加和修改页面 input框元素 需要的参数
     *
     * @author jinbo
     * @Date 2019/1/19 2:18 PM
     */
    public void bindingInputsParams(Template template) {
        TableInfo table = (TableInfo) tableContext.get("table");
        List<TableField> fields = table.getFields();

        //获取去掉主键的字段，主键字段不需要渲染到输入框
        ArrayList<TableField> fieldsNoKey = new ArrayList<>();
        for (TableField field : fields) {
            if (!field.isKeyFlag()) {
                fieldsNoKey.add(field);
            }
        }

        template.binding("fieldsNoKey", fieldsNoKey);
    }

    /**
     * 获取代码生成的模板
     *
     * @author jinbo
     * @Date 2018/12/13 9:46 AM
     */
    public abstract String getTemplateResourcePath();

    /**
     * 获取代码生成的模板
     *
     * @author jinbo
     * @Date 2018/12/13 9:46 AM
     */
    public abstract String getGenerateFilePath();
}

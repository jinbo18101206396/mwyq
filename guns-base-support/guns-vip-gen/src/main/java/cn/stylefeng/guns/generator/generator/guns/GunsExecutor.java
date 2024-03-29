package cn.stylefeng.guns.generator.generator.guns;

import cn.stylefeng.guns.generator.generator.base.model.ContextParam;
import cn.stylefeng.guns.generator.generator.guns.controller.GunsControllerGenerator;
import cn.stylefeng.guns.generator.generator.guns.html.GunsPageAddGenerator;
import cn.stylefeng.guns.generator.generator.guns.html.GunsPageEditGenerator;
import cn.stylefeng.guns.generator.generator.guns.html.GunsPageIndexGenerator;
import cn.stylefeng.guns.generator.generator.guns.js.GunsPageAddJsGenerator;
import cn.stylefeng.guns.generator.generator.guns.js.GunsPageEditJsGenerator;
import cn.stylefeng.guns.generator.generator.guns.js.GunsPageIndexJsGenerator;
import cn.stylefeng.guns.generator.generator.guns.mybatisplus.GunsMpGenerator;
import cn.stylefeng.guns.generator.generator.guns.sqls.GunsMenuSqlGenerator;
import cn.stylefeng.guns.generator.generator.restful.mybatisplus.param.MpParam;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * 测试的执行器
 *
 * @author jinbo
 * @date 2018-12-18-6:39 PM
 */
public class GunsExecutor {

    /**
     * 默认的生成器
     *
     * @author jinbo
     * @Date 2019/1/13 22:18
     */
    public static void executor(ContextParam contextParam, MpParam mpContext) {

        //执行mp的代码生成，生成entity,dao,service,model，生成后保留数据库元数据
        GunsMpGenerator gunsMpGenerator = new GunsMpGenerator(mpContext);
        gunsMpGenerator.initContext(contextParam);
        gunsMpGenerator.doGeneration();

        //获取元数据
        List<TableInfo> tableInfos = gunsMpGenerator.getTableInfos();
        Map<String, Map<String, Object>> everyTableContexts = gunsMpGenerator.getEveryTableContexts();

        //遍历所有表
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> map = everyTableContexts.get(tableInfo.getName());

            //生成控制器
            GunsControllerGenerator gunsControllerGenerator = new GunsControllerGenerator(map);
            gunsControllerGenerator.initContext(contextParam);
            gunsControllerGenerator.doGeneration();

            //生成主页面html
            GunsPageIndexGenerator gunsPageIndexGenerator = new GunsPageIndexGenerator(map);
            gunsPageIndexGenerator.initContext(contextParam);
            gunsPageIndexGenerator.doGeneration();

            //生成主页面js
            GunsPageIndexJsGenerator gunsPageIndexJsGenerator = new GunsPageIndexJsGenerator(map);
            gunsPageIndexJsGenerator.initContext(contextParam);
            gunsPageIndexJsGenerator.doGeneration();

            //生成添加页面html
            GunsPageAddGenerator gunsPageAddGenerator = new GunsPageAddGenerator(map);
            gunsPageAddGenerator.initContext(contextParam);
            gunsPageAddGenerator.doGeneration();

            //生成添加页面的js
            GunsPageAddJsGenerator gunsPageAddJsGenerator = new GunsPageAddJsGenerator(map);
            gunsPageAddJsGenerator.initContext(contextParam);
            gunsPageAddJsGenerator.doGeneration();

            //生成编辑页面html
            GunsPageEditGenerator gunsPageEditGenerator = new GunsPageEditGenerator(map);
            gunsPageEditGenerator.initContext(contextParam);
            gunsPageEditGenerator.doGeneration();

            //生成编辑页面的js
            GunsPageEditJsGenerator gunsPageEditJsGenerator = new GunsPageEditJsGenerator(map);
            gunsPageEditJsGenerator.initContext(contextParam);
            gunsPageEditJsGenerator.doGeneration();

            //生成菜单的sql
            GunsMenuSqlGenerator gunsMenuSqlGenerator = new GunsMenuSqlGenerator(map);
            gunsMenuSqlGenerator.initContext(contextParam);
            gunsMenuSqlGenerator.doGeneration();
        }
    }

    public static void main(String[] args) {

        ContextParam contextParam = new ContextParam();

        contextParam.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        contextParam.setJdbcUserName("root");
        contextParam.setJdbcPassword("7271009boe");
        contextParam.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/law?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT");
        contextParam.setOutputPath("temp");
        contextParam.setAuthor("jinbo");
        contextParam.setProPackage("cn.stylefeng.guns.modular.law");

        MpParam mpContextParam = new MpParam();
        mpContextParam.setGeneratorInterface(true);
        mpContextParam.setIncludeTables(new String[]{"test"});
        mpContextParam.setRemoveTablePrefix(new String[]{"sys_"});

        GunsExecutor.executor(contextParam, mpContextParam);
    }

}

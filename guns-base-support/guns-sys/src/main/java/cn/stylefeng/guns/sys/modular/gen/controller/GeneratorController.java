package cn.stylefeng.guns.sys.modular.gen.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.stylefeng.guns.base.db.entity.DatabaseInfo;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.generator.generator.base.model.ContextParam;
import cn.stylefeng.guns.generator.generator.guns.GunsExecutor;
import cn.stylefeng.guns.generator.generator.restful.RestfulApiExecutor;
import cn.stylefeng.guns.generator.generator.restful.mybatisplus.param.MpParam;
import cn.stylefeng.guns.generator.util.ConcatUtil;
import cn.stylefeng.guns.generator.util.MapperConditionMapHolder;
import cn.stylefeng.guns.sys.modular.db.mapper.DatabaseInfoMapper;
import cn.stylefeng.guns.sys.modular.gen.service.TableService;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @date 2019-01-30-2:39 PM
 */
@Controller
public class GeneratorController {

    /**
     * session中标识已选择条件字段的key
     */
    public static String CONDITION_FIELDS = "CONDITION_FIELDS";

    @Autowired
    private DatabaseInfoMapper databaseInfoMapper;

    @Autowired
    private TableService tableService;

    /**
     * 代码生成主页
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/gen")
    public String index(Model model) {

        List<DatabaseInfo> all = databaseInfoMapper.selectList(new QueryWrapper<>());
        model.addAttribute("dataSources", all);

        return "/modular/gen/gen.html";
    }

    /**
     * 跳转到字段列表页面
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/tableFields")
    public String tableFields(@RequestParam("tableName") String tableName,
                              @RequestParam("dbId") Long dbId, Model model) {

        model.addAttribute("tableName", tableName);
        model.addAttribute("dbId", dbId);

        return "/modular/gen/tableFields.html";
    }

    /**
     * 获取表的字段列表
     *
     * @author fengshuonan
     * @Date 2019/1/30 2:49 PM
     */
    @RequestMapping("/getTableFields")
    @ResponseBody
    public LayuiPageInfo getTableFields(@RequestParam("tableName") String tableName,
                                        @RequestParam("dbId") Long dbId, HttpServletRequest request) {

        List<Map<String, Object>> tableFields = tableService.getTableFields(dbId, tableName);

        LayuiPageInfo layuiPageInfo = new LayuiPageInfo();
        layuiPageInfo.setData(tableFields);
        layuiPageInfo.setCount(tableFields.size());

        return layuiPageInfo;
    }

    /**
     * 设置条件字段
     *
     * @author fengshuonan
     * @Date 2019-05-04 21:50
     */
    @RequestMapping("/setConditionFields")
    @ResponseBody
    public ResponseData setConditionFields(@RequestParam("tableName") String tableName,
                                           @RequestParam("fields") String fields, HttpServletRequest request) {

        //如果字段传输为空，对应tableName的字段设置为空
        if (StrUtil.isEmpty(fields)) {
            HttpSession session = request.getSession();
            Map<String, String[]> fieldMap = (Map<String, String[]>) session.getAttribute(CONDITION_FIELDS);
            if (fieldMap == null) {
                fieldMap = new HashMap<>();
            }
            fieldMap.remove(tableName);
            session.setAttribute(CONDITION_FIELDS, fieldMap);
            return new SuccessResponseData();
        }

        //设置session缓存条件字段
        String[] fieldArray = ConcatUtil.getArray(fields);
        HttpSession session = request.getSession();
        Map<String, String[]> fieldMap = (Map<String, String[]>) session.getAttribute(CONDITION_FIELDS);
        if (fieldMap == null) {
            fieldMap = new HashMap<>();
        }
        fieldMap.put(tableName, fieldArray);
        session.setAttribute(CONDITION_FIELDS, fieldMap);

        return new SuccessResponseData();
    }

    /**
     * 执行代码生成
     *
     * @author fengshuonan
     * @Date 2019-01-11
     */
    @RequestMapping(value = "/execute")
    @ResponseBody
    public ResponseEntity<InputStreamResource> execute(String author, String proPackage, String removePrefix,
                                                       Long dataSourceId, String tables, String modularName,
                                                       String version, String swagger, String remote) {
        //获取字符串拼接数组
        String[] tableArray = ConcatUtil.getArray(tables);

        //获取数据源信息
        DatabaseInfo databaseInfo = this.databaseInfoMapper.selectById(dataSourceId);

        ContextParam contextParam = new ContextParam();
        contextParam.setAuthor(author);
        contextParam.setProPackage(proPackage);
        contextParam.setJdbcDriver(databaseInfo.getJdbcDriver());
        contextParam.setJdbcUserName(databaseInfo.getUserName());
        contextParam.setJdbcPassword(databaseInfo.getPassword());
        contextParam.setJdbcUrl(databaseInfo.getJdbcUrl());
        if ("Y".equalsIgnoreCase(swagger)) {
            contextParam.setSwagger(true);
        } else {
            contextParam.setSwagger(false);
        }
        if ("Y".equalsIgnoreCase(remote)) {
            contextParam.setRemote(true);
        } else {
            contextParam.setRemote(false);
        }

        //处理modularName，如果modularName传值不为空，则待上左斜杠路径
        if (ToolUtil.isNotEmpty(modularName)) {
            modularName = "/" + modularName;
            contextParam.setModularName(modularName);
        }

        //获取临时目录
        long fileName = IdWorker.getId();
        String tempPath = System.getProperty("java.io.tmpdir") + File.separator + "gunsGeneration" + File.separator + fileName;
        contextParam.setOutputPath(tempPath);

        MpParam mpContextParam = new MpParam();
        mpContextParam.setGeneratorInterface(true);
        mpContextParam.setIncludeTables(tableArray);

        if (StrUtil.isNotEmpty(removePrefix)) {
            mpContextParam.setRemoveTablePrefix(new String[]{removePrefix});
        }

        //将session中的数据传递到threadlocal中
        Map<String, String[]> attribute = (Map<String, String[]>) HttpContext.getRequest().getSession().getAttribute(CONDITION_FIELDS);
        MapperConditionMapHolder.put(attribute);


        try {

            //如果是Guns单体版本生成
            if (version.equalsIgnoreCase("single")) {
                GunsExecutor.executor(contextParam, mpContextParam);
            } else {

                //如果是微服务版本代码生成
                RestfulApiExecutor.executor(contextParam, mpContextParam);
            }

        } finally {
            MapperConditionMapHolder.remove();
        }

        //打包下载代码
        File zip = ZipUtil.zip(tempPath);

        return renderFile(fileName + ".zip", zip.getAbsolutePath());
    }

    /**
     * 返回前台文件流
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return
     * @author 0x0001
     */
    private ResponseEntity<InputStreamResource> renderFile(String fileName, InputStream inputStream) {
        InputStreamResource resource = new InputStreamResource(inputStream);
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    private ResponseEntity<InputStreamResource> renderFile(String fileName, String filePath) {
        try {
            final FileInputStream inputStream = new FileInputStream(filePath);
            return renderFile(fileName, inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件读取错误");
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    private ResponseEntity<InputStreamResource> renderFile(String fileName, byte[] fileBytes) {
        return renderFile(fileName, new ByteArrayInputStream(fileBytes));
    }


}

package cn.stylefeng.guns.modular.demos.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.CustomWord;
import cn.stylefeng.guns.modular.mwyq.service.CustomWordService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel导入导出示例
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomWordService customWordService;

    /**
     * excel导入页面
     *
     * @author jinbo
     * @Date
     */
    @RequestMapping("/import")
    public String importIndex() {
        return "/demos/excel_import.html";
    }

    /**
     * 上传excel填报
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public ResponseData uploadExcel(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String name = file.getOriginalFilename();
        request.getSession().setAttribute("upFile", name);
        String fileSavePath = ConstantsContext.getFileUploadPath();
        try {
            file.transferTo(new File(fileSavePath + name));
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", IdWorker.getIdStr());
        return ResponseData.success(0, "上传成功", map);
    }

    /**
     * 获取上传成功的数据
     */
    @RequestMapping("/getUploadData")
    @ResponseBody
    public Object getUploadData(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute("upFile");
        String fileSavePath = ConstantsContext.getFileUploadPath();
        if (name != null) {
            File file = new File(fileSavePath + name);
            try {
                ImportParams params = new ImportParams();
                params.setTitleRows(0);
                params.setHeadRows(1);
                List<CustomWord> customWordList = ExcelImportUtil.importExcel(file, CustomWord.class, params);
                //插入数据库
                for(CustomWord customWord:customWordList){
                    customWord.setCreateUser(LoginContextHolder.getContext().getUserId());
                    customWord.setCreateTime(new Date());
                }
                customWordService.saveBatch(customWordList);
                for(CustomWord customWord:customWordList){
                    //customWord.setUserName(LoginContextHolder.getContext().getUser().getName());
                    customWord.setStatus("ENABLE");
                }
                //返回插入数据
                LayuiPageInfo returns = new LayuiPageInfo();
                returns.setCount(customWordList.size());
                returns.setData(customWordList);
                return returns;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * excel导出
     *
     * @author fengshuonan
     * @Date 2019/3/9 11:03
     */
    @RequestMapping("/export")
    public void export(ModelMap modelMap, HttpServletRequest request,
                       HttpServletResponse response) {

        //初始化表头
        List<ExcelExportEntity> entity = new ArrayList<>();
        entity.add(new ExcelExportEntity("用户id", "user_id"));
        entity.add(new ExcelExportEntity("头像", "avatar"));
        entity.add(new ExcelExportEntity("账号", "account"));
        entity.add(new ExcelExportEntity("姓名", "name"));
        entity.add(new ExcelExportEntity("生日", "birthday"));
        entity.add(new ExcelExportEntity("性别", "sex"));
        entity.add(new ExcelExportEntity("邮箱", "email"));
        entity.add(new ExcelExportEntity("电话", "phone"));
        entity.add(new ExcelExportEntity("角色id", "role_id"));
        entity.add(new ExcelExportEntity("部门id", "dept_id"));
        entity.add(new ExcelExportEntity("状态", "status"));
        entity.add(new ExcelExportEntity("创建时间", "create_time"));

        //初始化化数据
        List<Map<String, Object>> maps = userService.listMaps();
        ArrayList<Map<String, Object>> total = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            total.addAll(maps);
        }

        ExportParams params = new ExportParams("Guns管理系统所有用户", "用户表", ExcelType.XSSF);
        modelMap.put(MapExcelConstants.MAP_LIST, total);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        modelMap.put(MapExcelConstants.FILE_NAME, "Guns管理系统所有用户");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }


}

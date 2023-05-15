package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.entity.SolrWeiboDocResEntity;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.params.WebsiteRetrievalParam;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboRetrievalParam;
import cn.stylefeng.guns.modular.mwyq.utils.*;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.rule.util.HttpServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 信息检索
 *
 * @author jinbo
 * @Date 2021/1/17
 */

@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController extends BaseController {


    @Value("${report.backup.path}")
    private String reportPath;


    private final String PREFIX = "/report";

    @RequestMapping("")
    public String index() {
        return PREFIX + "/report.html";
    }


    /**
     * 下载报告
     *
     * @author jinbo
     * @date 2023/5/11
     */
    @RequestMapping(name = "下载笔录", path = "/download")
    public String reportDownload() {

        HttpServletResponse response = HttpServletUtil.getResponse();

        WordUtil.downloadRecord(response, reportPath);

        return PREFIX + "/report/report.html";
    }

}

package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.service.NewsService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 控制器
 *
 * @author jinbo
 * @Date 2020-06-19 16:03:20
 */
@Controller
@RequestMapping("/sensitive")
public class SensitiveController extends BaseController {

    private String PREFIX = "/sensitive";

    @Autowired
    private NewsService newsService;

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).recordStats().build();

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-06-19
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/sensitive.html";
    }

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-06-19
     */
    @RequestMapping("/religion")
    public String religion() {
        return PREFIX + "/religion.html";
    }

    /**
     * 详情页面
     *
     * @author jinbo
     * @Date 2020-06-19
     */
    @RequestMapping("/detailPage")
    public String detailPage(@RequestParam String lang) {
        if (lang.equals("cn")) {
            return PREFIX + "/sensitive_detail.html";
        }
        return PREFIX + "/sensitive_detail_little.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/sensitive_edit.html";
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(NewsParam newsParam) {
        this.newsService.update(newsParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(NewsParam newsParam) {
        News detail = this.newsService.getById(newsParam.getNewsId());
        return ResponseData.success(detail);
    }

    /**
     * 查询综合新闻列表(初始化10%新闻，按时间倒序排列)
     *
     * @author jinbo
     * @Date 2020-06-19
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(NewsParam newsParam) {

        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "sensitive_news_"+newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getSensitiveWords()+"_"+newsParam.getKeyWords()+"_"+newsParam.getWebsitename()+"_"+newsParam.getTimeLimit()+"_"+page+"_"+limit;
        LayuiPageInfo sensitiveNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(sensitiveNewsCache != null){
            return sensitiveNewsCache;
        }
        LayuiPageInfo sensitiveNewsPage = this.newsService.selectPage(newsParam);
        localCache.put(cacheKey,sensitiveNewsPage);
        return sensitiveNewsPage;
    }

    /**
     * 查询宗教新闻列表
     *
     * @author jinbo
     * @Date 2020-06-19
     */
    @ResponseBody
    @RequestMapping("/religion/list")
    public LayuiPageInfo religionList(NewsParam newsParam) {

        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "sensitive_news_"+newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getSensitiveWords()+"_"+newsParam.getKeyWords()+"_"+newsParam.getWebsitename()+"_"+newsParam.getTimeLimit()+"_"+page+"_"+limit;
        LayuiPageInfo sensitiveNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(sensitiveNewsCache != null){
            return sensitiveNewsCache;
        }
        LayuiPageInfo sensitiveNewsPage = this.newsService.selectPage(newsParam);
        localCache.put(cacheKey,sensitiveNewsPage);
        return sensitiveNewsPage;
    }

}



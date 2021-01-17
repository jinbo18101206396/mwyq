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
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 信息检索
 *
 * @author jinbo
 * @Date 2021/1/17
 */

@Controller
@RequestMapping("/retrieval")
@Slf4j
public class RetrievalController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RetrievalController.class);

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.DAYS).recordStats().build();

    private static WebsiteDocQuery biSolrDoc = new WebsiteDocQuery();
    private static WebsiteDocQuery moSolrDoc = new WebsiteDocQuery();

    private String PREFIX = "/retrieval";

    @RequestMapping("")
    public String index() {
        return PREFIX + "/retrieval.html";
    }

    @RequestMapping("/weibo")
    public String weiboRetrieval() {
        return PREFIX + "/weibo_retrieval.html";
    }

    /**
     * 跳转到网页搜索页面
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/website")
    public String websiteRetrieval(NewsParam newsParam,Model model) {

        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();

        model.addAttribute("keyWords",keyWords);
        model.addAttribute("langType",langType);

        return PREFIX + "/website_retrieval.html";
    }

    /**
     * 加载微博搜索数据
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/search/weibo")
    @ResponseBody
    public JSONObject weiboSearch(WeiboRetrievalParam wrParam) {

        String keyword = wrParam.getKeyword();
        String lang = wrParam.getLang();
        String cycle = wrParam.getCycle();
        String scope = wrParam.getScope();
        String sensitive = wrParam.getSensitive();

        JSONObject weiboSearchJson = new JSONObject();
        String cacheKey = "weibo_search_"+wrParam.getKeyword()+"_"+wrParam.getLang()+"_"+wrParam.getCycle()+"_"+wrParam.getScope()+"_"+wrParam.getSensitive();
        JSONObject weiboSearchCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (weiboSearchCache != null) {
            return weiboSearchCache;
        }
        long positiveNum=0;
        long negativeNum=0;
        long allNum=0;
        WeiboDocQuery docQuery = new WeiboDocQuery();
        if(sensitive.equals("emotion-all")){
            positiveNum = (long) docQuery.queryNum(keyword, lang, "emotion-positive", scope, cycle, 1, 100000, true);
            negativeNum = (long) docQuery.queryNum(keyword, lang, "emotion-negative", scope, cycle, 1, 100000, true);
            allNum = (long) docQuery.queryNum(keyword, lang, "emotion-all", scope, cycle, 1, 100000, true);
        }else if(sensitive.equals("emotion-positive")){
            positiveNum = (long) docQuery.queryNum(keyword, lang, sensitive, scope, cycle, 1, 100000, true);
            allNum = positiveNum;
        }else{
            negativeNum = (long) docQuery.queryNum(keyword, lang, sensitive, scope, cycle, 1, 100000, true);
            allNum = negativeNum;
        }
        weiboSearchJson.put("positiveNum",positiveNum);
        weiboSearchJson.put("negativeNum",negativeNum);
        weiboSearchJson.put("neutralNum",allNum-positiveNum-negativeNum);

        List<SolrWeiboDocResEntity> weiboList = docQuery.query(keyword, lang, sensitive, scope, cycle, 1, 1000, true);
        weiboSearchJson.put("docResList",weiboList);

        String earlyTime = "-";
        String latestTime = "-";
        if(weiboList.size()>0){
            String time_span = docQuery.queryDate(keyword, lang, sensitive, scope, cycle, 1, 100000, true);
            String []times = time_span.split(";");
            earlyTime = times[0];
            latestTime = times[1];
        }
        weiboSearchJson.put("keyword",keyword);
        weiboSearchJson.put("allNum",allNum);
        weiboSearchJson.put("earlyTime",earlyTime);
        weiboSearchJson.put("latestTime",latestTime);

        List<SolrWeiboDocResEntity> hotWeiboList = docQuery.queryTopTen(lang,cycle);
        weiboSearchJson.put("hotWeiboList",hotWeiboList);

        localCache.put(cacheKey,weiboSearchJson);

        return weiboSearchJson;
    }

    /**
     * 根据检索条件查询网页新闻(单语、双语)
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/search/news")
    @ResponseBody
    public JSONObject websiteSearch(WebsiteRetrievalParam webParam) {

        String keyword = webParam.getKeyword();
        String lang = webParam.getLang();
        String sensitive = webParam.getSensitive();
        String cycle = webParam.getCycle();

        String cacheKey = "search_news_"+keyword+"_"+lang+"_"+sensitive+"_"+cycle;
        JSONObject newsListCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsListCache != null) {
            return newsListCache;
        }
        JSONObject websiteSearchJson = new JSONObject();
        if(lang.contains("-")){
            //双语检索
            biSolrDoc.biQuery(keyword,lang, sensitive,cycle,websiteSearchJson);
        }else{
            //单语检索
            moSolrDoc.moQuery(keyword, lang, sensitive,cycle,true,websiteSearchJson);
        }
        localCache.put(cacheKey,websiteSearchJson);
        return websiteSearchJson;
    }

    /**
     * 跳转到新闻详情页（翻译页面）
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/news/detail/page")
    public String websiteNews(NewsParam newsParam,Model model){

        Integer newsId = newsParam.getNewsId();
        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();

        model.addAttribute("newsId",newsId);
        model.addAttribute("keyWords",keyWords);
        model.addAttribute("langType",langType);
        return PREFIX + "/website_news_detail.html";
    }

    /**
     * 翻译新闻
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/news/translate")
    @ResponseBody
    public ResponseData newsDetail(NewsParam newsParam, Model model){

        Integer newsId = newsParam.getNewsId();
        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();
        if(langType.contains("-")){
            langType = langType.split("-")[1];
        }

        String cacheKey = "news_translate"+newsId+"_"+keyWords+"_"+langType;
        SuccessResponseData responseDataCache = (SuccessResponseData)localCache.getIfPresent(cacheKey);
        if (responseDataCache != null) {
            return responseDataCache;
        }
        String queryString = CrossLangQE.getMnFromZhInCrossVali(keyWords, 2);
        String newsTitle = biSolrDoc.getTitleById(newsId).replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        String newsUrl = biSolrDoc.getUrlById(newsId);
        String newsTime = biSolrDoc.getTimeById(newsId);
        String newsContent = biSolrDoc.getContentById(newsId).replace("\u1800", "\u202f").replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        newsContent = "<span style='font-size:20px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + newsTitle + "</span>" +
                "<span style='font-size:10px;text-align:center;display:block;'>"+newsTime+"</span>"+
                "<span style='color:blue;font-size:12px;text-align:center;display:block;'>"+newsUrl+"</span>" + newsContent;

        News news = new News();
        news.setNewsContent(newsContent);

        //翻译少数语言
        if(!langType.equals("cn")){
            TranslationUtil trans = new TranslationUtil();
            String sourceTitleAndContent = newsTitle.replace("\n", "")+"\n"+newsContent;
            String transTitleAndContent = trans.sendPost(sourceTitleAndContent,langType,"article");
            if(ToolUtil.isNotEmpty(transTitleAndContent)) {
                String transTitle = transTitleAndContent.split("\n")[0];
                String transContent = transTitleAndContent.replace("\n", "\n<br>").replace("\r","\n<br>").replace(transTitle, "");
                transContent = "<span style='font-size:17px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + transTitle + "</span>" +
                        "<span style='font-size:10px;text-align:center;display:block;'>"+newsTime+"</span>"+transContent;
                news.setTranslateContent(transContent);
            }
        }
        SuccessResponseData responseData = ResponseData.success(news);
        localCache.put(cacheKey,responseData);
        return responseData;
    }
}

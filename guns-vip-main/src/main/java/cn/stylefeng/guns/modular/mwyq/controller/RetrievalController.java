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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
@RequestMapping("/retrieval")
@Slf4j
public class RetrievalController extends BaseController {

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).recordStats().build();
    private static final Logger logger = LoggerFactory.getLogger(RetrievalController.class);
    private static final WebsiteDocQuery biSolrDoc = new WebsiteDocQuery();
    private static final WebsiteDocQuery moSolrDoc = new WebsiteDocQuery();

    private final String PREFIX = "/retrieval";

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
    public String websiteRetrieval(NewsParam newsParam, Model model) {

        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();

        model.addAttribute("keyWords", keyWords);
        model.addAttribute("langType", langType);

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
        String blogger = wrParam.getBlogger();
        String lang = wrParam.getLang();
        String cycle = wrParam.getCycle();
        String scope = wrParam.getScope();
        String sensitive = wrParam.getSensitive();

        JSONObject weiboSearchJson = new JSONObject();
        long positiveNum = 0;
        long negativeNum = 0;
        long allNum = 0;
        WeiboDocQuery docQuery = new WeiboDocQuery();
        if (sensitive.equals("emotion-all")) {
            positiveNum = docQuery.queryNum(keyword, blogger, lang, "emotion-positive", scope, cycle, 1, 100000, true);
            negativeNum = docQuery.queryNum(keyword, blogger, lang, "emotion-negative", scope, cycle, 1, 100000, true);
            allNum = docQuery.queryNum(keyword, blogger, lang, "emotion-all", scope, cycle, 1, 100000, true);
        } else if (sensitive.equals("emotion-positive")) {
            positiveNum = docQuery.queryNum(keyword, blogger, lang, sensitive, scope, cycle, 1, 100000, true);
            allNum = positiveNum;
        } else {
            negativeNum = docQuery.queryNum(keyword, blogger, lang, sensitive, scope, cycle, 1, 100000, true);
            allNum = negativeNum;
        }
        weiboSearchJson.put("positiveNum", positiveNum);
        weiboSearchJson.put("negativeNum", negativeNum);
        weiboSearchJson.put("neutralNum", allNum - positiveNum - negativeNum);

        List<SolrWeiboDocResEntity> weiboList = docQuery.query(keyword, blogger, lang, sensitive, scope, cycle, 1, 1000, true);
        weiboSearchJson.put("docResList", weiboList);

        String earlyTime = "-";
        String latestTime = "-";
        if (weiboList.size() > 0) {
            String time_span = docQuery.queryDate(keyword, lang, sensitive, scope, cycle, 1, 100000, true);
            String[] times = time_span.split(";");
            earlyTime = times[0];
            latestTime = times[1];
        }
        weiboSearchJson.put("keyword", keyword);
        weiboSearchJson.put("allNum", allNum);
        weiboSearchJson.put("earlyTime", earlyTime);
        weiboSearchJson.put("latestTime", latestTime);

        List<SolrWeiboDocResEntity> hotWeiboList = docQuery.queryTopTen(lang, cycle);
        weiboSearchJson.put("hotWeiboList", hotWeiboList);
        return weiboSearchJson;
    }


    /**
     * 从ES中获取微博数据（单语、双语）
     *
     * @author jinbo
     * @Date 2023/4/20
     */
    @RequestMapping("/search/weibo/es")
    @ResponseBody
    public JSONObject weiboSearchEs(WeiboRetrievalParam wrParam) {
        String keyword = wrParam.getKeyword();
        String blogger = wrParam.getBlogger();
        String lang = wrParam.getLang();
        String cycle = wrParam.getCycle();
        String sensitive = wrParam.getSensitive();

        String url = "http://10.119.130.183:9202/inquiry_weibo";
        JSONObject params = new JSONObject();
        params.put("key_word", keyword);
        params.put("author_name", blogger);
        params.put("lang", lang);
        params.put("sensitive", sensitive);
        params.put("time_limit", cycle);

        JSONArray weiboArray = new JSONArray();
        JSONArray hotWeiboArray = new JSONArray();
        String cnWord = "";
        String minWord = "";

        String result = SendHttpRequest.sendEsPost(url, params);
        if (!result.isEmpty() && !result.contains("Internal Server Error")) {
            JSONObject jsonObject = JSON.parseObject(result);
            weiboArray = jsonObject.getJSONArray("weibos");
            hotWeiboArray = jsonObject.getJSONArray("hot_weibos");
            cnWord = jsonObject.getString("cn_word");
            minWord = jsonObject.getString("min_word");
        }

        int positiveNum = 0;
        int negativeNum = 0;
        int neutralNum = 0;
        for (int i = 0; i < weiboArray.size(); i++) {
            JSONObject newsObject = weiboArray.getJSONObject(i);
            String sent = newsObject.getString("sentiment");
            if ("3".equals(sent)) {
                positiveNum += 1;
            } else if ("2".equals(sent)) {
                negativeNum += 1;
            } else {
                neutralNum += 1;
            }
        }
        JSONObject weiboSearchJson = new JSONObject();
        weiboSearchJson.put("cnWord", cnWord);
        weiboSearchJson.put("minWord", minWord);
        weiboSearchJson.put("weiboList", weiboArray);
        weiboSearchJson.put("hotWeiboList", hotWeiboArray);
        weiboSearchJson.put("positiveNum", positiveNum);
        weiboSearchJson.put("negativeNum", negativeNum);
        weiboSearchJson.put("neutralNum", neutralNum);
        weiboSearchJson.put("weibosNum", weiboArray.size());
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
        JSONObject websiteSearchJson = new JSONObject();
        if (lang.contains("-")) {
            //双语检索
            biSolrDoc.biQuery(keyword, lang, sensitive, cycle, websiteSearchJson);
        } else {
            //单语检索
            moSolrDoc.moQuery(keyword, lang, sensitive, cycle, true, websiteSearchJson);
        }
        return websiteSearchJson;
    }


    /**
     * 从ES中查询网页新闻(单语、双语)
     *
     * @author jinbo
     * @Date 2023/4/18
     */
    @RequestMapping("/search/news/es")
    @ResponseBody
    public JSONObject websiteSearchEs(WebsiteRetrievalParam webParam) {
        String keyword = webParam.getKeyword();
        String lang = webParam.getLang();
        String sensitive = webParam.getSensitive();
        String cycle = webParam.getCycle();

        String cacheKey = "search_news_es_" + keyword + "_" + lang + "_" + sensitive + "_" + cycle;
        JSONObject websiteEsCache = (JSONObject) localCache.getIfPresent(cacheKey);
        if (websiteEsCache != null) {
            return websiteEsCache;
        }

        String url = "http://10.119.130.183:9201/inquiry_by_cn";
        JSONObject params = new JSONObject();
        params.put("key_word", keyword);
        params.put("lang", lang);
        params.put("sensitive", sensitive);
        params.put("time_limit", cycle);

        JSONArray newsArray = new JSONArray();
        JSONArray hotNewsArray = new JSONArray();
        String cnWord = "";
        String minWord = "";

        String result = SendHttpRequest.sendEsPost(url, params);
        if (!result.isEmpty() && !result.contains("Internal Server Error")) {
            JSONObject jsonObject = JSON.parseObject(result);
            newsArray = jsonObject.getJSONArray("news");
            hotNewsArray = jsonObject.getJSONArray("hot_news");
            cnWord = jsonObject.getString("cn_word");
            minWord = jsonObject.getString("min_word");
        }

        int positiveNum = 0;
        int negativeNum = 0;
        int neutralNum = 0;
        for (int i = 0; i < newsArray.size(); i++) {
            JSONObject newsObject = newsArray.getJSONObject(i);
            String sens = newsObject.getString("is_sensitive");
            if ("3".equals(sens)) {
                positiveNum += 1;
            } else if ("2".equals(sens)) {
                negativeNum += 1;
            } else {
                neutralNum += 1;
            }
        }
        JSONObject websiteJson = new JSONObject();
        websiteJson.put("cnWord", cnWord);
        websiteJson.put("minWord", minWord);
        websiteJson.put("newsArray", newsArray);
        websiteJson.put("hotNewsArray", hotNewsArray);
        websiteJson.put("newsNum", newsArray.size());
        websiteJson.put("positiveNum", positiveNum);
        websiteJson.put("negativeNum", negativeNum);
        websiteJson.put("neutralNum", neutralNum);

//        localCache.put(cacheKey,websiteJson);

        return websiteJson;
    }


    /**
     * 跳转到新闻详情页（翻译页面）
     *
     * @author jinbo
     * @Date 2021/1/17
     */
    @RequestMapping("/news/detail/page")
    public String websiteNews(NewsParam newsParam, Model model) {

        Integer newsId = newsParam.getNewsId();
        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();
        Date newsTime = newsParam.getNewsTime();
        String newsUrl = newsParam.getNewsUrl();
        String newsTitle = newsParam.getNewsTitle();
        String newsContent = newsParam.getNewsContent();

        model.addAttribute("newsId", newsId);
        model.addAttribute("keyWords", keyWords);
        model.addAttribute("langType", langType);
        model.addAttribute("newsTime", newsTime);
        model.addAttribute("newsUrl", newsUrl);
        model.addAttribute("newsTitle", newsTitle);
        model.addAttribute("newsContent", newsContent);
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
    public ResponseData newsDetail(NewsParam newsParam, Model model) {

        Integer newsId = newsParam.getNewsId();
        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();
        if (langType.contains("-")) {
            langType = langType.split("-")[1];
        }
        String queryString = CrossLangQE.getMnFromZhInCrossVali(keyWords, 2);
        String newsTitle = biSolrDoc.getTitleById(newsId).replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        String newsUrl = biSolrDoc.getUrlById(newsId);
        String newsTime = biSolrDoc.getTimeById(newsId);
        String newsContent = biSolrDoc.getContentById(newsId).replace("\u1800", "\u202f").replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        newsContent = "<span style='font-size:20px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + newsTitle + "</span>" +
                "<span style='font-size:10px;text-align:center;display:block;'>" + newsTime + "</span>" +
                "<span style='color:blue;font-size:12px;text-align:center;display:block;'>" + newsUrl + "</span>" + newsContent;

        News news = new News();
        news.setNewsContent(newsContent);

        //翻译少数语言
        if (!langType.equals("cn")) {
            TranslationUtil trans = new TranslationUtil();
            String sourceTitleAndContent = newsTitle.replace("\n", "") + "\n" + newsContent;
            String transTitleAndContent = trans.sendPost(sourceTitleAndContent, langType, "article");
            if (ToolUtil.isNotEmpty(transTitleAndContent)) {
                String transTitle = transTitleAndContent.split("\n")[0];
                String transContent = transTitleAndContent.replace("\n", "\n<br>").replace("\r", "\n<br>").replace(transTitle, "");
                transContent = "<span style='font-size:17px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + transTitle + "</span>" +
                        "<span style='font-size:10px;text-align:center;display:block;'>" + newsTime + "</span>" + transContent;
                news.setTranslateContent(transContent);
            }
        }
        SuccessResponseData responseData = ResponseData.success(news);
        return responseData;
    }


    /**
     * 翻译新闻（跨语言检索模块）
     *
     * @author jinbo
     * @Date 2023/4/19
     */
    @RequestMapping("/news/translate/es")
    @ResponseBody
    public ResponseData retrieveDetail(NewsParam newsParam) {

        String keyWords = newsParam.getKeyWords();
        String langType = newsParam.getLangType();
        Date newsTime = newsParam.getNewsTime();
        String newsUrl = newsParam.getNewsUrl();
        String newsTitle = newsParam.getNewsTitle();
        String newsContent = newsParam.getNewsContent();

        String queryString = CrossLangQE.getMnFromZhInCrossVali(keyWords, 2);
        newsTitle = newsTitle.replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        newsContent = newsContent.replace("\u1800", "\u202f").replace(queryString, "<span style=\"color:red\">" + queryString + "</span>");
        newsContent = "<span style='font-size:20px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + newsTitle + "</span>" +
                "<span style='font-size:10px;text-align:center;display:block;'>" + newsTime + "</span>" +
                "<span style='color:blue;font-size:12px;text-align:center;display:block;'>" + newsUrl + "</span>" + newsContent;

        News news = new News();
        news.setNewsContent(newsContent);

        //翻译少数语言
        if (!langType.equals("cn")) {
            TranslationUtil trans = new TranslationUtil();
            String sourceTitleAndContent = newsTitle.replace("\n", "") + "\n" + newsContent;
            String transTitleAndContent = trans.sendPost(sourceTitleAndContent, langType, "article");
            if (ToolUtil.isNotEmpty(transTitleAndContent)) {
                String transTitle = transTitleAndContent.split("\n")[0];
                String transContent = transTitleAndContent.replace("\n", "\n<br>").replace("\r", "\n<br>").replace(transTitle, "");
                transContent = "<span style='font-size:17px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + transTitle + "</span>" +
                        "<span style='font-size:10px;text-align:center;display:block;'>" + newsTime + "</span>" + transContent;
                news.setTranslateContent(transContent);
            }
        }
        SuccessResponseData responseData = ResponseData.success(news);
        return responseData;
    }
}

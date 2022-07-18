package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.enums.Lang;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveCategory;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveType;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.result.*;
import cn.stylefeng.guns.modular.mwyq.service.NewsService;
import cn.stylefeng.guns.modular.mwyq.utils.TranslationUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 控制器
 *
 * @author jinbo
 * @Date 2020-06-14 10:03:33
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    private String PREFIX = "/news";

    @Autowired
    private NewsService newsService;

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).recordStats().build();

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/news.html";
    }

    /**
     * 新增页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/news_add.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/news_edit.html";
    }

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(NewsParam newsParam) {
        this.newsService.add(newsParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(NewsParam newsParam) {
        this.newsService.update(newsParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(NewsParam newsParam) {
        this.newsService.delete(newsParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(NewsParam newsParam) {
        News detail = this.newsService.getById(newsParam.getNewsId());
        return ResponseData.success(detail);
    }

    /**
     * 新闻详情弹框内容
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/dialog/detail")
    @ResponseBody
    public ResponseData showDetail(NewsParam newsParam) {
        News news = this.newsService.getById(newsParam.getNewsId());
        String lang = news.getLangType();
        String newsTitle = news.getNewsTitle();
        String newsContent = news.getNewsContent();
        //翻译少数语言
        if(ToolUtil.isNotEmpty(lang) && !lang.equals("cn")){
            TranslationUtil trans = new TranslationUtil();
            String sourceTitleAndContent = newsTitle.replace("\n", "")+"\n"+newsContent;
            String transTitleAndContent = trans.sendPost(sourceTitleAndContent,lang,"article");
            if(ToolUtil.isNotEmpty(transTitleAndContent)) {
                String transTitle = transTitleAndContent.split("\n")[0];
                String transContent = transTitleAndContent.replace("\n", "\n<br>").replace("\r","\n<br>").replace(transTitle, "");
                news.setTranslateContent("<span style='color:blue;font-size:20px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + transTitle + "</span>" + transContent);
            }
        }
        String sensitiveType = SensitiveType.getDescription(news.getIsSensitive());
        String sensitiveCategory = SensitiveCategory.getDescription(news.getSensitiveCategory());

        //处理敏感词
        String sensitiveWords = news.getSensitiveWords();
        String sensitiveWordsResult = "";
        if (ToolUtil.isNotEmpty(sensitiveWords)) {
            String[] sensitiveWordAndFrequencys = sensitiveWords.split("\\*");
            for (String sensitiveWordAndFrequency : sensitiveWordAndFrequencys) {
                String[] sentiveWordAndFrequencySplit = sensitiveWordAndFrequency.split(":");
                String sensitiveWord = sentiveWordAndFrequencySplit[0];
                String sensitiveWordFrequency = "";
                //正向新闻，词频为空
                if (sentiveWordAndFrequencySplit.length > 1) {
                    sensitiveWordFrequency = sentiveWordAndFrequencySplit[1];
                }
                sensitiveWordsResult += "<span style=\"color:red\">" + sensitiveWord + "</span> : " + sensitiveWordFrequency + "<br>";
                //处理新闻内容
                newsContent = newsContent.replace(sensitiveWord, "<span style=\"color:red\">" + sensitiveWord + "</span>");
            }
        }
        news.setSensitiveWords(sensitiveWordsResult);
        newsContent = "<span style='color:blue;font-size:16px;font-weight:bold;text-align:center;display:block;padding-bottom:5px'>" + newsTitle + "</span>" +
                "<span style='color:red;font-size:16px;text-align:center;display:block;'>" + sensitiveType+"（"+sensitiveCategory+"）</span>"
                + newsContent;
        news.setNewsContent(newsContent);
        //处理关键词
        String keyWords = news.getKeyWords();
        String keyWordsResult = "";
        if (ToolUtil.isNotEmpty(keyWords)) {
            String[] keyWordAndFrequencys = keyWords.split(",");
            for (String keyWordAndFrequency : keyWordAndFrequencys) {
                if(keyWordAndFrequency.contains(":")){
                    String[] keyWordAndFrequencySplit = keyWordAndFrequency.split(":");
                    keyWordsResult += "<span style=\"color:red\">" + keyWordAndFrequencySplit[0] + "</span> : " + keyWordAndFrequencySplit[1] + "<br>";
                }
            }
        }
        news.setKeyWords(keyWordsResult);
        return ResponseData.success(news);
    }

    /**
     * 查询列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(NewsParam newsParam) {
        return this.newsService.findPageBySpec(newsParam);
    }

    /**
     * 新闻来源（热门新闻、敏感新闻饼图）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/source", method = RequestMethod.GET)
    public JSONObject newsSource(NewsParam newsParam) {
        String cacheKey = "news_source_" + newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getWebsitename();
        JSONObject newsSourceCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsSourceCache != null) {
            return newsSourceCache;
        }
        JSONObject newsSourceJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<NewsResult> newsSourceList = newsService.newsSourceList(newsParam);
        for (NewsResult newsSource : newsSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", newsSource.getNum());
            json.put("name", newsSource.getWebsitename());
            jsonArray.add(json);
        }
        newsSourceJson.put("newsSourceData", jsonArray);
        localCache.put(cacheKey, newsSourceJson);
        return newsSourceJson;
    }

    /**
     * 新闻来源（宗教新闻）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/religion/source", method = RequestMethod.GET)
    public JSONObject religionNewsSource(NewsParam newsParam) {

        String cacheKey = "religion_news_source_" + newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getSensitiveCategory();
        JSONObject newsSourceCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsSourceCache != null) {
            return newsSourceCache;
        }
        JSONObject newsSourceJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<NewsResult> religionNewsSourceList = newsService.religionNewsSourceList(newsParam);
        for (NewsResult religionNewsSource : religionNewsSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", religionNewsSource.getNum());
            json.put("name", religionNewsSource.getWebsitename());
            jsonArray.add(json);
        }
        newsSourceJson.put("religionNewsSourceData", jsonArray);
        localCache.put(cacheKey, newsSourceJson);
        return newsSourceJson;
    }

    /**
     * 新闻分布
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/distribution", method = RequestMethod.GET)
    public JSONObject newsDistribution(NewsParam newsParam) {
        String cacheKey = "news_distribution";
        JSONObject newsDistributionCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsDistributionCache != null) {
            return newsDistributionCache;
        }
        JSONObject newsDistributionJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<NewsResult> newsDistributionList = newsService.newsDistributionList(newsParam);
        for (NewsResult newsDistribution : newsDistributionList) {
            JSONObject json = new JSONObject();
            json.put("value", newsDistribution.getNum());
            json.put("name", Lang.getDescription(newsDistribution.getLangType()));
            jsonArray.add(json);
        }
        newsDistributionJson.put("newsDistributionData", jsonArray);
        localCache.put(cacheKey, newsDistributionJson);
        return newsDistributionJson;
    }

    /**
     * 新闻类型（热门新闻、敏感新闻饼图）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/sensitive/type", method = RequestMethod.GET)
    public JSONObject sensitiveType(NewsParam newsParam) {

        //新闻类型数据准备
        String cacheKey = "sensitive_type_" + newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getSensitiveWords()+"_"+newsParam.getKeyWords()+"_"+newsParam.getWebsitename()+"_"+newsParam.getTimeLimit();
        JSONObject sensitiveTypeCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveTypeCache != null) {
            return sensitiveTypeCache;
        }
        JSONObject sensitiveTypeJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<NewsResult> newsSourceList = newsService.sensitiveTypeList(newsParam);
        for (NewsResult newsSource : newsSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", newsSource.getNum());
            json.put("name", SensitiveType.getDescription(newsSource.getIsSensitive()));
            jsonArray.add(json);
        }
        sensitiveTypeJson.put("sensitiveTypeData", jsonArray);
        localCache.put(cacheKey, sensitiveTypeJson);
        return sensitiveTypeJson;
    }

    /**
     * 新闻类型（宗教新闻）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/religion/sensitive", method = RequestMethod.GET)
    public JSONObject religionNewsSensitiveType(NewsParam newsParam) {

        String cacheKey = "religion_sensitive_type_" + newsParam.getLangType()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveCategory();
        JSONObject sensitiveTypeCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveTypeCache != null) {
            return sensitiveTypeCache;
        }
        List<NewsResult> sensitiveTypeList = newsService.religionSensitiveTypeList(newsParam);
        JSONObject sensitiveTypeJson = new JSONObject();
        JSONArray sensitiveTypeArray = new JSONArray();
        for (NewsResult news : sensitiveTypeList) {
            JSONObject sensitiveType = new JSONObject();
            sensitiveType.put("value", news.getNum());
            sensitiveType.put("name", SensitiveType.getDescription(news.getIsSensitive()));
            sensitiveTypeArray.add(sensitiveType);
        }
        sensitiveTypeJson.put("sensitiveTypeData", sensitiveTypeArray);
        localCache.put(cacheKey, sensitiveTypeJson);
        return sensitiveTypeJson;
    }

    /**
     * 新闻类别
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public JSONObject newsCategory(NewsParam newsParam) {

        String lang = newsParam.getLangType();
        String cacheKey = "news_category_" +lang+"_"+newsParam.getTimeLimit();
        JSONObject newsCategoryCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsCategoryCache != null) {
            return newsCategoryCache;
        }
        JSONObject newsCategoryJson = new JSONObject();
        JSONArray categoryNameArray = new JSONArray();
        JSONArray newsCategoryArray = new JSONArray();
        List<NewsResult> newsCategoryList = newsService.newsCategoryList(newsParam);
        for (NewsResult newscategory : newsCategoryList) {
            JSONObject json = new JSONObject();
            String category = newscategory.getNewsCategory();
            Integer num = newscategory.getNum();
            json.put("value", num);
            json.put("name", category);
            categoryNameArray.add(category);
            newsCategoryArray.add(json);
            if (newsCategoryArray.size() > 5) {
                break;
            }
        }
        newsCategoryJson.put("newsCategoryData", newsCategoryArray);
        newsCategoryJson.put("names", categoryNameArray);
        localCache.put(cacheKey, newsCategoryJson);
        return newsCategoryJson;
    }

    /**
     * 敏感类别（柱状图）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/sensitive/category", method = RequestMethod.GET)
    public JSONObject sensitiveCategory(NewsParam newsParam) {

        String cacheKey = "sensitive_category_"+newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getWebsitename();
        JSONObject sensitiveCategoryCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveCategoryCache != null) {
            return sensitiveCategoryCache;
        }

        JSONObject sensitiveCategoryJson = new JSONObject();
        JSONArray categoryNameArray = new JSONArray();
        JSONArray categoryNumArray = new JSONArray();
        List<NewsResult> sensitiveCategoryList = newsService.sensitiveCategoryList(newsParam);
        for (NewsResult sensitiveCategory : sensitiveCategoryList) {
            String categoryName = SensitiveCategory.getDescription(sensitiveCategory.getSensitiveCategory());
            categoryNameArray.add(categoryName);
            categoryNumArray.add(sensitiveCategory.getNum());
        }
        sensitiveCategoryJson.put("categoryName", categoryNameArray);
        sensitiveCategoryJson.put("categoryNum", categoryNumArray);
        localCache.put(cacheKey, sensitiveCategoryJson);
        return sensitiveCategoryJson;
    }


    /**
     * 新闻数量统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/static", method = RequestMethod.GET)
    public JSONObject newsNumStatic(NewsParam newsParam) {
        //统计新闻数量
        String cacheKey = "news_static";
        JSONObject newsStaticCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsStaticCache != null) {
            return newsStaticCache;
        }

        List<NewsStaticResult> newsStaticList = newsService.newsStaticList();
        JSONObject newsStaticJson = new JSONObject();

        List<String> dataTimeList = newsStaticList.stream().map(NewsStaticResult::getDataTime).collect(Collectors.toList());
        newsStaticJson.put("dataTime", JSONArray.parseArray(JSON.toJSONString(dataTimeList)));

        List<String> cnNumList = newsStaticList.stream().map(NewsStaticResult::getCn).collect(Collectors.toList());
        newsStaticJson.put("cnNum", JSONArray.parseArray(JSON.toJSONString(cnNumList)));

        List<String> zangNumList = newsStaticList.stream().map(NewsStaticResult::getZang).collect(Collectors.toList());
        newsStaticJson.put("zangNum", JSONArray.parseArray(JSON.toJSONString(zangNumList)));

        List<String> mengNumList = newsStaticList.stream().map(NewsStaticResult::getMeng).collect(Collectors.toList());
        newsStaticJson.put("mengNum", JSONArray.parseArray(JSON.toJSONString(mengNumList)));

        List<String> weiNumList = newsStaticList.stream().map(NewsStaticResult::getWei).collect(Collectors.toList());
        newsStaticJson.put("weiNum", JSONArray.parseArray(JSON.toJSONString(weiNumList)));

        localCache.put(cacheKey, newsStaticJson);

        return newsStaticJson;
    }

    /**
     * 首页>>敏感统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/trend", method = RequestMethod.GET)
    public JSONObject newsTrend(NewsParam newsParam) {
        String cacheKey = "home_news_trend";
        JSONObject newsTrendCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsTrendCache != null) {
            return newsTrendCache;
        }
        List<NewsTrendResult> newsTrendList = newsService.newsTrendList();
        JSONObject newsTrendJson = new JSONObject();

        List<String> dataTimeList = newsTrendList.stream().map(NewsTrendResult::getDataTime).collect(Collectors.toList());
        newsTrendJson.put("dataTime", JSONArray.parseArray(JSON.toJSONString(dataTimeList)));

        List<String> forNumList = newsTrendList.stream().map(NewsTrendResult::getForNum).collect(Collectors.toList());
        newsTrendJson.put("forNum", JSONArray.parseArray(JSON.toJSONString(forNumList)));

        List<String> neuNumList = newsTrendList.stream().map(NewsTrendResult::getNeuNum).collect(Collectors.toList());
        newsTrendJson.put("neuNum", JSONArray.parseArray(JSON.toJSONString(neuNumList)));

        List<String> senNumList = newsTrendList.stream().map(NewsTrendResult::getSenNum).collect(Collectors.toList());
        newsTrendJson.put("senNum", JSONArray.parseArray(JSON.toJSONString(senNumList)));

        List<String> totalList = newsTrendList.stream().map(NewsTrendResult::getTotal).collect(Collectors.toList());
        newsTrendJson.put("total", JSONArray.parseArray(JSON.toJSONString(totalList)));

        localCache.put(cacheKey, newsTrendJson);
        return newsTrendJson;
    }


    /**
     * 情感分析>>情感走势
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/sensitive/trend", method = RequestMethod.GET)
    public JSONObject sensitiveNewsTrend(NewsParam newsParam) {
        String cacheKey = "sen_news_trend_"+newsParam.getLangType()+"_"+newsParam.getKeyWords()+"_"+newsParam.getWebsitename()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getSensitiveWords()+"_"+newsParam.getTimeLimit();
        JSONObject newsTrendCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsTrendCache != null) {
            return newsTrendCache;
        }
        List<NewsTrendResult> newsTrendList = newsService.sensitiveNewsTrendList(newsParam);
        JSONObject newsTrendJson = new JSONObject();

        List<String> dataTimeList = newsTrendList.stream().map(NewsTrendResult::getDataTime).collect(Collectors.toList());
        newsTrendJson.put("dataTime", JSONArray.parseArray(JSON.toJSONString(dataTimeList)));

        List<String> forNumList = newsTrendList.stream().map(NewsTrendResult::getForNum).collect(Collectors.toList());
        newsTrendJson.put("forNum", JSONArray.parseArray(JSON.toJSONString(forNumList)));

        List<String> neuNumList = newsTrendList.stream().map(NewsTrendResult::getNeuNum).collect(Collectors.toList());
        newsTrendJson.put("neuNum", JSONArray.parseArray(JSON.toJSONString(neuNumList)));

        List<String> senNumList = newsTrendList.stream().map(NewsTrendResult::getSenNum).collect(Collectors.toList());
        newsTrendJson.put("senNum", JSONArray.parseArray(JSON.toJSONString(senNumList)));

        List<String> totalList = newsTrendList.stream().map(NewsTrendResult::getTotal).collect(Collectors.toList());
        newsTrendJson.put("total", JSONArray.parseArray(JSON.toJSONString(totalList)));

        localCache.put(cacheKey, newsTrendJson);
        return newsTrendJson;
    }

    /**
     * 热门新闻列表(时间倒序 取10%)
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/hot/list")
    public LayuiPageInfo hotNews(NewsParam newsParam) {
        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "news_hot_"+newsParam.getLangType()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getSensitiveCategory()+"_"+page+"_"+limit;
        LayuiPageInfo hotNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(hotNewsCache != null){
            return hotNewsCache;
        }
        LayuiPageInfo hotNewsPage = this.newsService.hotPageList(newsParam);
        localCache.put(cacheKey,hotNewsPage);
        return hotNewsPage;
    }

    /**
     * 敏感新闻列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/sensitive/list")
    public LayuiPageInfo sensitiveNews(NewsParam newsParam) {
        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "sen_news_"+newsParam.getLangType()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getWebsitename()+"_"+page+"_"+limit;
        LayuiPageInfo sensitiveNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(sensitiveNewsCache != null){
            return sensitiveNewsCache;
        }
        LayuiPageInfo sensitiveNewsPage = this.newsService.sensitivePageList(newsParam);
        localCache.put(cacheKey,sensitiveNewsPage);
        return sensitiveNewsPage;
    }

    /**
     * 宗教新闻列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/religion/list")
    public LayuiPageInfo religionNews(NewsParam newsParam) {
        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "religion_news_list"+newsParam.getLangType()+"_"+newsParam.getTimeLimit()+"_"+newsParam.getSensitiveCategory()+"_"+newsParam.getIsSensitive()+"_"+newsParam.getKeyWords()+"_"+newsParam.getSensitiveWords()+"_"+"_"+newsParam.getWebsitename()+"_"+page+"_"+limit;
        LayuiPageInfo religionNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(religionNewsCache != null){
            return religionNewsCache;
        }
        LayuiPageInfo religionNews = this.newsService.religionPageList(newsParam);
        localCache.put(cacheKey,religionNews);
        return religionNews;
    }
}



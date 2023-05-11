package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.enums.Lang;
import cn.stylefeng.guns.modular.mwyq.enums.SentimentType;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboUserParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboTrendResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserMapResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserResult;
import cn.stylefeng.guns.modular.mwyq.service.WeiboService;
import cn.stylefeng.guns.modular.mwyq.service.WeiboUserService;
import cn.stylefeng.guns.modular.mwyq.utils.TranslationUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author jinbo
 * @Date 2020-12-16 18:30:10
 */
@Controller
@RequestMapping("/weibo")
public class WeiboController extends BaseController {

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.DAYS).recordStats().build();
    private final String PREFIX = "/weibo";
    @Autowired
    private WeiboService weiboService;
    @Autowired
    private WeiboUserService weiboUserService;

    /**
     * 跳转到微博页面
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/weibo.html";
    }

    /**
     * 所有微博展示
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(WeiboParam weiboParam) {

//        String keyword = weiboParam.getKeyword();
        LayuiPageInfo weiboPage = this.weiboService.findPageBySpec(weiboParam);
//        if(keyword == null){
//            return weiboPage;
//        }
//        List<WeiboResult> weiboList = new ArrayList();
//        List<WeiboResult> weiboResults = weiboPage.getData();
//        for(WeiboResult weibo:weiboResults){
//            String content = weibo.getContent();
//            if(content.contains(keyword)){
//                weiboList.add(weibo);
//            }
//        }
//        weiboPage.setData(weiboList);
//        weiboPage.setCount(weiboList.size());
        return weiboPage;
    }

    /**
     * 微博情感分析图
     *
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment/type", method = RequestMethod.GET)
    public JSONObject weiboSentiment(WeiboParam weiboParam) {
        JSONObject sentimentTypeJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboResult> weiboSourceList = weiboService.sentimentTypeList(weiboParam);
        for (WeiboResult weiboSource : weiboSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", weiboSource.getNum());
            json.put("name", SentimentType.getDescription(weiboSource.getSentiment()));
            jsonArray.add(json);
        }
        sentimentTypeJson.put("sentimentTypeData", jsonArray);
        return sentimentTypeJson;
    }

    /**
     * 微博情感分析图(带关键词)
     *
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment/type/keyword", method = RequestMethod.GET)
    public JSONObject weiboSentimentByKeyword(WeiboParam weiboParam) {

        LayuiPageInfo weiboPage = this.weiboService.findPageBySpec(weiboParam);
        List<WeiboResult> weiboResults = weiboPage.getData();

        List<WeiboResult> weiboList = new ArrayList();
        String keyword = weiboParam.getKeyword();
        if(!"".equals(keyword)){
            for(WeiboResult weiboResult:weiboResults){
                String content = weiboResult.getContent();
                if(content.contains(keyword)){
                    weiboList.add(weiboResult);
                }
            }
        }else{
            weiboList.addAll(weiboResults);
        }

        Map<Integer,Integer> countMap = new HashMap();

        int positive = 0;
        int neural = 0;
        int negative = 0;

        for(WeiboResult weibo : weiboList){
            Integer sentiment = weibo.getSentiment();
            if(sentiment == 1){
                neural += 1;
            }else if(sentiment == 2){
                negative += 1;
            }else if(sentiment == 3){
                positive += 1;
            }
        }
        countMap.put(1,neural);
        countMap.put(2,negative);
        countMap.put(3,positive);

        JSONObject sentimentTypeJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("value", entry.getValue());
            json.put("name", SentimentType.getDescription(entry.getKey()));
            jsonArray.add(json);
        }
        sentimentTypeJson.put("sentimentTypeData", jsonArray);
        return sentimentTypeJson;
    }

    /**
     * 微博语言分布图
     *
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lang/type", method = RequestMethod.GET)
    public JSONObject weiboLangType(WeiboParam weiboParam) {
        JSONObject langJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboResult> langList = weiboService.langTypeList(weiboParam);
        for (WeiboResult weiboResult : langList) {
            JSONObject json = new JSONObject();
            Integer num = weiboResult.getNum();
            String lang = Lang.getDescription(weiboResult.getLang());
            if (TextUtils.isEmpty(lang)) {
                continue;
            }
            json.put("value", num);
            json.put("name", lang);
            jsonArray.add(json);
        }
        langJson.put("langTypeData", jsonArray);
        return langJson;
    }


    /**
     * 微博博主排行
     *
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blogger/rank", method = RequestMethod.GET)
    public JSONObject weiboBloggerRank(WeiboParam weiboParam) {
        String cacheKey = "blogger_rank_" + weiboParam.getLang() + "_" + weiboParam.getSentiment() + "_" + weiboParam.getAuthorName() + "_" + weiboParam.getLocation() + "_" + weiboParam.getTimeLimit();
        JSONObject weiboBloggerRankCache = (JSONObject) localCache.getIfPresent(cacheKey);
        if (weiboBloggerRankCache != null) {
            return weiboBloggerRankCache;
        }
        JSONObject bloggerRankJson = new JSONObject();
        List<WeiboResult> bloggerRankList = weiboService.bloggerRankList(weiboParam);
        List<String> authorNameList = bloggerRankList.stream().map(WeiboResult::getAuthorName).collect(Collectors.toList());
        bloggerRankJson.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        weiboParam.setAuthorNameList(authorNameList);
        JSONObject authorSentiment = weiboService.getAuthorSentiment(weiboParam);
        bloggerRankJson.put("positiveCount", authorSentiment.getJSONArray("positive"));
        bloggerRankJson.put("neuralCount", authorSentiment.getJSONArray("neural"));
        bloggerRankJson.put("negativeCount", authorSentiment.getJSONArray("negative"));
        localCache.put(cacheKey, bloggerRankJson);
        return bloggerRankJson;
    }

    /**
     * 微博走势数据
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment/trend", method = RequestMethod.GET)
    public JSONObject sentimentWeiboTrend(WeiboParam weiboParam) {
        List<WeiboTrendResult> weiboTrendList = weiboService.sentimentTrendList(weiboParam);
        JSONObject weiboTrendJson = new JSONObject();

        List<String> dataTimeList = weiboTrendList.stream().map(WeiboTrendResult::getDataTime).collect(Collectors.toList());
        weiboTrendJson.put("dataTime", JSONArray.parseArray(JSON.toJSONString(dataTimeList)));

        List<String> forNumList = weiboTrendList.stream().map(WeiboTrendResult::getForNum).collect(Collectors.toList());
        weiboTrendJson.put("forNum", JSONArray.parseArray(JSON.toJSONString(forNumList)));

        List<String> neuNumList = weiboTrendList.stream().map(WeiboTrendResult::getNeuNum).collect(Collectors.toList());
        weiboTrendJson.put("neuNum", JSONArray.parseArray(JSON.toJSONString(neuNumList)));

        List<String> senNumList = weiboTrendList.stream().map(WeiboTrendResult::getSenNum).collect(Collectors.toList());
        weiboTrendJson.put("senNum", JSONArray.parseArray(JSON.toJSONString(senNumList)));

        List<String> totalList = weiboTrendList.stream().map(WeiboTrendResult::getTotal).collect(Collectors.toList());
        weiboTrendJson.put("total", JSONArray.parseArray(JSON.toJSONString(totalList)));
        return weiboTrendJson;
    }

    /**
     * 微博翻译
     */
    @RequestMapping(value = "/translate", method = RequestMethod.GET)
    @ResponseBody
    public String translate(WeiboParam weiboParam) {
        String content = weiboParam.getContent();
        String lang = weiboParam.getLang();
        String transContent = "";
        if (ToolUtil.isNotEmpty(content) && !"zh".equals(lang)) {
            TranslationUtil trans = new TranslationUtil();
            transContent = trans.sendPost(content, lang, "paragraph");
        }
        return transContent;
    }

    /**
     * 博主影响力分布
     */
    @RequestMapping("/author")
    @ResponseBody
    public JSONObject weiboFollowersCategory(WeiboUserParam weiboUserParam) {
        JSONObject followersJson = new JSONObject();
        List<WeiboUserResult> weiboFollowers = weiboUserService.followersList(weiboUserParam);
        //博主姓名
        List<String> authorNameList = weiboFollowers.stream().map(WeiboUserResult::getAuthorName).collect(Collectors.toList());
        followersJson.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        //博主粉丝排行
        List<Integer> followersCountList = weiboFollowers.stream().map(WeiboUserResult::getFollowersCount).collect(Collectors.toList());
        followersJson.put("followersCount", JSONArray.parseArray(JSON.toJSONString(followersCountList)));
        //博主微博数
        List<Integer> weiboCountList = weiboFollowers.stream().map(WeiboUserResult::getWeiboCount).collect(Collectors.toList());
        followersJson.put("weiboCount", JSONArray.parseArray(JSON.toJSONString(weiboCountList)));
        //博主关注量
        List<Integer> attentionCountList = weiboFollowers.stream().map(WeiboUserResult::getFollowCount).collect(Collectors.toList());
        followersJson.put("attentionCount", JSONArray.parseArray(JSON.toJSONString(attentionCountList)));
        return followersJson;
    }

    /**
     * 微博数量
     */
    @RequestMapping("/count")
    @ResponseBody
    public JSONObject weiboCount(WeiboUserParam weiboUserParam) {
        JSONObject weiboCountObject = new JSONObject();
        List<WeiboUserResult> weiboFollowers = weiboUserService.followersList(weiboUserParam);
        List<String> authorNameList = weiboFollowers.stream().map(WeiboUserResult::getAuthorName).collect(Collectors.toList());
        weiboCountObject.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        List<Integer> weiboCountList = weiboFollowers.stream().map(WeiboUserResult::getWeiboCount).collect(Collectors.toList());
        weiboCountObject.put("weiboCount", JSONArray.parseArray(JSON.toJSONString(weiboCountList)));
        return weiboCountObject;
    }

    /**
     * 博主粉丝数量
     */
    @RequestMapping("/fellower")
    @ResponseBody
    public JSONObject weiboFellowersCount(WeiboUserParam weiboUserParam) {
        JSONObject weiboFellowerObject = new JSONObject();
        List<WeiboUserResult> weiboFollowers = weiboUserService.followersList(weiboUserParam);
        List<String> authorNameList = weiboFollowers.stream().map(WeiboUserResult::getAuthorName).collect(Collectors.toList());
        weiboFellowerObject.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        //获得博主粉丝排行list
        List<Integer> followersCountList = weiboFollowers.stream().map(WeiboUserResult::getFollowersCount).collect(Collectors.toList());
        weiboFellowerObject.put("followersCount", JSONArray.parseArray(JSON.toJSONString(followersCountList)));
        return weiboFellowerObject;
    }

    /**
     * 博主关注数量
     */
    @RequestMapping("/attention")
    @ResponseBody
    public JSONObject weiboAttentionCount(WeiboUserParam weiboUserParam) {
        JSONObject weiboAttentionObject = new JSONObject();
        List<WeiboUserResult> weiboFollowers = weiboUserService.followersList(weiboUserParam);
        List<String> authorNameList = weiboFollowers.stream().map(WeiboUserResult::getAuthorName).collect(Collectors.toList());
        weiboAttentionObject.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        List<Integer> attentionCountList = weiboFollowers.stream().map(WeiboUserResult::getFollowCount).collect(Collectors.toList());
        weiboAttentionObject.put("attentionCount", JSONArray.parseArray(JSON.toJSONString(attentionCountList)));
        return weiboAttentionObject;
    }

    /**
     * 博主地域分布
     */
    @RequestMapping("/userMap")
    @ResponseBody
    public JSONObject weiboUserMap(WeiboUserParam weiboUserParam) {
        JSONObject weiboUserMapJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboUserMapResult> weiboUserMapList = weiboUserService.userMapList(weiboUserParam);
        for (WeiboUserMapResult usermap : weiboUserMapList) {
            JSONObject json = new JSONObject();
            json.put("name", usermap.getProvince());
            json.put("value", usermap.getUserCount());
            jsonArray.add(json);
        }
        weiboUserMapJson.put("weiboUserMapData", jsonArray);
        return weiboUserMapJson;
    }

    /**
     * 微博地域分布
     */
    @RequestMapping("/areaMap")
    @ResponseBody
    public JSONObject weiboAreaMap(WeiboParam weiboParam) {
        JSONObject weiboAreaMapObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboResult> weiboAreaMapList = weiboService.areaMapList(weiboParam);
        for (WeiboResult areaMap : weiboAreaMapList) {
            String location = areaMap.getLocation();
            Integer num = areaMap.getNum();
            if (ObjectUtils.isEmpty(location)) {
                continue;
            }
            JSONObject json = new JSONObject();
            json.put("name", location);
            json.put("value", num);
            jsonArray.add(json);
        }
        weiboAreaMapObject.put("weiboAreaMapData", jsonArray);
        return weiboAreaMapObject;
    }
}



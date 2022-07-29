package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Weibo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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
        LayuiPageInfo weiboPage = this.weiboService.findPageBySpec(weiboParam);
        return weiboPage;
    }

    /**
     * 微博图表（微博趋势、情感分析图）
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
     * 微博博主排行
     *
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blogger/rank", method = RequestMethod.GET)
    public JSONObject weiboBloggerRank(WeiboParam weiboParam) {
        JSONObject bloggerRankJson = new JSONObject();
        List<WeiboResult> bloggerRankList = weiboService.bloggerRankList(weiboParam);
        List<String> authorNameList = bloggerRankList.stream().map(WeiboResult::getAuthorName).collect(Collectors.toList());
        bloggerRankJson.put("authorName", JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        List<Integer> weiboCountList = bloggerRankList.stream().map(WeiboResult::getNum).collect(Collectors.toList());
        bloggerRankJson.put("weiboCount", JSONArray.parseArray(JSON.toJSONString(weiboCountList)));
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
        Weibo weibo = this.weiboService.getById(weiboParam.getId());
        String weiboContent = weibo.getContent();
        String weiboLang = weibo.getLang();
        String transContent = "";
        if (ToolUtil.isNotEmpty(weiboLang) && !weiboLang.equals("zh")) {
            TranslationUtil trans = new TranslationUtil();
            transContent = trans.sendPost(weiboContent, weiboLang, "paragraph");
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
            if(ObjectUtils.isEmpty(location)){
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



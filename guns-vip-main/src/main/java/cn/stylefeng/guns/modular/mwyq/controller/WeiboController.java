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

    private String PREFIX = "/weibo";

    @Autowired
    private WeiboService weiboService;

    @Autowired
    private WeiboUserService weiboUserService;

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.DAYS).recordStats().build();

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
     * @param weiboParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment/type", method = RequestMethod.GET)
    public JSONObject weiboSentiment(WeiboParam weiboParam){
        JSONObject sentimentTypeJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboResult> weiboSourceList = weiboService.sentimentTypeList(weiboParam);
        for (WeiboResult weiboSource : weiboSourceList){
            JSONObject json = new JSONObject();
            json.put("value", weiboSource.getNum());
            json.put("name", SentimentType.getDescription(weiboSource.getSentiment()));
            jsonArray.add(json);
        }
        sentimentTypeJson.put("sentimentTypeData", jsonArray);
        return sentimentTypeJson;
    }

    /**
     * 微博走势数据
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment/trend", method = RequestMethod.GET)
    public JSONObject sentimentWeiboTrend(WeiboParam weiboParam){
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
    @RequestMapping("/translate")
    @ResponseBody
    public String translate(WeiboParam weiboParam){
        Weibo weibo = this.weiboService.getById(weiboParam.getId());
        String weiboContent = weibo.getContent();
        String weiboLang = weibo.getLang();

        if(ToolUtil.isNotEmpty(weiboLang) && !weiboLang.equals("zh")){
            TranslationUtil trans = new TranslationUtil();
            String transContent = trans.sendPost(weiboContent, weiboLang, "paragraph");
            if (ToolUtil.isNotEmpty(transContent)){
                weibo.setTranslateContent(transContent);
            }
        }
        return weibo.getTranslateContent();
    }

    /**
     * 博主影响力分布
     */
    @RequestMapping("/author")
    @ResponseBody
    public JSONObject weiboFollowersCategory(WeiboUserParam weiboUserParam){
        JSONObject followersJson = new JSONObject();
        List<WeiboUserResult> weiboFollowers = weiboUserService.followersList(weiboUserParam);
        //获得博主姓名list
        List<String> authorNameList = weiboFollowers.stream().map(WeiboUserResult::getAuthorName).collect(Collectors.toList());
        followersJson.put("authorName",JSONArray.parseArray(JSON.toJSONString(authorNameList)));
        //获得博主粉丝排行list
        List<Integer> followersCountList = weiboFollowers.stream().map(WeiboUserResult::getFollowersCount).collect(Collectors.toList());
        followersJson.put("followersCount",JSONArray.parseArray(JSON.toJSONString(followersCountList)));
        //获得博主微博数排行list
        List<Integer> weiboCountList = weiboFollowers.stream().map(WeiboUserResult::getWeiboCount).collect(Collectors.toList());
        followersJson.put("weiboCount", JSONArray.parseArray(JSON.toJSONString(weiboCountList)));

        return followersJson;
    }

    @RequestMapping("/areaMap")
    @ResponseBody
    public JSONObject weiboUserMap(WeiboUserParam weiboUserParam){
        JSONObject weiboUserMapJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        List<WeiboUserMapResult> weiboUserMapList = weiboUserService.userMapList(weiboUserParam);
        for (WeiboUserMapResult usermap:weiboUserMapList) {
            JSONObject json = new JSONObject();
            json.put("name", usermap.getProvince());
            json.put("value", usermap.getUserCount());
            jsonArray.add(json);
        }
        weiboUserMapJson.put("weiboUserMapData",jsonArray);
        return weiboUserMapJson;
    }
}



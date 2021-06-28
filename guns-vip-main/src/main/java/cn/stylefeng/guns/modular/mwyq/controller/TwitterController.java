package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Twitter;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveType;
import cn.stylefeng.guns.modular.mwyq.model.params.TwitterParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TwitterResult;
import cn.stylefeng.guns.modular.mwyq.service.TwitterService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
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


/**
 * 控制器
 *
 * @author jinbo
 * @Date 2021-06-22 15:40:08
 */
@Controller
@RequestMapping("/twitter")
public class TwitterController extends BaseController {

    private String PREFIX = "/twitter";

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.DAYS).recordStats().build();


    @Autowired
    private TwitterService twitterService;

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/twitter.html";
    }

    /**
     * 新增页面
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/twitter_add.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/twitter_edit.html";
    }

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(TwitterParam twitterParam) {
        this.twitterService.add(twitterParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(TwitterParam twitterParam) {
        this.twitterService.update(twitterParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(TwitterParam twitterParam) {
        this.twitterService.delete(twitterParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(TwitterParam twitterParam) {
        Twitter detail = this.twitterService.getById(twitterParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TwitterParam twitterParam) {
        return this.twitterService.findPageBySpec(twitterParam);
    }

    /**
     * 推特作者排行
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @ResponseBody
    @RequestMapping(value = "/author/rank", method = RequestMethod.GET)
    public JSONObject twitterAuthorRank(TwitterParam twitterParam) {
        String cacheKey = "twitter_author_rank";
        JSONObject twitterAuthorRankJsonCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (twitterAuthorRankJsonCache != null) {
            return twitterAuthorRankJsonCache;
        }
        JSONObject twitterAuthorRankJson = new JSONObject();
        JSONArray authorArray = new JSONArray();
        JSONArray twitterArray = new JSONArray();
        List<TwitterResult> authorRankList = twitterService.authorRankList(twitterParam);
        for(TwitterResult authorRank : authorRankList){
            String name = authorRank.getName();
            int twitterCount = authorRank.getTwitterCount();
            authorArray.add(name);
            twitterArray.add(twitterCount);
        }
        twitterAuthorRankJson.put("authors", authorArray);
        twitterAuthorRankJson.put("twitterCount", twitterArray);
        localCache.put(cacheKey,twitterAuthorRankJson);
        return twitterAuthorRankJson;
    }

    /**
     * 情感分析
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    @ResponseBody
    @RequestMapping(value = "/sentiment", method = RequestMethod.GET)
    public JSONObject sentiment(TwitterParam twitterParam) {

        String cacheKey = "twitter_sentiment";
        JSONObject sentimentJsonCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sentimentJsonCache != null) {
            return sentimentJsonCache;
        }
        JSONObject sentimentJson = new JSONObject();
        JSONArray senArray = new JSONArray();
        List<TwitterResult> twitterResults = twitterService.sentimentList(twitterParam);
        for (TwitterResult twitterResult : twitterResults) {
            JSONObject json = new JSONObject();
            json.put("name", SensitiveType.getDescription(Integer.parseInt(twitterResult.getSentiment())));
            json.put("value", twitterResult.getSentimentCount());
            senArray.add(json);
        }
        sentimentJson.put("sentimentData", senArray);
        localCache.put(cacheKey,sentimentJson);
        return sentimentJson;
    }
}



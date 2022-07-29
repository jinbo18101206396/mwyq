package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.*;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveType;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicParam;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicResult;
import cn.stylefeng.guns.modular.mwyq.service.*;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.stylefeng.roses.core.util.HttpContext;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 控制器
 *
 * @author jinbo
 * @Date 2020-07-01 16:06:38
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends BaseController {

    private String PREFIX = "/topic";

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicDocumentRelationService topicDocumentRelationService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EntityTopicRelationService entityTopicRelationService;

    @Autowired
    private EntityNewsRelationService entityNewsRelationService;

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).recordStats().build();

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/topic.html";
    }

    /**
     * 新增页面
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/topic_add.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/topic_edit.html";
    }

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(TopicParam topicParam) {
        this.topicService.add(topicParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(TopicParam topicParam) {
        this.topicService.update(topicParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(TopicParam topicParam) {
        this.topicService.delete(topicParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(TopicParam topicParam) {
        Topic detail = this.topicService.getById(topicParam.getTopicId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TopicParam topicParam) {
        return this.topicService.findPageBySpec(topicParam);
    }

    /**
     * 热门话题列表（时间倒序，取前10%）
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/hot/list")
    public LayuiPageInfo hotTopic(TopicParam topicParam) {
        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "topic_hot_"+topicParam.getLangType()+"_"+topicParam.getTimeLimit()+"_"+topicParam.getTopwords()+"_"+page+"_"+limit;
        LayuiPageInfo hotTopicCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(hotTopicCache != null){
            return hotTopicCache;
        }
        LayuiPageInfo hotTopicPage = this.topicService.findPageBySpec(topicParam);
        localCache.put(cacheKey,hotTopicPage);
        return hotTopicPage;
    }

    /**
     * 热门话题相关新闻列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/relate/news")
    public LayuiPageInfo topicRelateNews(TopicParam topicParam) {

        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "topic_relate_news_"+topicParam.getTopicId()+"_"+topicParam.getLangType()+"_"+topicParam.getIsSensitive()+"_"+topicParam.getSensitiveCategory()+"_"+topicParam.getWebsitename()+"_"+topicParam.getTimeLimit()+"_"+page+"_"+limit;
        LayuiPageInfo topicRelateNewsCache = (LayuiPageInfo)localCache.getIfPresent(cacheKey);
        if(topicRelateNewsCache != null){
            return topicRelateNewsCache;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("topic_id", topicParam.getTopicId());
        List<TopicDocumentRelation> topicDocumentRelationList = topicDocumentRelationService.list(queryWrapper);

        LayuiPageInfo topicRelateNewsPage = null;
        if(topicDocumentRelationList.size() > 0){
            List<Double> newsIdList = topicDocumentRelationList.stream().map(TopicDocumentRelation::getNewsId).collect(Collectors.toList());
            List<Integer> newsIds = new ArrayList<Integer>();
            for(Double newsId:newsIdList){
                newsIds.add(newsId.intValue());
            }
            topicRelateNewsPage = newsService.selectPage(newsIds, topicParam);
        }
        localCache.put(cacheKey,topicRelateNewsPage);
        return topicRelateNewsPage;
    }

    /**
     * 话题相关新闻敏感类型(饼图)
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/relate/news/sensitive")
    public JSONObject topicRelateNewsType(TopicParam topicParam) {

        String cacheKey = "news_sen_"+topicParam.getTopicId()+"_"+topicParam.getLangType()+"_"+topicParam.getIsSensitive()+"_"+topicParam.getSensitiveCategory()+"_"+topicParam.getWebsitename()+"_"+topicParam.getTimeLimit();
        JSONObject sensitiveTypeCache = (JSONObject)localCache.getIfPresent(cacheKey);
        if(sensitiveTypeCache != null){
            return sensitiveTypeCache;
        }
        QueryWrapper topicDocumentRelationQueryWrapper = new QueryWrapper();
        topicDocumentRelationQueryWrapper.eq("topic_id", topicParam.getTopicId());
        List<TopicDocumentRelation> topicDocumentRelationList = topicDocumentRelationService.list(topicDocumentRelationQueryWrapper);
        List<Double> newsIdList = topicDocumentRelationList.stream().map(TopicDocumentRelation::getNewsId).collect(Collectors.toList());

        List<Integer> newsIds = new ArrayList<Integer>();
        for(Double newsId:newsIdList){
            newsIds.add(newsId.intValue());
        }
        List<NewsResult> newsSourceList = newsService.sensitiveTypeListByIds(newsIds,topicParam);
        JSONObject sensitiveTypeJson = new JSONObject();
        JSONArray sensitiveTypeArray = new JSONArray();
        for (NewsResult news : newsSourceList) {
            JSONObject sensitiveType = new JSONObject();
            sensitiveType.put("value", news.getNum());
            sensitiveType.put("name", SensitiveType.getDescription(news.getIsSensitive()));
            sensitiveTypeArray.add(sensitiveType);
        }
        sensitiveTypeJson.put("sensitiveTypeData", sensitiveTypeArray);
        localCache.put(cacheKey,sensitiveTypeJson);
        return sensitiveTypeJson;
    }

    /**
     * 话题相关新闻来源(饼图)
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping("/relate/news/source")
    public JSONObject topicRelateNewsSource(TopicParam topicParam) {

        String cacheKey = "relate_news_source_"+topicParam.getTopicId()+"_"+topicParam.getLangType()+"_"+topicParam.getIsSensitive()+"_"+topicParam.getSensitiveCategory()+"_"+topicParam.getWebsitename()+"_"+topicParam.getTimeLimit();
        JSONObject topicRelateNewsSourceCache = (JSONObject)localCache.getIfPresent(cacheKey);
        if(topicRelateNewsSourceCache != null){
            return topicRelateNewsSourceCache;
        }

        QueryWrapper topicDocumentRelationQueryWrapper = new QueryWrapper();
        topicDocumentRelationQueryWrapper.eq("topic_id", topicParam.getTopicId());
        List<TopicDocumentRelation> topicDocumentRelationList = topicDocumentRelationService.list(topicDocumentRelationQueryWrapper);
        List<Double> newsIdList = topicDocumentRelationList.stream().map(TopicDocumentRelation::getNewsId).collect(Collectors.toList());

        List<Integer> newsIds = new ArrayList<Integer>();
        for(Double newsId:newsIdList){
            newsIds.add(newsId.intValue());
        }
        List<NewsResult> newsSourceList = newsService.topicRelateNewsSourceList(newsIds,topicParam);

        JSONObject topicRelateNewsSourceJson = new JSONObject();
        JSONArray topicRelateNewsSourceArray = new JSONArray();
        JSONArray websiteNameArray = new JSONArray();
        for (NewsResult news : newsSourceList) {
            JSONObject topicNewsSource = new JSONObject();
            topicNewsSource.put("value", news.getNum());
            topicNewsSource.put("name", news.getWebsitename());
            topicRelateNewsSourceArray.add(topicNewsSource);
            websiteNameArray.add(news.getWebsitename());
        }
        topicRelateNewsSourceJson.put("topicNewsSourceData",topicRelateNewsSourceArray);
        topicRelateNewsSourceJson.put("websiteName",websiteNameArray);
        localCache.put(cacheKey,topicRelateNewsSourceJson);
        return topicRelateNewsSourceJson;
    }

    /**
     * 热门话题实体统计(关键词、人名、地名、组织机构名)
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping(value = "/entity/static", method = RequestMethod.GET)
    public JSONObject topicEntityStatic(TopicParam topicParam) {

        String cacheKey = "entity_static_" + topicParam.getTopicId();
        JSONObject entityStaticCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (entityStaticCache != null) {
            return entityStaticCache;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("topic_id", topicParam.getTopicId());
        queryWrapper.orderByDesc("relation");
        List<EntityTopicRelation> entityTopicRelationList = entityTopicRelationService.list(queryWrapper);

        JSONObject entityStaticJson = new JSONObject();
        JSONArray keyWordsNameArray = new JSONArray();
        JSONArray keyWordsNumArray = new JSONArray();
        JSONArray personNameArray = new JSONArray();
        JSONArray personNumArray = new JSONArray();
        JSONArray locationNameArray = new JSONArray();
        JSONArray locationNumArray = new JSONArray();
        JSONArray organizeNameArray = new JSONArray();
        JSONArray organizeNumArray = new JSONArray();
        JSONArray wordcloudArray = new JSONArray();

        int personEntityCount = 0;
        int locationEntityCount = 0;
        int organizeEntityCount = 0;

        for (EntityTopicRelation entityTopicRelation : entityTopicRelationList) {
            Double relation = entityTopicRelation.getRelation();            Entity entity = entityService.getById(entityTopicRelation.getEntityId());
            String entityKey = entity.getEntityKey();
            String entityType = entity.getEntityType();

            //关键词统计
            if (keyWordsNameArray.size() < 10) {
                keyWordsNameArray.add(entityKey);
                keyWordsNumArray.add(relation);
            }
            //高频人名实体统计（柱状图）
            if (entityType.equals("PER") && personNameArray.size() < 10) {
                personNameArray.add(entityKey);
                personNumArray.add(relation);
            }
            //高频地名实体统计（柱状图）
            if (entityType.equals("LOC") && locationNameArray.size() < 10) {
                locationNameArray.add(entityKey);
                locationNumArray.add(relation);
            }
            //高频组织机构实体统计（柱状图）
            if (entityType.equals("ORG") && organizeNameArray.size() < 10) {
                organizeNameArray.add(entityKey);
                organizeNumArray.add(relation);
            }
            //实体词云
            JSONObject wordcloudJson = new JSONObject();
            wordcloudJson.put("name", entityKey);
            wordcloudJson.put("value", entity.getCount());
            wordcloudArray.add(wordcloudJson);
            //人物、地点、组织机构实体个数统计（话题概览）
            if(entityType.equals("PER")){
                personEntityCount++;
            }else if(entityType.equals("LOC")){
                locationEntityCount++;
            }else if(entityType.equals("ORG")){
                organizeEntityCount++;
            }
        }
        entityStaticJson.put("keyWordsName", keyWordsNameArray);
        entityStaticJson.put("keyWordsNum", keyWordsNumArray);
        entityStaticJson.put("personName", personNameArray);
        entityStaticJson.put("personNum", personNumArray);
        entityStaticJson.put("personEntityCount",personEntityCount);
        entityStaticJson.put("locationName", locationNameArray);
        entityStaticJson.put("locationNum", locationNumArray);
        entityStaticJson.put("locationEntityCount",locationEntityCount);
        entityStaticJson.put("organizeName", organizeNameArray);
        entityStaticJson.put("organizeNum", organizeNumArray);
        entityStaticJson.put("organizeEntityCount",organizeEntityCount);
        entityStaticJson.put("wordcloud", wordcloudArray);
        localCache.put(cacheKey, entityStaticJson);
        return entityStaticJson;
    }

    /**
     * 热门话题实体关系
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping(value = "/entity/relation", method = RequestMethod.GET)
    public JSONObject topicEntityRelation(TopicParam topicParam) {

        String cacheKey = "entity_relate_" + topicParam.getTopicId() + "_" + topicParam.getLangType();
        JSONObject entityRelationCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (entityRelationCache != null) {
            return entityRelationCache;
        }
        QueryWrapper topicDocumentRelationQueryWrapper = new QueryWrapper();
        topicDocumentRelationQueryWrapper.eq("topic_id", topicParam.getTopicId());
        topicDocumentRelationQueryWrapper.orderByDesc("relation");
        List<TopicDocumentRelation> topicDocumentRelationList = topicDocumentRelationService.list(topicDocumentRelationQueryWrapper);
        List<Double> newsIds = topicDocumentRelationList.stream().map(TopicDocumentRelation::getNewsId).collect(Collectors.toList());
        QueryWrapper entityNewsRelationQueryWrapper = new QueryWrapper();
        entityNewsRelationQueryWrapper.in("news_id", newsIds);
        List<EntityNewsRelation> entityNewsRelationList = entityNewsRelationService.list(entityNewsRelationQueryWrapper);
        JSONObject entityRelationJson = new JSONObject();
        JSONArray entityRelationArray = new JSONArray();
        for (EntityNewsRelation entityNewsRelation : entityNewsRelationList) {
            Integer entityId = entityNewsRelation.getEntityId();
            Integer newsId = entityNewsRelation.getNewsId();
            String entityType = entityNewsRelation.getEntityType();
            Entity entity = entityService.getById(entityId);
            News news = newsService.getById(newsId);
            JSONObject entityRelation = new JSONObject();
            if (entityType.equals("PER")) {
                entityRelation.put("entityType", 1);
            } else if (entityType.equals("LOC")) {
                entityRelation.put("entityType", 2);
            } else {
                entityRelation.put("entityType", 3);
            }
            entityRelation.put("entityKey", entity.getEntityKey());
            entityRelation.put("newsTitle", news.getNewsTitle());
            entityRelationArray.add(entityRelation);
        }
        entityRelationJson.put("entityRelation", entityRelationArray);
        localCache.put(cacheKey, entityRelationJson);
        return entityRelationJson;
    }

    /**
     * 话题数量(统计前10%)
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    @ResponseBody
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public JSONObject topicCount(TopicParam topicParam) {

        String cacheKey = "topic_count_"+ topicParam.getLangType()+"_"+topicParam.getTimeLimit()+"_"+topicParam.getTopwords();
        JSONObject topicCountCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (topicCountCache != null) {
            return topicCountCache;
        }

        JSONObject topicCountJson = new JSONObject();
        JSONArray dateArray = new JSONArray();
        JSONArray countArray = new JSONArray();

        List<TopicResult> topicList = topicService.topicCountList(topicParam);
        for(TopicResult topic:topicList){
            dateArray.add(topic.getDataTime());
            countArray.add(topic.getCount());
        }
        topicCountJson.put("date",dateArray);
        topicCountJson.put("count",countArray);

        localCache.put(cacheKey, topicCountJson);
        return topicCountJson;
    }
}



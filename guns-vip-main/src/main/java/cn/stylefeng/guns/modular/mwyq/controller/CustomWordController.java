package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.enums.CommonStatus;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.CustomWord;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.model.result.CustomWordResult;
import cn.stylefeng.guns.modular.mwyq.service.CustomWordService;
import cn.stylefeng.guns.modular.mwyq.entity.Entity;
import cn.stylefeng.guns.modular.mwyq.entity.EntityNewsRelation;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveCategory;
import cn.stylefeng.guns.modular.mwyq.enums.SensitiveType;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.service.EntityNewsRelationService;
import cn.stylefeng.guns.modular.mwyq.service.EntityService;
import cn.stylefeng.guns.modular.mwyq.service.NewsService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 定制词表控制器
 *
 * @author jinbo
 * @Date 2020-06-11 22:26:03
 */
@Controller
@RequestMapping("/customWord")
public class CustomWordController extends BaseController {

    private String PREFIX = "/customWord";

    @Autowired
    private CustomWordService customWordService;
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private EntityNewsRelationService entityNewsRelationService;

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.SECONDS).recordStats().build();

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/customWord.html";
    }

    /**
     * 跳转到主题相关新闻页
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/relate")
    public String relate(@RequestParam("customWordId") Long customWordId, @RequestParam("lang") String lang, Model model) {
        model.addAttribute("customWordId", customWordId);
        model.addAttribute("lang", lang);

        CustomWord customWord = customWordService.getById(customWordId);
        if (customWord == null) {
            throw new RequestEmptyException();
        }
        model.addAttribute("customWordName", customWord.getName());

        return PREFIX + "/customWord_relate.html";
    }

    /**
     * 跳转到所属领域新闻页
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/field")
    public String field(@RequestParam("type") String type, @RequestParam("lang") String lang, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("field", CustomWordParam.getFieldByType(type));
        model.addAttribute("lang", lang);
        return PREFIX + "/customWord_field.html";
    }

    /**
     * 新增页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/customWord_add.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/customWord_edit.html";
    }

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CustomWordParam customWordParam) {
        //词表数据去重（主题词和所属领域皆相同则判定为重复）
        QueryWrapper<CustomWord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", customWordParam.getName());
        queryWrapper.eq("type", customWordParam.getType());
        List<CustomWord> customWordList = customWordService.list(queryWrapper);
        if (customWordList.size() > 0) {
            return ResponseData.error("主题词已存在");
        }
        this.customWordService.add(customWordParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(CustomWordParam customWordParam) {
        QueryWrapper<CustomWord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", customWordParam.getName());
        queryWrapper.eq("type", customWordParam.getType());
        List<CustomWord> customWordList = customWordService.list(queryWrapper);
        if (customWordList.size() > 0) {
            return ResponseData.error("主题词已存在");
        }
        this.customWordService.update(customWordParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(CustomWordParam customWordParam) {
        this.customWordService.delete(customWordParam);
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
    public ResponseData detail(CustomWordParam customWordParam) {
        CustomWord detail = this.customWordService.getById(customWordParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表（查询少数民族语言的主题词，统统翻译成汉语进行查询，需要选择主题词语言类型）
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CustomWordParam customWordParam) {

        if (LoginContextHolder.getContext().isAdmin()) {
            String account = customWordParam.getUserName();
            if (ToolUtil.isNotEmpty(account)) {
                User user = userService.getByAccount(account);
                if (ToolUtil.isNotEmpty(user)) {
                    customWordParam.setCreateUser(user.getUserId());
                } else {
                    customWordParam.setCreateUser(1234567890L);
                }
            }
        } else {
            customWordParam.setCreateUser(LoginContextHolder.getContext().getUserId());
        }
        return this.customWordService.findPageBySpec(customWordParam);
    }

    /**
     * 主题相关新闻查询列表（定制新闻、主题相关新闻）
     * 1、列表中包含主题词对应的蒙、汉、藏、维四种语言的新闻
     * 2、首页展示主题词创建时间之后的新闻
     * 3、主题相关页展示主题词对应的所有新闻
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/relate/list")
    public LayuiPageInfo relateList(CustomWordParam customWordParam) {

        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "customWord_relate_news_" + customWordParam.getModular() + "_" + customWordParam.getId() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_"+customWordParam.getKeyWords()+"_"+customWordParam.getSensitiveWords()+ "_" + customWordParam.getNewsSource() + "_" + customWordParam.getTimeLimit() + "_" + page + "_" + limit;
        LayuiPageInfo customWordNewsCache = ( LayuiPageInfo ) localCache.getIfPresent(cacheKey);
        if (customWordNewsCache != null) {
            return customWordNewsCache;
        }
        CustomWord customWord = customWordService.getById(customWordParam.getId());
        String modular = customWordParam.getModular();
        if (modular.equals("1")) {
           //customWordParam.setCreateTime(customWord.getCreateTime());
        }
        //将中文主题词翻译成少数民族语言
        //List<String> customWordNames = customWordService.translateCnCustomWordName(customWord.getName(),customWord.getLang());
        List<String> customWordNames = Stream.of(customWord.getName()).collect(Collectors.toList());
        customWordParam.setNames(customWordNames);
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNames);

        LayuiPageInfo customWordNewsPage = new LayuiPageInfo();
        if (newsIdList != null && newsIdList.size() > 0) {
            customWordNewsPage = newsService.selectPage(newsIdList, customWordParam);
        }
        localCache.put(cacheKey, customWordNewsPage);
        return customWordNewsPage;
    }

    /**
     * 新闻来源（定制新闻、主题相关新闻）
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/relate/news/source")
    public JSONObject relateNewsSource(CustomWordParam customWordParam) {
        String cacheKey = "relate_news_source_" + customWordParam.getModular() + "_" + customWordParam.getId() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource()+"_"+customWordParam.getSensitiveWords()+"_"+customWordParam.getKeyWords()+ "_" + customWordParam.getTimeLimit();
        JSONObject newsSourceCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsSourceCache != null) {
            return newsSourceCache;
        }
        CustomWord customWord = customWordService.getById(customWordParam.getId());
        String modular = customWordParam.getModular();
        if (modular.equals("1")) {
            customWordParam.setCreateTime(customWord.getCreateTime());
        }
        //将中文主题词翻译成少数民族语言
        //List<String> customWordNames = customWordService.translateCnCustomwordName(customWord.getName(),customWordParam.getLang());
        List<String> customWordNames = Stream.of(customWord.getName()).collect(Collectors.toList());
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNames);
        List<NewsResult> customWordRelateNewsSourceList = new ArrayList<NewsResult>();
        if(ToolUtil.isNotEmpty(newsIdList)){
            customWordRelateNewsSourceList = newsService.customWordRelateNewsSourceList(newsIdList, customWordParam);
        }
        //主题词相关新闻来源
        JSONObject newsSourceJson = new JSONObject();
        JSONArray newsSourceArray = new JSONArray();
        for (NewsResult newsSource : customWordRelateNewsSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", newsSource.getNum());
            json.put("name", newsSource.getWebsitename());
            newsSourceArray.add(json);
        }
        newsSourceJson.put("newsSourceData", newsSourceArray);
        localCache.put(cacheKey, newsSourceJson);
        return newsSourceJson;
    }

    /**
     * 新闻类型（定制新闻、主题相关新闻）
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/relate/sensitive/type")
    public JSONObject relateSensitiveType(CustomWordParam customWordParam) {
        String cacheKey = "cus_rel_sen_type_" + customWordParam.getModular() + "_" + customWordParam.getId() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_"+customWordParam.getSensitiveWords()+"_"+customWordParam.getKeyWords()+"_" + customWordParam.getNewsSource() + "_" + customWordParam.getTimeLimit();
        JSONObject sensitiveTypeCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveTypeCache != null) {
            return sensitiveTypeCache;
        }
        CustomWord customWord = customWordService.getById(customWordParam.getId());
        String modular = customWordParam.getModular();
        if (modular.equals("1")) {
            customWordParam.setCreateTime(customWord.getCreateTime());
        }
        //将中文主题词翻译成少数民族语言
        //List<String> customWordNames = customWordService.translateCnCustomwordName(customWord.getName(),customWordParam.getLang());
        List<String> customWordNames = Stream.of(customWord.getName()).collect(Collectors.toList());
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNames);
        List<NewsResult> customWordRelateNewsSensitiveList = new ArrayList<NewsResult>();
        if(ToolUtil.isNotEmpty(newsIdList)){
            customWordRelateNewsSensitiveList = newsService.customWordRelateNewsSensitiveList(newsIdList, customWordParam);
        }
        //主题词相关新闻类型
        JSONObject sensitiveTypeJson = new JSONObject();
        JSONArray SensitiveTypeArray = new JSONArray();
        for (NewsResult newsSensitive : customWordRelateNewsSensitiveList) {
            JSONObject json = new JSONObject();
            json.put("name", SensitiveType.getDescription(newsSensitive.getIsSensitive()));
            json.put("value", newsSensitive.getNum());
            SensitiveTypeArray.add(json);
        }
        sensitiveTypeJson.put("sensitiveTypeData", SensitiveTypeArray);
        localCache.put(cacheKey, sensitiveTypeJson);
        return sensitiveTypeJson;
    }

    /**
     * 主题相关页 -- 敏感类别
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/relate/news/sensitive/category", method = RequestMethod.GET)
    public JSONObject sensitiveCategory(CustomWordParam customWordParam) {

        String cacheKey = "sensitive_category_" + customWordParam.getId() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource()+"_"+customWordParam.getSensitiveWords()+"_"+customWordParam.getKeyWords() + "_" + customWordParam.getTimeLimit();
        JSONObject sensitiveCategoryCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveCategoryCache != null) {
            return sensitiveCategoryCache;
        }
        CustomWord customWord = customWordService.getById(customWordParam.getId());
        //将中文主题词翻译成少数民族语言
        //List<String> customWordNames = customWordService.translateCnCustomwordName(customWord.getName(),customWordParam.getLang());
        List<String> customWordNames = Stream.of(customWord.getName()).collect(Collectors.toList());
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNames);
        List<NewsResult> customWordRelateNewsSensitiveCategoryList = new ArrayList<NewsResult>();
        if(ToolUtil.isNotEmpty(newsIdList)){
            customWordRelateNewsSensitiveCategoryList = newsService.customWordRelateNewsSensitiveCategoryList(newsIdList, customWordParam);
        }
        //主题词相关新闻敏感类别
        JSONObject sensitiveCategoryJson = new JSONObject();
        JSONArray sensitiveCategoryArray = new JSONArray();
        for (NewsResult sensitiveCategory : customWordRelateNewsSensitiveCategoryList) {
            JSONObject json = new JSONObject();
            json.put("name", SensitiveCategory.getDescription(sensitiveCategory.getSensitiveCategory()));
            json.put("value", sensitiveCategory.getNum());
            sensitiveCategoryArray.add(json);
        }
        sensitiveCategoryJson.put("sensitiveCategoryData", sensitiveCategoryArray);
        localCache.put(cacheKey, sensitiveCategoryJson);
        return sensitiveCategoryJson;
    }

    /**
     * 所属领域相关新闻查询列表
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/field/list")
    public LayuiPageInfo fieldList(CustomWordParam customWordParam) {

        HttpServletRequest request = HttpContext.getRequest();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String cacheKey = "customWord_field_list_" + customWordParam.getType() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource() + "_" +customWordParam.getKeyWords()+"_"+customWordParam.getSensitiveWords()+"_"+ customWordParam.getTimeLimit() + "_" + page + "_" + limit;
        LayuiPageInfo fieldNewsPageCache = ( LayuiPageInfo ) localCache.getIfPresent(cacheKey);
        if (fieldNewsPageCache != null) {
            return fieldNewsPageCache;
        }
        //查询领域包含的主题词
        List<String> customWordNameList = customWordService.findListBySpec(customWordParam).stream().map(CustomWordResult::getName).collect(Collectors.toList());
        //将领域包含的主题词翻译成少数民族语言
        //customWordNameList = customWordService.translateCnCustomwordNames(customWordNameList, customWordParam.getLang());
        customWordParam.setNames(customWordNameList);
        //查询主题词相关新闻id
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNameList);
        LayuiPageInfo fieldNewsPage = new LayuiPageInfo();
        if (newsIdList == null) {
            return fieldNewsPage;
        }
        //查询新闻列表
        fieldNewsPage = newsService.selectPage(newsIdList, customWordParam);
        localCache.put(cacheKey, fieldNewsPage);
        return fieldNewsPage;
    }

    /**
     * 主题词所属领域 - 新闻类型
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/field/sensitive/type")
    public JSONObject fieldSensitiveType(CustomWordParam customWordParam) {
        String cacheKey = "field_sen_type_" + customWordParam.getType() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource() + "_" +customWordParam.getSensitiveWords()+"_"+customWordParam.getKeyWords()+"_"+ customWordParam.getTimeLimit();
        JSONObject sensitiveTypeCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveTypeCache != null) {
            return sensitiveTypeCache;
        }
        //查询领域包含的主题词
        List<String> customWordNameList = customWordService.findListBySpec(customWordParam).stream().map(CustomWordResult::getName).collect(Collectors.toList());
        //将领域包含的主题词翻译成少数民族语言
        //customWordNameList = customWordService.translateCnCustomwordNames(customWordNameList, customWordParam.getLang());
        customWordParam.setNames(customWordNameList);
        //查询主题词相关新闻id
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNameList);
        List<NewsResult> customWordFieldNewsSensitiveList = new ArrayList<NewsResult>();
        if (ToolUtil.isNotEmpty(newsIdList)) {
            customWordFieldNewsSensitiveList = newsService.customWordRelateNewsSensitiveList(newsIdList, customWordParam);
        }
        //主题词相关新闻类型
        JSONObject sensitiveTypeJson = new JSONObject();
        JSONArray SensitiveTypeArray = new JSONArray();
        for (NewsResult newsSensitive : customWordFieldNewsSensitiveList) {
            JSONObject json = new JSONObject();
            json.put("name", SensitiveType.getDescription(newsSensitive.getIsSensitive()));
            json.put("value", newsSensitive.getNum());
            SensitiveTypeArray.add(json);
        }
        sensitiveTypeJson.put("sensitiveTypeData", SensitiveTypeArray);
        localCache.put(cacheKey, sensitiveTypeJson);
        return sensitiveTypeJson;
    }

    /**
     * 主题词所属领域 - 敏感类别
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/field/news/sensitive/category")
    public JSONObject fieldSensitiveCategory(CustomWordParam customWordParam) {
        String cacheKey = "field_news_sen_category_" + customWordParam.getType() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource() + "_" +customWordParam.getSensitiveWords()+"_"+customWordParam.getKeyWords()+"_"+ customWordParam.getTimeLimit();
        JSONObject sensitiveTypeCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (sensitiveTypeCache != null) {
            return sensitiveTypeCache;
        }
        //查询领域包含的主题词
        List<String> customWordNameList = customWordService.findListBySpec(customWordParam).stream().map(CustomWordResult::getName).collect(Collectors.toList());
        //将领域包含的主题词翻译成少数民族语言
        //customWordNameList = customWordService.translateCnCustomwordNames(customWordNameList, customWordParam.getLang());
        customWordParam.setNames(customWordNameList);
        //查询主题词相关新闻id
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNameList);
        List<NewsResult> customWordRelateNewsSensitiveCategoryList = new ArrayList<NewsResult>();
        if(ToolUtil.isNotEmpty(newsIdList)){
            customWordRelateNewsSensitiveCategoryList = newsService.customWordRelateNewsSensitiveCategoryList(newsIdList, customWordParam);
        }
        //主题词相关新闻敏感类别
        JSONObject sensitiveCategoryJson = new JSONObject();
        JSONArray sensitiveCategoryArray = new JSONArray();
        for (NewsResult sensitiveCategory : customWordRelateNewsSensitiveCategoryList) {
            JSONObject json = new JSONObject();
            json.put("name", SensitiveCategory.getDescription(sensitiveCategory.getSensitiveCategory()));
            json.put("value", sensitiveCategory.getNum());
            sensitiveCategoryArray.add(json);
        }
        sensitiveCategoryJson.put("sensitiveCategoryData", sensitiveCategoryArray);
        localCache.put(cacheKey, sensitiveCategoryJson);
        return sensitiveCategoryJson;
    }

    /**
     * 主题词所属领域 - 新闻来源
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/field/news/source")
    public JSONObject fieldNewsSource(CustomWordParam customWordParam) {
        String cacheKey = "field_news_source_" + customWordParam.getType() + "_" + customWordParam.getLang() + "_" + customWordParam.getIsSensitive() + "_" + customWordParam.getSensitiveCategory() + "_" + customWordParam.getNewsSource() + "_" +customWordParam.getKeyWords()+"_"+customWordParam.getSensitiveWords()+"_"+ customWordParam.getTimeLimit();
        JSONObject newsSourceCache = ( JSONObject ) localCache.getIfPresent(cacheKey);
        if (newsSourceCache != null) {
            return newsSourceCache;
        }
        //查询领域包含的主题词
        List<String> customWordNameList = customWordService.findListBySpec(customWordParam).stream().map(CustomWordResult::getName).collect(Collectors.toList());
        //将领域包含的主题词翻译成少数民族语言
        //customWordNameList = customWordService.translateCnCustomwordNames(customWordNameList, customWordParam.getLang());
        customWordParam.setNames(customWordNameList);
        //查询主题词相关新闻id
        List<Integer> newsIdList = getNewsIdsByCustomWordNames(customWordNameList);
        List<NewsResult> customWordRelateNewsSourceList = new ArrayList<NewsResult>();
        if(ToolUtil.isNotEmpty(newsIdList)){
            customWordRelateNewsSourceList = newsService.customWordRelateNewsSourceList(newsIdList, customWordParam);
        }
        //主题词相关新闻来源
        JSONObject newsSourceJson = new JSONObject();
        JSONArray newsSourceArray = new JSONArray();
        for (NewsResult newsSource : customWordRelateNewsSourceList) {
            JSONObject json = new JSONObject();
            json.put("value", newsSource.getNum());
            json.put("name", newsSource.getWebsitename());
            newsSourceArray.add(json);
        }
        newsSourceJson.put("newsSourceData", newsSourceArray);
        localCache.put(cacheKey, newsSourceJson);
        return newsSourceJson;
    }

    /**
     * 修改主题词状态
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @ResponseBody
    @RequestMapping("/changeStatus")
    public ResponseData changeStatus(@RequestParam("customWordId") String customWordId, @RequestParam("status") Boolean status) {

        CustomWord customWord = this.customWordService.getById(customWordId);
        if (customWord == null) {
            throw new RequestEmptyException();
        }
        if (status) {
            customWord.setStatus(CommonStatus.ENABLE.getCode());
        } else {
            customWord.setStatus(CommonStatus.DISABLE.getCode());
        }
        this.customWordService.updateById(customWord);
        return new SuccessResponseData();
    }

    /**
     * 定制主题（标签）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/tab", method = RequestMethod.GET)
    public JSONObject sensitiveCategoryTab(CustomWordParam customWordParam) {

        //主题词名，分权限、状态 {"id":[],"name":[]}
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("create_user", LoginContextHolder.getContext().getUserId());
        queryWrapper.eq("status", "ENABLE");
        List<CustomWord> customWordList = customWordService.list(queryWrapper);

        JSONObject customWordJson = new JSONObject();
        List<Long> idList = customWordList.stream().map(CustomWord::getId).collect(Collectors.toList());
        customWordJson.put("ids", JSONArray.parseArray(JSON.toJSONString(idList)));
        List<String> nameList = customWordList.stream().map(CustomWord::getName).collect(Collectors.toList());
        customWordJson.put("names", JSONArray.parseArray(JSON.toJSONString(nameList)));

        return customWordJson;
    }

    //查询主题词对应的新闻
    public List<Integer> getNewsIdsByCustomWordNames(List<String> customWordNames) {

        if (customWordNames.size() <= 0) {
            return null;
        }
        String cacheKey = "news_ids_by_" + customWordNames;
        List<Integer> newsIds = ( List<Integer> ) localCache.getIfPresent(cacheKey);
        if (newsIds != null) {
            return newsIds;
        }
        //查询entityIdList
        QueryWrapper entityQueryWrapper = new QueryWrapper();
        entityQueryWrapper.in("entity_key", customWordNames);
        List<Entity> entityResultList = entityService.list(entityQueryWrapper);

        if(entityResultList.size() <= 0){
            return null;
        }

        //查询newsIdList
        QueryWrapper entityNewsRelationQueryWrapper = new QueryWrapper();
        List<Integer> entityIdList = entityResultList.stream().map(Entity::getEntityId).collect(Collectors.toList());
        entityNewsRelationQueryWrapper.in("entity_id", entityIdList);
        List<EntityNewsRelation> entityNewsRelationList = entityNewsRelationService.list(entityNewsRelationQueryWrapper);
        List<Integer> newsIdList = entityNewsRelationList.stream().map(EntityNewsRelation::getNewsId).collect(Collectors.toList());
        localCache.put(cacheKey, newsIdList);
        return newsIdList;
    }

}



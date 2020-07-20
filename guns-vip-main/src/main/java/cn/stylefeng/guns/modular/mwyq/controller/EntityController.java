package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Entity;
import cn.stylefeng.guns.modular.mwyq.entity.EntityNewsRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityParam;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityResult;
import cn.stylefeng.guns.modular.mwyq.service.EntityNewsRelationService;
import cn.stylefeng.guns.modular.mwyq.service.EntityService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 控制器
 *
 * @author jinbo
 * @Date 2020-06-14 10:03:33
 */
@Controller
@RequestMapping("/entity")
public class EntityController extends BaseController {

    private String PREFIX = "/entity";

    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityNewsRelationService entityNewsRelationService;

    /**
     * 跳转到主页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/entity.html";
    }

    /**
     * 新增页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/entity_add.html";
    }

    /**
     * 编辑页面
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/entity_edit.html";
    }

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(EntityParam entityParam) {
        this.entityService.add(entityParam);
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
    public ResponseData editItem(EntityParam entityParam) {
        this.entityService.update(entityParam);
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
    public ResponseData delete(EntityParam entityParam) {
        this.entityService.delete(entityParam);
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
    public ResponseData detail(EntityParam entityParam) {
        Entity detail = this.entityService.getById(entityParam.getEntityId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(EntityParam entityParam) {
        return this.entityService.findPageBySpec(entityParam);
    }


    /**
     * 实体统计（柱状图）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/entityStatic", method = RequestMethod.GET)
    public JSONObject sensitiveCategory(EntityParam entityParam) {

        JSONObject jsonObject = new JSONObject();
        JSONArray entityNameArray = new JSONArray();
        JSONArray entityNumArray = new JSONArray();
        List<EntityResult> entityResultList = entityService.findListBySpec(entityParam);
        for (EntityResult entityResult : entityResultList) {
            entityNameArray.add(entityResult.getEntityKey());
            entityNumArray.add(entityResult.getCount());
        }
        jsonObject.put("entityName", entityNameArray);
        jsonObject.put("entityNum", entityNumArray);
        return jsonObject;
    }

    /**
     * 词云数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    @ResponseBody
    @RequestMapping(value = "/wordcloud", method = RequestMethod.GET)
    public JSONObject getEntityLocKey(NewsParam newsParam) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("news_id", newsParam.getNewsId());
        List<EntityNewsRelation> entityNewsRelationList = entityNewsRelationService.list(queryWrapper);
        if (entityNewsRelationList.isEmpty()) {
            return null;
        }
        List<Integer> entityIds = entityNewsRelationList.stream().map(EntityNewsRelation::getEntityId).collect(Collectors.toList());
        List<Entity> entityList = entityService.listByIds(entityIds);
        JSONObject wordCloudJson = new JSONObject();
        JSONArray personArray = new JSONArray();
        JSONArray locationArray = new JSONArray();
        JSONArray organizationArray = new JSONArray();
        for (Entity entity : entityList) {
            String entityType = entity.getEntityType();
            String entityKey = entity.getEntityKey();
            Integer count = entity.getCount();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", entityKey);
            jsonObject.put("value", count);
            if (entityType.equals("PER")) {
                personArray.add(jsonObject);
            } else if (entityType.equals("LOC")) {
                locationArray.add(jsonObject);
            } else if (entityType.equals("ORG")) {
                organizationArray.add(jsonObject);
            }
        }
        wordCloudJson.put("person", personArray);
        wordCloudJson.put("location", locationArray);
        wordCloudJson.put("organization", organizationArray);
        return wordCloudJson;
    }

}



package cn.stylefeng.guns.sys.modular.rest.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.rest.entity.RestDictType;
import cn.stylefeng.guns.sys.modular.rest.service.RestDictTypeService;
import cn.stylefeng.guns.sys.modular.system.model.params.DictTypeParam;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 字典类型表控制器
 *
 * @author jinbo
 * @Date 2019-03-13 1:54
 */
@RestController
@RequestMapping("/rest/dictType")
public class RestDictTypeController extends BaseController {

    @Autowired
    private RestDictTypeService restDictTypeService;

    /**
     * 新增接口
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/addItem")
    public ResponseData addItem(@RequestBody DictTypeParam dictTypeParam) {
        this.restDictTypeService.add(dictTypeParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/editItem")
    public ResponseData editItem(@RequestBody DictTypeParam dictTypeParam) {
        this.restDictTypeService.update(dictTypeParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/delete")
    public ResponseData delete(@RequestBody DictTypeParam dictTypeParam) {
        this.restDictTypeService.delete(dictTypeParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/detail")
    public ResponseData detail(@RequestBody DictTypeParam dictTypeParam) {
        RestDictType detail = this.restDictTypeService.getById(dictTypeParam.getDictTypeId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/list")
    public LayuiPageInfo list(@RequestBody DictTypeParam dictTypeParam) {
        return this.restDictTypeService.findPageBySpec(dictTypeParam);
    }

    /**
     * 查询所有字典
     *
     * @author jinbo
     * @Date 2019-03-13
     */
    @RequestMapping("/listTypes")
    public ResponseData listTypes() {

        QueryWrapper<RestDictType> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.select("dict_type_id", "code", "name");

        List<RestDictType> list = this.restDictTypeService.list(objectQueryWrapper);
        return new SuccessResponseData(list);
    }

}



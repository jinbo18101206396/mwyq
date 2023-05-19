package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Toutiao;
import cn.stylefeng.guns.modular.mwyq.model.params.ToutiaoParam;
import cn.stylefeng.guns.modular.mwyq.service.ToutiaoService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 控制器
 *
 * @author 金波
 * @Date 2023-05-18 12:58:02
 */
@Controller
@RequestMapping("/toutiao")
public class ToutiaoController extends BaseController {

    private String PREFIX = "/toutiao";

    @Autowired
    private ToutiaoService toutiaoService;

    /**
     * 跳转到主页面
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/toutiao.html";
    }

    /**
     * 新增页面
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/toutiao_add.html";
    }

    /**
     * 编辑页面
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/toutiao_edit.html";
    }

    /**
     * 新增接口
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ToutiaoParam toutiaoParam) {
        this.toutiaoService.add(toutiaoParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ToutiaoParam toutiaoParam) {
        this.toutiaoService.update(toutiaoParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ToutiaoParam toutiaoParam) {
        this.toutiaoService.delete(toutiaoParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ToutiaoParam toutiaoParam) {
        Toutiao detail = this.toutiaoService.getById(toutiaoParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author 金波
     * @Date 2023-05-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ToutiaoParam toutiaoParam) {
        return this.toutiaoService.findPageBySpec(toutiaoParam);
    }

}



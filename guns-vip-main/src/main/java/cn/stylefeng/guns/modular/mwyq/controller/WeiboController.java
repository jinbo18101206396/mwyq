package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.service.WeiboService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
     * 查询列表
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(WeiboParam weiboParam) {
        return this.weiboService.findPageBySpec(weiboParam);
    }

}



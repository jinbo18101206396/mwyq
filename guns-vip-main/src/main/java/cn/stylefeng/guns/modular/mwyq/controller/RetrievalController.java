package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/retrieval")
@Slf4j
public class RetrievalController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RetrievalController.class);

    private String PREFIX = "/retrieval";

    @RequestMapping("")
    public String index() {
        return PREFIX + "/retrieval.html";
    }

    @RequestMapping("/weibo")
    public String weiboSearch() {

        return PREFIX + "/weibo.html";
    }

    @RequestMapping("/website")
    public String websiteSearch() {

        return PREFIX + "/website.html";
    }

}

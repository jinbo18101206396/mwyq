/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.mwyq.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 主页
 *
 * @author
 * @Date
 */
@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController extends BaseController {

    private String PREFIX = "/home";

    private static final Cache<String, Object> localCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.DAYS).recordStats().build();


    @RequestMapping("")
    public String index() {
        return PREFIX + "/home.html";
    }

    /**
     * 新闻详情页面(热门新闻、敏感新闻、话题相关新闻、宗教新闻、定制新闻、主题词相关新闻)
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/news/detail")
    public String newsDetailPage(@RequestParam("newsId") Integer newsId, @RequestParam("lang") String lang) {
        if (lang.equals("cn")) {
            return PREFIX + "/home_news_detail.html";
        }
        return PREFIX + "/home_news_detail_little.html";
    }

    /**
     * 话题详情页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/topic/detail")
    public String topicDetailPage(String topicId, String topicName, String langType, String topwords, int newsCount, String newsTime,String summarize,Model model) {

        model.addAttribute("topicId", topicId);
        model.addAttribute("langType", langType);
        model.addAttribute("topicName", topicName);
        model.addAttribute("topwords", topwords);
        model.addAttribute("newsCount", newsCount);
        model.addAttribute("summarize", summarize);
        model.addAttribute("newsTime", newsTime);

        return PREFIX + "/home_topic_detail.html";
    }

    /**
     * 敏感新闻详情页面
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    @RequestMapping("/sensitive/detail")
    public String sensitiveDetailPage(@RequestParam("newsId") Integer newsId, @RequestParam("lang") String lang) {
        if (lang.equals("cn")) {
            return PREFIX + "/home_sensitive_detail.html";
        }
        return PREFIX + "/home_sensitive_detail_little.html";
    }
}

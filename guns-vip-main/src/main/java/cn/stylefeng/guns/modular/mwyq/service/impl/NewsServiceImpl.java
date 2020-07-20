package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.mapper.EntityMapper;
import cn.stylefeng.guns.modular.mwyq.mapper.EntityNewsRelationMapper;
import cn.stylefeng.guns.modular.mwyq.mapper.NewsMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicParam;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsStaticResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsTrendResult;
import cn.stylefeng.guns.modular.mwyq.service.NewsService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private EntityNewsRelationMapper entityNewsRelationMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public void add(NewsParam param) {
        News entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(NewsParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(NewsParam param) {
        News oldEntity = getOldEntity(param);
        News newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public NewsResult findBySpec(NewsParam param) {
        return null;
    }

    @Override
    public List<NewsResult> findListBySpec(NewsParam param) {
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(NewsParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo selectPage(List<Integer> ids, TopicParam topicParam) {

        QueryWrapper queryWrapper = new QueryWrapper();
        if (ToolUtil.isNotEmpty(ids)) {
            queryWrapper.in("news_id", ids);
        }
        String langType = topicParam.getLangType();
        if (ToolUtil.isNotEmpty(langType)) {
            queryWrapper.eq("lang_type", langType);
        }
        Integer sensitive = topicParam.getIsSensitive();
        if(ToolUtil.isNotEmpty(sensitive)){
            queryWrapper.eq("is_sensitive",sensitive);
        }
        Integer sensitiveCategory = topicParam.getSensitiveCategory();
        if(ToolUtil.isNotEmpty(sensitiveCategory)){
            queryWrapper.eq("sensitive_category",sensitiveCategory);
        }
        String websitename = topicParam.getWebsitename();
        if(ToolUtil.isNotEmpty(websitename)){
            queryWrapper.like("websitename",websitename);
        }
        String timeLimit = topicParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            queryWrapper.between("news_time",split[0],split[1]);
        }
        queryWrapper.orderByDesc("news_time");
        Page pageContext = getPageContext();
        IPage page = newsMapper.selectPage(pageContext, queryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo selectPage(List<Integer> ids, CustomWordParam customWordParam) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (ToolUtil.isNotEmpty(ids)) {
            queryWrapper.in("news_id", ids);
        }
        String lang = customWordParam.getLang();
        if (ToolUtil.isNotEmpty(lang)) {
            queryWrapper.eq("lang_type", lang);
        }
        Date createTime = customWordParam.getCreateTime();
        if (ToolUtil.isNotEmpty(createTime)) {
            queryWrapper.ge("news_time", createTime);
        }
        String timeLimit = customWordParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            queryWrapper.between("news_time", split[0], split[1]);
        }
        Integer sensitive = customWordParam.getIsSensitive();
        if (ToolUtil.isNotEmpty(sensitive)) {
            queryWrapper.eq("is_sensitive", sensitive);
        }
        Integer sensitiveCategory = customWordParam.getSensitiveCategory();
        if (ToolUtil.isNotEmpty(sensitiveCategory)) {
            queryWrapper.eq("sensitive_category", sensitiveCategory);
        }
        String newsSource = customWordParam.getNewsSource();
        if (ToolUtil.isNotEmpty(newsSource)) {
            queryWrapper.like("websitename", newsSource);
        }
        String newsAuthor = customWordParam.getNewsAuthor();
        if (ToolUtil.isNotEmpty(newsAuthor)) {
            queryWrapper.like("news_author", newsAuthor);
        }
        queryWrapper.orderByDesc("news_time");
        Page pageContext = getPageContext();
        IPage page = newsMapper.selectPage(pageContext, queryWrapper);
        List<News> records = page.getRecords();

        List<String> names = customWordParam.getNames();
        for (News record : records) {
            String sensitiveWords = record.getSensitiveWords();
            if (ToolUtil.isNotEmpty(sensitiveWords) && sensitiveWords.endsWith("*")) {
                sensitiveWords = sensitiveWords.substring(0, sensitiveWords.length() - 1);
                record.setSensitiveWords(sensitiveWords.replace("*", ","));
            }
            //新闻列表中标出领域主题词
            String newsTitle = record.getNewsTitle();
            for (String name : names) {
                if (newsTitle.contains(name)) {
                    record.setNewsTitle(newsTitle.replaceAll(name, "<span style=\"color:red;\">" + name + "</span>"));
                }
            }
        }
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public List<NewsResult> newsSourceList(NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.newsSourceList(newsParam);
    }

    @Override
    public List<NewsResult> newsDistributionList(NewsParam newsParam) {
        return newsMapper.newsDistributionList(newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSourceList(List<Integer> list, NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.relateNewsSourceList(list, newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSourceList(List<Integer> ids, CustomWordParam customWordParam) {
        NewsParam newsParam = new NewsParam();
        String timeLimit = customWordParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        newsParam.setNewsTime(customWordParam.getCreateTime());
        newsParam.setLangType(customWordParam.getLang());
        newsParam.setIds(ids);
        newsParam.setIsSensitive(customWordParam.getIsSensitive());
        newsParam.setSensitiveCategory(customWordParam.getSensitiveCategory());
        newsParam.setWebsitename(customWordParam.getNewsSource());
        return newsMapper.relateNewsSourceList(ids, newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSensitiveList(List<Integer> list, NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.sensitiveTypeListByIds(list, newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSensitiveList(List<Integer> ids, CustomWordParam customWordParam) {

        NewsParam newsParam = new NewsParam();
        String timeLimit = customWordParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        newsParam.setNewsTime(customWordParam.getCreateTime());
        newsParam.setLangType(customWordParam.getLang());
        newsParam.setIds(ids);
        newsParam.setIsSensitive(customWordParam.getIsSensitive());
        newsParam.setSensitiveCategory(customWordParam.getSensitiveCategory());
        newsParam.setWebsitename(customWordParam.getNewsSource());
        return newsMapper.sensitiveTypeListByIds(ids, newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSensitiveCategoryList(List<Integer> list, NewsParam newsParam) {
        return newsMapper.sensitiveCategoryListByIds(list, newsParam);
    }

    @Override
    public List<NewsResult> customWordRelateNewsSensitiveCategoryList(List<Integer> ids, CustomWordParam customWordParam) {
        NewsParam newsParam = new NewsParam();
        String timeLimit = customWordParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        newsParam.setLangType(customWordParam.getLang());
        newsParam.setIds(ids);
        newsParam.setIsSensitive(customWordParam.getIsSensitive());
        newsParam.setSensitiveCategory(customWordParam.getSensitiveCategory());
        newsParam.setWebsitename(customWordParam.getNewsSource());

        return newsMapper.sensitiveCategoryListByIds(ids, newsParam);
    }

    @Override
    public List<NewsResult> topicRelateNewsSourceList(List<Integer> ids, TopicParam topicParam) {

        NewsParam newsParam = new NewsParam();
        newsParam.setIds(ids);
        newsParam.setLangType(topicParam.getLangType());
        newsParam.setIsSensitive(topicParam.getIsSensitive());
        newsParam.setSensitiveCategory(topicParam.getSensitiveCategory());
        newsParam.setWebsitename(topicParam.getWebsitename());
        String timeLimit = topicParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.relateNewsSourceList(ids, newsParam);
    }

    @Override
    public List<NewsResult> sensitiveTypeList(NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.sensitiveTypeList(newsParam);
    }

    @Override
    public List<NewsResult> newsCategoryList(NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.newsCategoryList(newsParam);
    }

    @Override
    public List<NewsResult> sensitiveTypeListByIds(List<Integer> ids, TopicParam topicParam) {
        NewsParam newsParam = new NewsParam();
        newsParam.setIds(ids);
        newsParam.setLangType(topicParam.getLangType());
        newsParam.setIsSensitive(topicParam.getIsSensitive());
        newsParam.setSensitiveCategory(topicParam.getSensitiveCategory());
        newsParam.setWebsitename(topicParam.getWebsitename());
        String timeLimit = topicParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.sensitiveTypeListByIds(ids,newsParam);
    }

    @Override
    public List<NewsResult> sensitiveCategoryList(NewsParam newsParam) {
        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        return newsMapper.sensitiveCategoryList(newsParam);
    }

    @Override
    public List<NewsStaticResult> newsStaticList() {
        return newsMapper.newsStaticList();
    }

    @Override
    public List<NewsTrendResult> newsTrendList() {
        return newsMapper.newsTrendList();
    }

    @Override
    public LayuiPageInfo hotPageList(NewsParam newsParam) {

        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        Page<NewsResult> page = newsMapper.hotPageList(pageContext, newsParam);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public LayuiPageInfo sensitivePageList(NewsParam newsParam) {

        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        Page<NewsResult> page = newsMapper.sensitivePageList(pageContext, newsParam);
        return LayuiPageFactory.createPageInfo(page);
    }



    @Override
    public LayuiPageInfo selectPage(NewsParam newsParam) {

        String timeLimit = newsParam.getTimeLimit();
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            newsParam.setBeginTime(split[0]);
            newsParam.setEndTime(split[1]);
        }
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.sensitivePageList(pageContext, newsParam);
        List<NewsResult> records = page.getRecords();
        for (NewsResult record : records) {
            String sensitiveWords = record.getSensitiveWords();
            if (ToolUtil.isNotEmpty(sensitiveWords) && sensitiveWords.endsWith("*")) {
                sensitiveWords = sensitiveWords.substring(0, sensitiveWords.length() - 1);
                record.setSensitiveWords(sensitiveWords.replace("*", ","));
            }
        }
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(NewsParam param) {
        return param.getNewsId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private News getOldEntity(NewsParam param) {
        return this.getById(getKey(param));
    }

    private News getEntity(NewsParam param) {
        News entity = new News();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }
}

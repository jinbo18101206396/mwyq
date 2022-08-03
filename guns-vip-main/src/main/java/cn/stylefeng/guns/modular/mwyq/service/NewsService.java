package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicParam;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsStaticResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsTrendResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
public interface NewsService extends IService<News> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void add(NewsParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void delete(NewsParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void update(NewsParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    NewsResult findBySpec(NewsParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> findListBySpec(NewsParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
     LayuiPageInfo findPageBySpec(NewsParam param);

    /**
     * 话题新闻
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    LayuiPageInfo selectPage(List<Integer> ids,TopicParam topicParam);

    /**
     * 敏感分析模块数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public LayuiPageInfo selectPage(NewsParam param);

    /**
     * 主题词模块数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    LayuiPageInfo selectPage(List<Integer> ids,CustomWordParam customWordParam);

    /**
     * 新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> newsSourceList(NewsParam newsParam);

    /**
     * 新闻来源数据(首页全局)
     *
     * @author jinbo
     * @Date 2020-08-02
     */
    public List<NewsResult> newsSourceGlobalList(NewsParam newsParam);

    /**
     * 宗教新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> religionNewsSourceList(NewsParam newsParam);

    /**
     * 新闻分布数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> newsDistributionList(NewsParam newsParam);

    /**
     * 敏感分布数据(全局)
     *
     * @author jinbo
     * @Date 2022-08-02
     */
    public List<NewsResult> senDistributionList(NewsParam newsParam);

    /**
     * 主题词相关新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSourceList(List<Integer> list,NewsParam newsParam);

    /**
     * 主题词相关新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSourceList(List<Integer> list,CustomWordParam customWordParam);

    /**
     * 主题词相关新闻敏感类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSensitiveList(List<Integer> list,NewsParam newsParam);

    /**
     * 主题词相关新闻敏感类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSensitiveList(List<Integer> list,CustomWordParam customWordParam);

    /**
     * 主题词相关新闻敏感类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSensitiveCategoryList(List<Integer> list,NewsParam newsParam);

    /**
     * 主题词相关新闻敏感类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> customWordRelateNewsSensitiveCategoryList(List<Integer> list,CustomWordParam customWordParam);

    /**
     * 热门话题相关新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> topicRelateNewsSourceList(List<Integer> list,TopicParam topicParam);

    /**
     * 新闻类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> sensitiveTypeList(NewsParam newsParam);

    /**
     * 新闻类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> newsCategoryList(NewsParam newsParam);

    /**
     * 新闻类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> sensitiveTypeListByIds(List<Integer> list,TopicParam topicParam);

    /**
     * 宗教新闻类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> religionSensitiveTypeList(NewsParam newsParam);

    /**
     * 敏感类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> sensitiveCategoryList(NewsParam newsParam);

    /**
     * 新闻来源（常规新闻页）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> sensitiveSourceList(NewsParam newsParam);

    /**
     * 敏感类别数据(首页，敏感新闻)
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsResult> homeSensitiveCategoryList(NewsParam newsParam);

    /**
     * 新闻数量统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsStaticResult> newsStaticList();

    /**
     * 敏感趋势统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsTrendResult> newsTrendList();

    /**
     * 情感分析>>情感走势
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public List<NewsTrendResult> sensitiveNewsTrendList(NewsParam newsParam);


    /**
     * 热门新闻
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public LayuiPageInfo hotPageList(NewsParam newsParam);

    /**
     * 敏感新闻
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public LayuiPageInfo sensitivePageList(NewsParam newsParam);

    /**
     * 敏感新闻(首页)
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public LayuiPageInfo homeSensitivePageList(NewsParam newsParam);

    /**
     * 宗教新闻
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    public LayuiPageInfo religionPageList(NewsParam newsParam);
}

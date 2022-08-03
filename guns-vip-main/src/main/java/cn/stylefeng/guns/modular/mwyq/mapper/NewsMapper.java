package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.News;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsStaticResult;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsTrendResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2020-06-14
 */
public interface NewsMapper extends BaseMapper<News> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> customList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻来源数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> newsSourceList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻来源数据(首页全局)
     *
     * @author jinbo
     * @Date 2020-08-02
     */
    List<NewsResult> newsSourceGlobalList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻分布数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> newsDistributionList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感分布数据（全局）
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> senDistributionList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取主题相关新闻来源
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> relateNewsSourceList(@Param("list")List<Integer> list,@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻类型数据(常规新闻页)
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> sensitiveTypeList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻类型数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> sensitiveTypeListByIds(@Param("list")List<Integer> list,@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> newsCategoryList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> sensitiveCategoryList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取新闻来源（常规新闻页）
     * @author jinbo
     * @Date 2020-08-02
     */
    List<NewsResult> sensitiveSourceList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感类别数据(首页，敏感新闻)
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> homeSensitiveCategoryList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感类别数据
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsResult> sensitiveCategoryListByIds(@Param("list")List<Integer> list,@Param("paramCondition") NewsParam paramCondition);


    /**
     * 新闻数量统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsStaticResult> newsStaticList();

    /**
     * 首页>>敏感趋势统计
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsTrendResult> newsTrendList();

    /**
     * 情感分析>>情感走势
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<NewsTrendResult> sensitiveNewsTrendList(@Param("paramCondition") NewsParam paramCondition);


    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<NewsResult> customPageList(@Param("page") Page page, @Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感新闻列表
     *
     * @author jinbo
     * @Date 2022-08-02
     */
    Page<NewsResult> homeSensitivePageList(@Param("page") Page page, @Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取敏感新闻列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<NewsResult> sensitivePageList(@Param("page") Page page, @Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") NewsParam paramCondition);

    /**
     * 获取热门新闻列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<NewsResult> hotPageList(@Param("page") Page page, @Param("paramCondition") NewsParam paramCondition);

}

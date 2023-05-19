package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Weibo;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboTrendResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinbo
 * @since 2020-12-16
 */
public interface WeiboService extends IService<Weibo> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    void add(WeiboParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    void delete(WeiboParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    void update(WeiboParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    WeiboResult findBySpec(WeiboParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    List<WeiboResult> findListBySpec(WeiboParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-12-16
     */
     LayuiPageInfo findPageBySpec(WeiboParam param);

    /**
     * 微博情感分布
     *
     * @author jinbo
     * @Date 2022-07-29
     */
    List<WeiboResult> sentimentTypeList(WeiboParam weiboParam);

    /**
     * 微博语言分布
     *
     * @author jinbo
     * @Date 2022-08-02
     */
    List<WeiboResult> langTypeList(WeiboParam weiboParam);

    /**
     * 博主排行
     *
     * @author jinbo
     * @Date 2022-07-29
     */
    List<WeiboResult> bloggerRankList(WeiboParam weiboParam);

    /**
     * 博主地域分布
     *
     * @author jinbo
     * @Date 2022-07-29
     */
    List<WeiboResult> areaMapList(WeiboParam weiboParam);

    /**
     * 微博情感走势
     *
     * @author jinbo
     * @Date 2022-07-29
     */
    List<WeiboTrendResult> sentimentTrendList(WeiboParam weiboParam);

    /**
     * 博主发文量的情感分布
     *
     * @author jinbo
     * @Date 2022-07-29
     */
    JSONObject getAuthorSentiment(WeiboParam weiboParam);

    WeiboResult getWeiboById(Serializable id);
}

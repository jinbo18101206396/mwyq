package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Weibo;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboTrendResult;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<WeiboResult> sentimentTypeList(WeiboParam weiboParam);

    List<WeiboResult> areaMapList(WeiboParam weiboParam);

    List<WeiboTrendResult> sentimentTrendList(WeiboParam weiboParam);
}

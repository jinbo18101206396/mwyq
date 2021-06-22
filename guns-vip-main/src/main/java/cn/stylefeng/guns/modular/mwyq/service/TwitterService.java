package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Twitter;
import cn.stylefeng.guns.modular.mwyq.model.params.TwitterParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TwitterResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinbo
 * @since 2021-06-22
 */
public interface TwitterService extends IService<Twitter> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    void add(TwitterParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    void delete(TwitterParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    void update(TwitterParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    TwitterResult findBySpec(TwitterParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> findListBySpec(TwitterParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2021-06-22
     */
     LayuiPageInfo findPageBySpec(TwitterParam param);

    /**
     * 推特作者排行
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> authorRankList(TwitterParam param);

    /**
     * 推特情感分析
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> sentimentList(TwitterParam param);

}

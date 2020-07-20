package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Topicminingdistribution;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicminingdistributionParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicminingdistributionResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinbo
 * @since 2020-07-01
 */
public interface TopicminingdistributionService extends IService<Topicminingdistribution> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void add(TopicminingdistributionParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void delete(TopicminingdistributionParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void update(TopicminingdistributionParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    TopicminingdistributionResult findBySpec(TopicminingdistributionParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicminingdistributionResult> findListBySpec(TopicminingdistributionParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
     LayuiPageInfo findPageBySpec(TopicminingdistributionParam param);

}

package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Topic;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicResult;
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
public interface TopicService extends IService<Topic> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void add(TopicParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void delete(TopicParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void update(TopicParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    TopicResult findBySpec(TopicParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicResult> findListBySpec(TopicParam param);

    /**
     * 话题数量
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicResult> topicCountList(TopicParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
     LayuiPageInfo findPageBySpec(TopicParam param);

}

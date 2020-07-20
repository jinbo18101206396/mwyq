package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.TopicDocumentRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicDocumentRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicDocumentRelationResult;
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
public interface TopicDocumentRelationService extends IService<TopicDocumentRelation> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void add(TopicDocumentRelationParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void delete(TopicDocumentRelationParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    void update(TopicDocumentRelationParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    TopicDocumentRelationResult findBySpec(TopicDocumentRelationParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicDocumentRelationResult> findListBySpec(TopicDocumentRelationParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-01
     */
     LayuiPageInfo findPageBySpec(TopicDocumentRelationParam param);

}

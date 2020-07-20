package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.EntityTopicRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityTopicRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityTopicRelationResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinbo
 * @since 2020-07-05
 */
public interface EntityTopicRelationService extends IService<EntityTopicRelation> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    void add(EntityTopicRelationParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    void delete(EntityTopicRelationParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    void update(EntityTopicRelationParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    EntityTopicRelationResult findBySpec(EntityTopicRelationParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    List<EntityTopicRelationResult> findListBySpec(EntityTopicRelationParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-07-05
     */
     LayuiPageInfo findPageBySpec(EntityTopicRelationParam param);

}

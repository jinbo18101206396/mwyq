package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.EntityNewsRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityNewsRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityNewsRelationResult;
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
public interface EntityNewsRelationService extends IService<EntityNewsRelation> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void add(EntityNewsRelationParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void delete(EntityNewsRelationParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void update(EntityNewsRelationParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    EntityNewsRelationResult findBySpec(EntityNewsRelationParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<EntityNewsRelationResult> findListBySpec(EntityNewsRelationParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
     LayuiPageInfo findPageBySpec(EntityNewsRelationParam param);

}

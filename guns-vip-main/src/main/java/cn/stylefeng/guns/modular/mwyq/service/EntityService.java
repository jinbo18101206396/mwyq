package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.mwyq.entity.Entity;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityResult;
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
public interface EntityService extends IService<Entity> {

    /**
     * 新增
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void add(EntityParam param);

    /**
     * 删除
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void delete(EntityParam param);

    /**
     * 更新
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    void update(EntityParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    EntityResult findBySpec(EntityParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<EntityResult> findListBySpec(EntityParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author jinbo
     * @Date 2020-06-14
     */
     LayuiPageInfo findPageBySpec(EntityParam param);

}

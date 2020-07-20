package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.Entity;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityResult;
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
public interface EntityMapper extends BaseMapper<Entity> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<EntityResult> customList(@Param("paramCondition") EntityParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntityParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<EntityResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntityParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntityParam paramCondition);

}

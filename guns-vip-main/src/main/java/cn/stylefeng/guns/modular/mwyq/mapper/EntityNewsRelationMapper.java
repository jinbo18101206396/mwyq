package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.EntityNewsRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityNewsRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityNewsRelationResult;
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
public interface EntityNewsRelationMapper extends BaseMapper<EntityNewsRelation> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<EntityNewsRelationResult> customList(@Param("paramCondition") EntityNewsRelationParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntityNewsRelationParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<EntityNewsRelationResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntityNewsRelationParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-06-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntityNewsRelationParam paramCondition);

}

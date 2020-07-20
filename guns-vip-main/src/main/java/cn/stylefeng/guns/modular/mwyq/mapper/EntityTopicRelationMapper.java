package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.EntityTopicRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.EntityTopicRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.EntityTopicRelationResult;

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
 * @since 2020-07-05
 */
public interface EntityTopicRelationMapper extends BaseMapper<EntityTopicRelation> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    List<EntityTopicRelationResult> customList(@Param("paramCondition") EntityTopicRelationParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntityTopicRelationParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    Page<EntityTopicRelationResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntityTopicRelationParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-07-05
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntityTopicRelationParam paramCondition);

}

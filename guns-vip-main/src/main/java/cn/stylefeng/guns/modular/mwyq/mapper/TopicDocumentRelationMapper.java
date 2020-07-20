package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.TopicDocumentRelation;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicDocumentRelationParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicDocumentRelationResult;
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
 * @since 2020-07-01
 */
public interface TopicDocumentRelationMapper extends BaseMapper<TopicDocumentRelation> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicDocumentRelationResult> customList(@Param("paramCondition") TopicDocumentRelationParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") TopicDocumentRelationParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    Page<TopicDocumentRelationResult> customPageList(@Param("page") Page page, @Param("paramCondition") TopicDocumentRelationParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") TopicDocumentRelationParam paramCondition);

}

package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.Topicminingdistribution;
import cn.stylefeng.guns.modular.mwyq.model.params.TopicminingdistributionParam;
import cn.stylefeng.guns.modular.mwyq.model.result.TopicminingdistributionResult;
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
public interface TopicminingdistributionMapper extends BaseMapper<Topicminingdistribution> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<TopicminingdistributionResult> customList(@Param("paramCondition") TopicminingdistributionParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") TopicminingdistributionParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    Page<TopicminingdistributionResult> customPageList(@Param("page") Page page, @Param("paramCondition") TopicminingdistributionParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-07-01
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") TopicminingdistributionParam paramCondition);

}

package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.CustomWord;
import cn.stylefeng.guns.modular.mwyq.model.params.CustomWordParam;
import cn.stylefeng.guns.modular.mwyq.model.result.CustomWordResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定制词表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2020-06-11
 */
public interface CustomWordMapper extends BaseMapper<CustomWord> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    List<CustomWordResult> customList(@Param("paramCondition") CustomWordParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") CustomWordParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    Page<CustomWordResult> customPageList(@Param("page") Page page, @Param("paramCondition") CustomWordParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-06-11
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") CustomWordParam paramCondition);

}

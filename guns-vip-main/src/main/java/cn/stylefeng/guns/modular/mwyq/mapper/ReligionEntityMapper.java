package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.ReligionEntity;
import cn.stylefeng.guns.modular.mwyq.model.params.ReligionEntityParam;
import cn.stylefeng.guns.modular.mwyq.model.result.ReligionEntityResult;
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
 * @since 2021-01-04
 */
public interface ReligionEntityMapper extends BaseMapper<ReligionEntity> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2021-01-04
     */
    List<ReligionEntityResult> customList(@Param("paramCondition") ReligionEntityParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2021-01-04
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ReligionEntityParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2021-01-04
     */
    Page<ReligionEntityResult> customPageList(@Param("page") Page page, @Param("paramCondition") ReligionEntityParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2021-01-04
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ReligionEntityParam paramCondition);

}

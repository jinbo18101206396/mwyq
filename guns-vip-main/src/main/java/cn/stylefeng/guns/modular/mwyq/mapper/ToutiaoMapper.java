package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.Toutiao;
import cn.stylefeng.guns.modular.mwyq.model.params.ToutiaoParam;
import cn.stylefeng.guns.modular.mwyq.model.result.ToutiaoResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 金波
 * @since 2023-05-18
 */
public interface ToutiaoMapper extends BaseMapper<Toutiao> {

    /**
     * 获取列表
     *
     * @author 金波
     * @Date 2023-05-18
     */
    List<ToutiaoResult> customList(@Param("paramCondition") ToutiaoParam paramCondition);

    /**
     * 获取map列表
     *
     * @author 金波
     * @Date 2023-05-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") ToutiaoParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author 金波
     * @Date 2023-05-18
     */
    Page<ToutiaoResult> customPageList(@Param("page") Page page, @Param("paramCondition") ToutiaoParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author 金波
     * @Date 2023-05-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") ToutiaoParam paramCondition);

}

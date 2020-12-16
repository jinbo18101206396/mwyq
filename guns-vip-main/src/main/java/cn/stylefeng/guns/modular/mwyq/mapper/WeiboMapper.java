package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.Weibo;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboResult;
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
 * @since 2020-12-16
 */
public interface WeiboMapper extends BaseMapper<Weibo> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    List<WeiboResult> customList(@Param("paramCondition") WeiboParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") WeiboParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    Page<WeiboResult> customPageList(@Param("page") Page page, @Param("paramCondition") WeiboParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2020-12-16
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") WeiboParam paramCondition);

}

package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.Twitter;
import cn.stylefeng.guns.modular.mwyq.model.params.NewsParam;
import cn.stylefeng.guns.modular.mwyq.model.params.TwitterParam;
import cn.stylefeng.guns.modular.mwyq.model.result.NewsResult;
import cn.stylefeng.guns.modular.mwyq.model.result.TwitterResult;
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
 * @author jinbo
 * @since 2021-06-22
 */
public interface TwitterMapper extends BaseMapper<Twitter> {

    /**
     * 获取列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> customList(@Param("paramCondition") TwitterParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") TwitterParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    Page<TwitterResult> customPageList(@Param("page") Page page, @Param("paramCondition") TwitterParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") TwitterParam paramCondition);


    /**
     * 获取作者排行列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> authorRankList(@Param("paramCondition") TwitterParam paramCondition);

    /**
     * 获取作者排行列表
     *
     * @author jinbo
     * @Date 2021-06-22
     */
    List<TwitterResult> sentimentList(@Param("paramCondition") TwitterParam paramCondition);
}

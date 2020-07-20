package cn.stylefeng.guns.sys.modular.third.mapper;

import cn.stylefeng.guns.sys.modular.third.entity.OauthUserInfo;
import cn.stylefeng.guns.sys.modular.third.model.params.OauthUserInfoParam;
import cn.stylefeng.guns.sys.modular.third.model.result.OauthUserInfoResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 第三方用户信息表 Mapper 接口
 * </p>
 *
 * @author jinbo
 * @since 2019-06-09
 */
public interface OauthUserInfoMapper extends BaseMapper<OauthUserInfo> {

    /**
     * 获取列表
     *
     * @author stylefeng
     * @Date 2019-06-09
     */
    List<OauthUserInfoResult> customList(@Param("paramCondition") OauthUserInfoParam paramCondition);

    /**
     * 获取map列表
     *
     * @author jinbo
     * @Date 2019-06-09
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OauthUserInfoParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author jinbo
     * @Date 2019-06-09
     */
    Page<OauthUserInfoResult> customPageList(@Param("page") Page page, @Param("paramCondition") OauthUserInfoParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author jinbo
     * @Date 2019-06-09
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OauthUserInfoParam paramCondition);

}

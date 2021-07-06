package cn.stylefeng.guns.modular.mwyq.mapper;

import cn.stylefeng.guns.modular.mwyq.entity.WeiboUser;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboUserParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserMapResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ethan
 */
public interface WeiboUserMapper extends BaseMapper<WeiboUser> {
    /**
     * 微博影响力分布
     * @param paramCondition
     * @return
     */
    List<WeiboUserResult> followersList(@Param("paramCondition") WeiboUserParam paramCondition);

    List<WeiboUserMapResult> userMapList(@Param("paramCondition")WeiboUserParam paramCondition);
}

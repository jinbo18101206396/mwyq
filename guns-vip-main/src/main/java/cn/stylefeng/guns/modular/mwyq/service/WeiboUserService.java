package cn.stylefeng.guns.modular.mwyq.service;

import cn.stylefeng.guns.modular.mwyq.entity.WeiboUser;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboUserParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserMapResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ethan
 */
public interface WeiboUserService extends IService<WeiboUser> {

    List<WeiboUserResult> followersList(WeiboUserParam weiboUserParam);

    List<WeiboUserMapResult> userMapList(WeiboUserParam weiboUserParam);
}

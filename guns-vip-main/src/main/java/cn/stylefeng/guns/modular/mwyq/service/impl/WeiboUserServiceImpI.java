package cn.stylefeng.guns.modular.mwyq.service.impl;

import cn.stylefeng.guns.modular.mwyq.entity.WeiboUser;
import cn.stylefeng.guns.modular.mwyq.mapper.WeiboUserMapper;
import cn.stylefeng.guns.modular.mwyq.model.params.WeiboUserParam;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserMapResult;
import cn.stylefeng.guns.modular.mwyq.model.result.WeiboUserResult;
import cn.stylefeng.guns.modular.mwyq.service.WeiboUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ethan
 */

@Service
public class WeiboUserServiceImpI extends ServiceImpl<WeiboUserMapper, WeiboUser> implements WeiboUserService{

    @Autowired
    private WeiboUserMapper weiboUserMapper;

    @Override
    public List<WeiboUserResult> followersList(WeiboUserParam weiboUserParam) {
         return weiboUserMapper.followersList(weiboUserParam);
    }

    @Override
    public List<WeiboUserMapResult> userMapList(WeiboUserParam weiboUserParam) {
        return weiboUserMapper.userMapList(weiboUserParam);
    }

    @Override
    public boolean updateById(WeiboUser entity) {
        return false;
    }
}

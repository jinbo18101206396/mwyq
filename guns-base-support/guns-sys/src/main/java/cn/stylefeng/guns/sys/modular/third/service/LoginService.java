package cn.stylefeng.guns.sys.modular.third.service;

import me.zhyd.oauth.model.AuthUser;

/**
 * 第三方登录服务
 *
 * @author
 * @Date
 */
public interface LoginService {

    /**
     * 第三方登录
     *
     * @author
     * @Date
     */
    String oauthLogin(AuthUser oauthUser);
}

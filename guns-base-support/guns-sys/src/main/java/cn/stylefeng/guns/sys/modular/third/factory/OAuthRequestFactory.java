package cn.stylefeng.guns.sys.modular.third.factory;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.request.*;

/**
 * OAuth2 请求的构建器
 *
 * @author
 * @Date
 */
public class OAuthRequestFactory {

    /**
     * 服务器基础地址
     */
    private static final String BASE_URL = "http://localhost";

    /**
     * 根据具体的授权来源，获取授权请求工具类
     */
    public static AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source) {
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId("1110506883")
                        .clientSecret("4vSEbY91eWObSBvZ")
                        .redirectUri(BASE_URL + "/oauth/callback/qq")
                        .build());
                break;
            case "wechat":
                authRequest = new AuthWeChatRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/wechat")
                        .build());
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/weibo")
                        .build());
                break;
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/dingtalk")
                        .build());
                break;
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/baidu")
                        .build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/github")
                        .build());
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/gitee")
                        .build());
                break;
            case "coding":
                authRequest = new AuthCodingRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/tencentCloud")
                        .build());
                break;
            case "tencentCloud":
                authRequest = new AuthTencentCloudRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/tencentCloud")
                        .build());
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/oschina")
                        .build());
                break;
            case "alipay":
                // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1，所以这儿的回调地址使用的局域网内的ip
                authRequest = new AuthAlipayRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .alipayPublicKey("")
                        .redirectUri(BASE_URL + "/oauth/callback/alipay")
                        .build());
                break;
            case "csdn":
                authRequest = new AuthCsdnRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/wechat")
                        .build());
                break;
            case "taobao":
                authRequest = new AuthTaobaoRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/taobao")
                        .build());
                break;
            case "google":
                authRequest = new AuthGoogleRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/google")
                        .build());
                break;
            case "facebook":
                authRequest = new AuthFacebookRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri(BASE_URL + "/oauth/callback/facebook")
                        .build());
                break;
        }

        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }

        return authRequest;
    }

}

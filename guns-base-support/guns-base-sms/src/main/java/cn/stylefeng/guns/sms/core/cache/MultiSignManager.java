package cn.stylefeng.guns.sms.core.cache;

/**
 * 多个签名的缓存管理
 *
 * @author fengshuonan
 * @date 2018-09-21-上午10:47
 */
public interface MultiSignManager {

    /**
     * 获取签名
     *
     * @param phone    电话
     * @param signName 发送短信用的签名，是一个以逗号隔开的字符串
     * @author fengshuonan
     * @Date 2018/9/21 上午10:51
     */
    String getSign(String phone, String signName);

}

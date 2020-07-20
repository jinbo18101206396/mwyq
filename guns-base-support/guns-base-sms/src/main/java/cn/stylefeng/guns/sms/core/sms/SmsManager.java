package cn.stylefeng.guns.sms.core.sms;

import cn.stylefeng.guns.sms.modular.model.SmsSendRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信发送服务
 *
 * @author fengshuonan
 * @date 2018-07-06-下午2:14
 */
public interface SmsManager {

    /**
     * 发送短信
     *
     * @param phoneNumber  电话号码
     * @param templateCode 模板号码
     * @param params       模板里参数的集合
     * @author fengshuonan
     * @Date 2018/7/6 下午2:32
     */
    void sendSms(String phoneNumber, String templateCode, Map<String, Object> params);

    /**
     * 获取某个手机号已发送短信列表
     *
     * @param phoneNumber 手机号码
     * @param sendDate    发送日期
     * @param pageNo      分页（第几页）不传递返回第一页，默认分页大小50
     * @author fengshuonan
     * @Date 2018/7/6 下午3:54
     */
    List<SmsSendRecord> getAlreadySendList(String phoneNumber, Date sendDate, Integer pageNo);

}

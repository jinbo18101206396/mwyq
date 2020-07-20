package cn.stylefeng.guns.sms.modular.provider;


import cn.hutool.core.util.RandomUtil;
import cn.stylefeng.guns.sms.core.enums.MessageType;
import cn.stylefeng.guns.sms.core.enums.SmsSendStatus;
import cn.stylefeng.guns.sms.core.enums.SmsVerifyResult;
import cn.stylefeng.guns.sms.core.sms.SmsManager;
import cn.stylefeng.guns.sms.modular.model.SendMessageParam;
import cn.stylefeng.guns.sms.modular.model.VerifySMSParam;
import cn.stylefeng.guns.sms.modular.service.SmsInfoService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


/**
 * 短信通知接口
 *
 * @author stylefeng
 * @Date 2018/7/5 21:05
 */
@Slf4j
public class SmsServiceProvider implements SmsServiceApi {

    @Autowired
    private SmsManager smsManager;

    @Autowired
    private SmsInfoService smsInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sendShortMessage(SendMessageParam sendMessageParam) {

        Map<String, Object> params = sendMessageParam.getParams();
        log.info("发送短信Provider接口，参数为：" + JSON.toJSONString(sendMessageParam));

        //如果是纯消息发送
        if (MessageType.MESSAGE.equals(sendMessageParam.getMessageType())) {
            smsManager.sendSms(sendMessageParam.getPhoneNumbers(), sendMessageParam.getTemplateCode(), params);
            log.info("发送短信Provider接口--message，params的map具体为：" + JSON.toJSONString(params));
        } else {

            //初始化短信发送参数
            String validateCode = null;

            //如果传的参数中没有code，就初始化一个code放到参数map里
            if (params != null && params.get("code") != null) {
                validateCode = params.get("code").toString();
            } else {
                validateCode = RandomUtil.randomNumbers(6);
                if (params == null) {
                    params = new HashMap<>();
                }
                params.put("code", validateCode);
            }

            log.info("发送短信Provider接口，params的map具体为：" + JSON.toJSONString(params));
            log.info("发送短信Provider接口，验证码为：" + validateCode);

            //存储短信到数据库
            Integer keyId = smsInfoService.saveSmsInfo(sendMessageParam, validateCode);

            log.info("开始发送短信：发送的电话号码= " + sendMessageParam.getPhoneNumbers() + ",发送的模板号=" + sendMessageParam.getTemplateCode() + "，发送的参数是：" + JSON.toJSONString(params));

            //发送短信
            smsManager.sendSms(sendMessageParam.getPhoneNumbers(), sendMessageParam.getTemplateCode(), params);

            //更新短信发送状态
            smsInfoService.updateSmsInfo(keyId, SmsSendStatus.SUCCESS);
        }

        return true;
    }


    @Override
    public SmsVerifyResult verifyShortMessage(VerifySMSParam verifySMSParam) {
        log.info("验证短信Provider接口，参数为：" + JSON.toJSONString(verifySMSParam));
        SmsVerifyResult smsVerifyResult = smsInfoService.validateSmsInfo(verifySMSParam);
        log.info("验证短信Provider接口，响应结果为：" + JSON.toJSONString(smsVerifyResult));
        return smsVerifyResult;

        //return SmsVerifyResult.SUCCESS;
    }

    @Override
    public SmsSendStatus getMessageSendStatus(Integer smsId) {
        return null;
    }

}

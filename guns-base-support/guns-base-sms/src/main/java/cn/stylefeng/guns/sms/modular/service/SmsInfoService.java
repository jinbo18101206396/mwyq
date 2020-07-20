package cn.stylefeng.guns.sms.modular.service;

import cn.stylefeng.guns.base.sms.AliyunSmsProperties;
import cn.stylefeng.guns.sms.core.enums.SmsResultEnum;
import cn.stylefeng.guns.sms.core.enums.SmsSendStatus;
import cn.stylefeng.guns.sms.core.enums.SmsVerifyResult;
import cn.stylefeng.guns.sms.core.exception.SmsException;
import cn.stylefeng.guns.sms.modular.entity.SmsInfo;
import cn.stylefeng.guns.sms.modular.mapper.SmsInfoMapper;
import cn.stylefeng.guns.sms.modular.model.SendMessageParam;
import cn.stylefeng.guns.sms.modular.model.VerifySMSParam;
import cn.stylefeng.roses.kernel.model.util.ValidateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 短信信息表 服务实现类
 * </p>
 *
 * @author fengshuonan
 * @since 2018-07-05
 */
@Slf4j
public class SmsInfoService extends ServiceImpl<SmsInfoMapper, SmsInfo> {

    @Autowired
    private AliyunSmsProperties aliyunSmsProperties;

    /**
     * 存储短信验证信息
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午4:47
     */
    public Integer saveSmsInfo(SendMessageParam sendMessageParam, String validateCode) {

        if (ValidateUtil.isOneEmpty(sendMessageParam.getPhoneNumbers(), validateCode, sendMessageParam.getTemplateCode())) {
            log.error("存储短信到数据库失败！有参数为空！");
            throw new SmsException(SmsResultEnum.PARAM_NULL.getCode(), SmsResultEnum.PARAM_NULL.getMessage());
        }

        //当前时间
        Date nowDate = new Date();

        //短信失效时间
        long invalidateTime = nowDate.getTime() + aliyunSmsProperties.getInvalidateMinutes() * 60 * 1000;
        Date invalidate = new Date(invalidateTime);

        SmsInfo smsInfo = new SmsInfo();
        smsInfo.setCreateTime(nowDate);
        smsInfo.setInvalidTime(invalidate);
        smsInfo.setPhoneNumbers(sendMessageParam.getPhoneNumbers());
        smsInfo.setStatus(SmsSendStatus.WAITING.getCode());
        smsInfo.setSource(sendMessageParam.getSmsSendSource().getCode());
        smsInfo.setTemplateCode(sendMessageParam.getTemplateCode());
        smsInfo.setValidateCode(validateCode);

        this.save(smsInfo);

        log.info("发送短信，存储短信到数据库，数据为：" + JSON.toJSONString(smsInfo));

        return smsInfo.getId();
    }

    /**
     * 更新短息发送状态
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午5:12
     */
    public void updateSmsInfo(Integer keyId, SmsSendStatus smsSendStatus) {
        SmsInfo smsInfo = this.getById(keyId);
        smsInfo.setStatus(smsSendStatus.getCode());
        this.updateById(smsInfo);
    }

    /**
     * 校验验证码是否正确
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午5:16
     */
    @Transactional(rollbackFor = Exception.class)
    public SmsVerifyResult validateSmsInfo(VerifySMSParam verifySMSParam) {

        //校验请求是否为空
        if (ValidateUtil.isOneEmpty(verifySMSParam.getPhoneNumbers(), verifySMSParam.getCode(), verifySMSParam.getTemplateCode())) {
            log.error("校验短信是否正确失败！有参数为空！");
            throw new SmsException(SmsResultEnum.PARAM_NULL.getCode(), SmsResultEnum.PARAM_NULL.getMessage());
        }

        //查询有没有这条记录
        QueryWrapper<SmsInfo> wrapper = new QueryWrapper<>();


        wrapper.eq("phone_numbers", verifySMSParam.getPhoneNumbers())
                .and(f -> f.eq("source", verifySMSParam.getSmsSendSource().getCode()))
                .and(f -> f.eq("template_code", verifySMSParam.getTemplateCode()));

        wrapper.orderByDesc("create_time");

        List<SmsInfo> smsInfos = this.list(wrapper);

        log.info("验证短信Provider接口，查询到sms记录：" + JSON.toJSONString(smsInfos));

        //如果找不到记录，提示验证失败
        if (smsInfos == null || smsInfos.isEmpty()) {
            log.info("验证短信Provider接口，找不到验证码记录，响应验证失败！");
            return SmsVerifyResult.ERROR;
        } else {

            //获取最近发送的第一条
            SmsInfo smsInfo = smsInfos.get(0);

            //先判断状态是不是失效的状态
            if (SmsSendStatus.INVALID.getCode().equals(smsInfo.getStatus())) {
                log.info("验证短信Provider接口，短信状态是失效，响应验证失败！");
                return SmsVerifyResult.ERROR;
            }

            //如果验证码和传过来的不一致
            if (!verifySMSParam.getCode().equals(smsInfo.getValidateCode())) {
                log.info("验证短信Provider接口，验证手机号和验证码不一致，响应验证失败！");
                return SmsVerifyResult.ERROR;
            }

            //判断是否超时
            Date invalidTime = smsInfo.getInvalidTime();
            if (invalidTime == null || new Date().after(invalidTime)) {
                log.info("验证短信Provider接口，验证码超时，响应验证失败！");
                return SmsVerifyResult.EXPIRED;
            }

            //验证成功把短信设置成失效
            smsInfo.setStatus(SmsSendStatus.INVALID.getCode());
            this.updateById(smsInfo);

            log.info("验证短信Provider接口，验证码验证成功！");
            return SmsVerifyResult.SUCCESS;
        }
    }


}

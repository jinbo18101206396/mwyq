package cn.stylefeng.guns.sms.core.sms.service;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.sms.AliyunSmsProperties;
import cn.stylefeng.guns.sms.core.cache.MultiSignManager;
import cn.stylefeng.guns.sms.core.enums.SmsResultEnum;
import cn.stylefeng.guns.sms.core.exception.SmsException;
import cn.stylefeng.guns.sms.core.sms.SmsManager;
import cn.stylefeng.guns.sms.modular.model.SmsSendRecord;
import cn.stylefeng.roses.kernel.model.util.ValidateUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.PURE_DATE_PATTERN;

/**
 * 阿里云短信发送服务
 *
 * @author fengshuonan
 * @date 2018-07-06-下午2:15
 */
@Slf4j
public class AliyunSmsManager implements SmsManager {

    @Autowired
    private MultiSignManager multiSignManager;

    @Override
    public void sendSms(String phoneNumber, String templateCode, Map<String, Object> params) {

        log.info("开始发送阿里云短信，手机号是：" + phoneNumber + ",模板号是：" + templateCode + ",参数是：" + JSON.toJSONString(params));

        //获取发送短信的配置
        AliyunSmsProperties aliyunSmsProperties = ConstantsContext.getAliyunSmsProperties();

        //检验参数是否合法
        assertSendSmsParams(phoneNumber, templateCode, params, aliyunSmsProperties);

        //初始化client profile
        IClientProfile profile = initClientProfile();
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsResponse sendSmsResponse = createSmsRequest(phoneNumber, templateCode, params, acsClient);

        //如果返回ok则发送成功
        if (sendSmsResponse.getCode() != null && SmsResultEnum.OK.getCode().equals(sendSmsResponse.getCode())) {
            return;
        } else {

            //放回其他状态码根据情况抛出业务异常
            String code = SmsResultEnum.SYSTEM_ERROR.getCode();
            String errorMessage = SmsResultEnum.SYSTEM_ERROR.getMessage();
            for (SmsResultEnum smsResultEnum : SmsResultEnum.values()) {
                if (smsResultEnum.getCode().equals(sendSmsResponse.getCode())) {
                    code = smsResultEnum.getCode();
                    errorMessage = smsResultEnum.getMessage();
                }
            }
            log.error("发送短信异常！code = " + code + ",message = " + errorMessage);
            throw new SmsException(code, errorMessage);
        }
    }

    @Override
    public List<SmsSendRecord> getAlreadySendList(String phoneNumber, Date sendDate, Integer pageNo) {

        //检验参数是否合法
        assertGetSendListParams(phoneNumber, sendDate, pageNo);

        //初始化阿里云短信发送环境
        IClientProfile clientProfile = initClientProfile();
        IAcsClient acsClient = new DefaultAcsClient(clientProfile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(phoneNumber);

        //必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        String dateString = DateUtil.format(sendDate, PURE_DATE_PATTERN);
        request.setSendDate(dateString);

        //必填-页大小
        request.setPageSize(50L);

        //必填-当前页码从1开始计数
        request.setCurrentPage(pageNo == null ? 1L : pageNo);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("获取已发短信列表异常！", e);
        }

        //获取返回结果
        if (querySendDetailsResponse.getCode() != null && querySendDetailsResponse.getCode().equals("OK")) {

            //转化查询结果为List<SmsSendRecord>
            List<QuerySendDetailsResponse.SmsSendDetailDTO> smsSendDetailDTOs = querySendDetailsResponse.getSmsSendDetailDTOs();
            ArrayList<SmsSendRecord> results = new ArrayList<>();
            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : smsSendDetailDTOs) {
                SmsSendRecord smsSendRecord = new SmsSendRecord();
                BeanUtils.copyProperties(smsSendDetailDTO, smsSendRecord);
                results.add(smsSendRecord);
            }
            return results;
        } else {
            log.error("获取短信列表异常！code = " + querySendDetailsResponse.getCode());
            throw new SmsException(querySendDetailsResponse.getCode(), "获取短信列表异常！");
        }
    }

    /**
     * 初始化短信发送的环境
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午3:57
     */
    private IClientProfile initClientProfile() {
        //设置超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";              //短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";  //短信API产品域名（接口地址固定，无需修改）

        AliyunSmsProperties aliyunSmsProperties = ConstantsContext.getAliyunSmsProperties();
        final String accessKeyId = aliyunSmsProperties.getAccessKeyId();
        final String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            log.error("初始化阿里云sms异常！", e);
        }
        return profile;
    }

    /**
     * 组装请求对象
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午3:00
     */
    private SendSmsResponse createSmsRequest(String phoneNumber, String templateCode, Map<String, Object> params, IAcsClient acsClient) {

        SendSmsRequest request = new SendSmsRequest();

        request.setMethod(MethodType.POST);

        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，
        //批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phoneNumber);

        //必填:短信签名-可在短信控制台中找到
        request.setSignName(this.getSmsSign(phoneNumber));

        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        //可选:模板中的变量替换JSON串
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(JSON.toJSONString(params));

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("初始化阿里云sms异常！可能是accessKey和secret错误！", e);
            throw new SmsException(SmsResultEnum.INIT_SMS_CLIENT_ERROR.getCode(),
                    SmsResultEnum.INIT_SMS_CLIENT_ERROR.getMessage());
        }
        return sendSmsResponse;
    }

    /**
     * 校验发送短信的参数是否正确
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午3:19
     */
    private void assertSendSmsParams(String phoneNumber, String templateCode, Map<String, Object> params,
                                     AliyunSmsProperties aliyunSmsProperties) {
        if (ValidateUtil.isOneEmpty(phoneNumber, templateCode, params, aliyunSmsProperties)) {
            log.error("阿里云短信发送异常！请求参数存在空！");
            throw new SmsException(SmsResultEnum.PARAM_NULL.getCode(), SmsResultEnum.PARAM_NULL.getMessage());
        }
    }

    /**
     * 校验获取已发送列表的传参
     *
     * @author fengshuonan
     * @Date 2018/7/6 下午4:13
     */
    private void assertGetSendListParams(String phoneNumber, Date sendDate, Integer pageNo) {
        if (ValidateUtil.isOneEmpty(phoneNumber, sendDate)) {
            log.error("阿里云获取短信发送列表异常！请求参数存在空！");
            throw new SmsException(SmsResultEnum.PARAM_NULL.getCode(), SmsResultEnum.PARAM_NULL.getMessage());
        }
    }

    /**
     * 获取sms发送的sign标识，参数phone是发送的手机号码
     *
     * @author stylefeng
     * @Date 2018/8/13 21:23
     */
    private String getSmsSign(String phone) {
        AliyunSmsProperties aliyunSmsProperties = ConstantsContext.getAliyunSmsProperties();
        String signName = aliyunSmsProperties.getSignName();

        //如果是单个签名就用一个签名发
        if (!signName.contains(",")) {
            log.info("发送短信，签名为：" + signName + ",电话为：" + phone);
            return signName;
        } else {
            return multiSignManager.getSign(phone, signName);
        }
    }

}

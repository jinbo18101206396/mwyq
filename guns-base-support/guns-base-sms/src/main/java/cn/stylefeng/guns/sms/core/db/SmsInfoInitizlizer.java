package cn.stylefeng.guns.sms.core.db;

import cn.stylefeng.guns.sms.modular.entity.SmsInfo;
import cn.stylefeng.roses.core.db.DbInitializer;

/**
 * 短信发送表的初始化程序
 *
 * @author fengshuonan
 * @date 2018-07-30-上午9:29
 */
public class SmsInfoInitizlizer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `sms_info` (\n" +
                "  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `PHONE_NUMBERS` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '手机号',\n" +
                "  `VALIDATE_CODE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '短信验证码',\n" +
                "  `TEMPLATE_CODE` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '短信模板ID',\n" +
                "  `BIZID` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '回执ID,可根据该ID查询具体的发送状态',\n" +
                "  `STATUS` smallint(3) NOT NULL COMMENT '0=未发送，1=发送成功，2=发送失败，3=失效',\n" +
                "  `SOURCE` smallint(3) NOT NULL COMMENT '0=app，1=pc，2=其它',\n" +
                "  `INVALID_TIME` timestamp NULL DEFAULT NULL COMMENT '失效时间',\n" +
                "  `MODIFY_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',\n" +
                "  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  PRIMARY KEY (`ID`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信信息发送表'";
    }

    @Override
    public String getTableName() {
        return "sms_info";
    }

    @Override
    public Class<?> getEntityClass() {
        return SmsInfo.class;
    }

}

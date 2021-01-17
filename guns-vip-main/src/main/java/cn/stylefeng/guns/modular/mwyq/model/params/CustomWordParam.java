package cn.stylefeng.guns.modular.mwyq.model.params;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 定制词表
 * </p>
 *
 * @author jinbo
 * @since 2020-06-11
 */
@Data
public class CustomWordParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long id;

    /**
     * 定制词名称
     */
    private String name;

    /**
     * 主题词列表
     */
    private List<String> names;

    /**
     * 定制词所属领域，0-宗教、1-语言文字、2-国际关系和国家安全、3-其他
     */
    private String type;

    /**
     * 语言类型，cn-中文、zang-藏文、wei-维吾尔文、meng-蒙文
     */
    private String lang;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建人姓名
     */
    private String userName;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 时间范围
     */
    private String timeLimit;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 新闻来源
     */
    private String newsSource;

    /**
     * 区分模块（1-首页，2-主题相关页）
     */
    private String modular;

    /**
     * 关键词
     */
    private String keyWords;

    /**
     * 敏感词
     */
    private String sensitiveWords;

    /**
     * 新闻作者
     */
    private String newsAuthor;

    private Integer isSensitive;

    private Integer sensitiveCategory;

    @Override
    public String checkParam() {
        return null;
    }

    public static String getFieldByType(String type){
        if(type.equals("0")){
            return "民族宗教";
        }else if(type.equals("1")){
            return "语言文字";
        }else if(type.equals("2")){
            return "国家安全";
        }else{
            return "其他";
        }
    }

}

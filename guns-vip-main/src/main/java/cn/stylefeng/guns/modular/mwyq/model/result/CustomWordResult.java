package cn.stylefeng.guns.modular.mwyq.model.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定制词表
 * </p>
 *
 * @author jinbo
 * @since 2020-06-11
 */
@Data
public class CustomWordResult implements Serializable {

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

}

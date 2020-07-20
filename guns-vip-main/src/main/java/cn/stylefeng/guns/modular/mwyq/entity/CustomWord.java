package cn.stylefeng.guns.modular.mwyq.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 主题词表
 * </p>
 *
 * @author
 * @since
 */
@TableName("custom_word")
@Data
public class CustomWord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @Excel(name = "主题词id")
    private Long id;

    /**
     * 主题词名称
     */
    @TableField("name")
    @Excel(name = "主题词",orderNum = "0")
    private String name;

    /**
     * 定制词所属领域，0-宗教、1-语言文字、2-国际关系和国家安全、3-其他
     */
    @TableField("type")
    @Excel(name = "所属领域", replace = {"宗教_0", "语言文字_1", "国家安全_2","其他_3"}, orderNum = "1")
    private String type;

    /**
     * 语言类型，cn-中文、zang-藏文、wei-维吾尔文、meng-蒙文
     */
    @TableField("lang")
    @Excel(name = "语言类型", replace = {"中文_cn", "藏文_zang", "维吾尔文_wei","蒙文_meng"}, orderNum = "2")
    private String lang;


    /**
     * 顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态(字典)
     */
    @TableField("status")
    @Excel(name = "状态", replace = {"开启_ENABLE", "禁用_DISABLE"},orderNum = "3")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建人id
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    @Excel(name = "创建人")
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Excel(name = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

}

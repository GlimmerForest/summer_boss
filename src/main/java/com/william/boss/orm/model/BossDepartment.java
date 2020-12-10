package com.william.boss.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 组织部门表
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("summer_boss_department")
public class BossDepartment extends Model<BossDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门id
     */
    private Integer pId;

    /**
     * 部门类型
     */
    private Integer type;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态
     */
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Integer createUserId;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    @Version
    private Integer version;

    /**
     * 部门手机号
     */
    private String telephone;

    /**
     * 部门编号
     */
    private String deptNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

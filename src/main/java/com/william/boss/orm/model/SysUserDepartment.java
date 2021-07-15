package com.william.boss.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.william.boss.orm.model.AbstractModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_sys_user_department")
@ApiModel(value="SysUserDepartment对象", description="用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。")
public class SysUserDepartment extends AbstractModel<SysUserDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "登录用户id")
    private Long userId;


    public static final String ID = "id";

    public static final String DEPARTMENT_ID = "department_id";

    public static final String USER_ID = "user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

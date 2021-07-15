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
 * 组织部门表
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_sys_department")
@ApiModel(value="SysDepartment对象", description="组织部门表")
public class SysDepartment extends AbstractModel<SysDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父部门id")
    private Integer pId;

    @ApiModelProperty(value = "部门类型")
    private Integer type;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "部门手机号")
    private String telephone;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String P_ID = "p_id";

    public static final String TYPE = "type";

    public static final String ADDRESS = "address";

    public static final String STATUS = "status";

    public static final String TELEPHONE = "telephone";

    public static final String DEPT_NO = "dept_no";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

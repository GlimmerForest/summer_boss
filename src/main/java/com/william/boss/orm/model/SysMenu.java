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
 * 系统菜单表
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_sys_menu")
@ApiModel(value="SysMenu对象", description="系统菜单表")
public class SysMenu extends AbstractModel<SysMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "菜单编码")
    private String code;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer pId;

    @ApiModelProperty(value = "路由")
    private String href;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "层级")
    private Integer level;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String P_ID = "p_id";

    public static final String HREF = "href";

    public static final String ICON = "icon";

    public static final String ORDER = "order";

    public static final String REMARK = "remark";

    public static final String TYPE = "type";

    public static final String LEVEL = "level";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

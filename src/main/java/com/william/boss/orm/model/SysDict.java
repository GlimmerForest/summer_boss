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
 * 字典码表
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_sys_dict")
@ApiModel(value="SysDict对象", description="字典码表")
public class SysDict extends AbstractModel<SysDict> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字典key")
    private String value;

    @ApiModelProperty(value = "字典值")
    private String text;

    @ApiModelProperty(value = "所属模块")
    private String module;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "字典描述")
    private String remark;

    @ApiModelProperty(value = "查询关键字")
    private String keyword;


    public static final String ID = "id";

    public static final String VALUE = "value";

    public static final String TEXT = "text";

    public static final String MODULE = "module";

    public static final String SORT = "sort";

    public static final String REMARK = "remark";

    public static final String KEYWORD = "keyword";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

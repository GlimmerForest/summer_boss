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
 * 数据库资源表
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_platform_db_resource")
@ApiModel(value="PlatformDbResource对象", description="数据库资源表")
public class PlatformDbResource extends AbstractModel<PlatformDbResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "数据源名称")
    private String sourceName;

    @ApiModelProperty(value = "数据库连接")
    private String url;

    @ApiModelProperty(value = "数据库用户名")
    private String username;

    @ApiModelProperty(value = "数据库密码")
    private String password;

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    private String schema;


    public static final String ID = "id";

    public static final String SOURCE_NAME = "source_name";

    public static final String URL = "url";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String DB_NAME = "db_name";

    public static final String SCHEMA = "schema";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

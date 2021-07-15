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
 * 
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("summer_platform_tenant_datasource")
@ApiModel(value="PlatformTenantDatasource对象", description="")
public class PlatformTenantDatasource extends AbstractModel<PlatformTenantDatasource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer datasourceId;

    private Integer tenantId;

    private String tenantName;

    private String module;


    public static final String ID = "id";

    public static final String DATASOURCE_ID = "datasource_id";

    public static final String TENANT_ID = "tenant_id";

    public static final String TENANT_NAME = "tenant_name";

    public static final String MODULE = "module";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

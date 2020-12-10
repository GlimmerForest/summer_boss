package com.william.boss.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统操作日志表
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("summer_boss_operation_log")
public class BossOperationLog extends Model<BossOperationLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作人
     */
    private String username;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作内容
     */
    private String params;

    /**
     * 操作时间
     */
    private LocalDateTime oprateTime;

    /**
     * 操作接口
     */
    private String url;

    /**
     * 操作模块
     */
    private String module;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

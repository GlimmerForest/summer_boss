package com.william.boss.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("summer_boss_user_department")
public class BossUserDepartment extends Model<BossUserDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 登录用户id
     */
    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 乐观锁版本控制
     */
    @Version
    private Long version;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}

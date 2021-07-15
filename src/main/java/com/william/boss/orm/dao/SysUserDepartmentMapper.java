package com.william.boss.orm.dao;

import com.william.boss.orm.model.SysUserDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 Mapper 接口
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
public interface SysUserDepartmentMapper extends BaseMapper<SysUserDepartment> {

}

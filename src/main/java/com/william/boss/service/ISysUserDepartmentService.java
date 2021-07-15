package com.william.boss.service;

import com.william.boss.orm.model.SysUserDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 服务类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
public interface ISysUserDepartmentService extends IService<SysUserDepartment> {

}

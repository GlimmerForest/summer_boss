package com.william.boss.service.impl;

import com.william.boss.orm.model.SysUserDepartment;
import com.william.boss.orm.dao.SysUserDepartmentMapper;
import com.william.boss.service.ISysUserDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Service
public class SysUserDepartmentServiceImpl extends ServiceImpl<SysUserDepartmentMapper, SysUserDepartment> implements ISysUserDepartmentService {

}

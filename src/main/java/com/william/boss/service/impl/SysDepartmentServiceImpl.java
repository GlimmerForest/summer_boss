package com.william.boss.service.impl;

import com.william.boss.orm.model.SysDepartment;
import com.william.boss.orm.dao.SysDepartmentMapper;
import com.william.boss.service.ISysDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织部门表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {

}

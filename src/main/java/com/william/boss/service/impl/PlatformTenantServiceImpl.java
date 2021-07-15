package com.william.boss.service.impl;

import com.william.boss.orm.model.PlatformTenant;
import com.william.boss.orm.dao.PlatformTenantMapper;
import com.william.boss.service.IPlatformTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台租户信息表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Service
public class PlatformTenantServiceImpl extends ServiceImpl<PlatformTenantMapper, PlatformTenant> implements IPlatformTenantService {

}

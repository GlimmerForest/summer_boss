package com.william.boss.service.impl;

import com.william.boss.orm.model.PlatformDbResource;
import com.william.boss.orm.dao.PlatformDbResourceMapper;
import com.william.boss.service.IPlatformDbResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据库资源表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Service
public class PlatformDbResourceServiceImpl extends ServiceImpl<PlatformDbResourceMapper, PlatformDbResource> implements IPlatformDbResourceService {

}

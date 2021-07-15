package com.william.boss.service.impl;

import com.william.boss.dto.OperationLog;
import com.william.boss.orm.model.SysOperationLog;
import com.william.boss.orm.dao.SysOperationLogMapper;
import com.william.boss.service.IOperationLogService;
import com.william.boss.service.ISysOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.william.boss.utils.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统操作日志表 服务实现类
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService, IOperationLogService {

    @Override
    public void saveLog(OperationLog operationLog) {
        SysOperationLog log = new SysOperationLog();
        BeanUtil.copyProperties(operationLog, log, null);
        log.insert();
    }
}

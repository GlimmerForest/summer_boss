package com.william.boss.service;


import com.william.boss.dto.OperationLog;

public interface IOperationLogService {
    void saveLog(OperationLog operationLog);
}

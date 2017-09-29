package com._520it.crm.service.impl;

import com._520it.crm.domain.SystemLog;
import com._520it.crm.mapper.SystemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 华硕 on 2017/9/29.
 */
@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;
    @Override
    public int insert(SystemLog record) {
        return systemLogMapper.insert(record);
    }
}

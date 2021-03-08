package com.codetc.web.component.aspect;

import com.codetc.common.component.aspect.OperationLogAspect;
import com.codetc.common.model.Log;
import com.codetc.mbg.entity.Logs;
import com.codetc.web.security.util.SecurityUtils;
import com.codetc.web.service.LogsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 需持久化的操作日志
 * Created by anvin on 1/16/2021.
 */
@Component
public class SaveOperationLog extends OperationLogAspect {

    @Autowired
    private LogsService logsService;

    @Async
    @Override
    protected void saveLog(Log log) {
        Logs saveLog = new Logs();
        BeanUtils.copyProperties(log, saveLog);
        saveLog.setInsertTime(LocalDateTime.now());
        Long userId = this.getCurrentUserId();
        saveLog.setUserId(userId == null ? 0 : userId);
        this.logsService.saveLog(saveLog);
    }

    /**
     * 获取当前用户 ID
     */
    private Long getCurrentUserId() {
        try {
            return SecurityUtils.getCurrentUserId();
        } catch (Exception e) {
            return null;
        }
    }
}

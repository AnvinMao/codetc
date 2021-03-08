package com.codetc.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codetc.common.model.param.PageParam;
import com.codetc.mbg.entity.Logs;
import com.codetc.mbg.mapper.LogsMapper;
import com.codetc.web.model.query.LogQuery;
import com.codetc.web.service.LogsService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作日志服务
 * Created by anvin on 1/16/2021.
 */
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {

    @Resource
    private LogsMapper logsMapper;

    @Override
    public void saveLog(Logs logs) {
        this.logsMapper.insert(logs);
    }

    @Override
    public List<Logs> getLogList(LogQuery query, PageParam param) {
        QueryWrapper<Logs> queryWrapper = new QueryWrapper<>();
        if (query.getStartDate() != null) {
            queryWrapper.lambda().ge(Logs::getInsertTime, query.getStartDate());
        }

        if (query.getEndDate() != null) {
            queryWrapper.lambda().lt(Logs::getInsertTime, query.getEndDate());
        }

        PageHelper.startPage(param.getPageIndex(), param.getPageSize());
        return list(queryWrapper);
    }
}

package com.codetc.web.service;

import com.codetc.common.model.param.PageParam;
import com.codetc.mbg.entity.Logs;
import com.codetc.web.model.query.LogQuery;

import java.util.List;

/**
 * 操作日志 Service
 * Created by anvin on 1/16/2021.
 */
public interface LogsService {

    /**
     * 保存日志
     * @param logs
     */
    void saveLog(Logs logs);

    /**
     * 日志列表
     * @param query
     * @param param
     * @return
     */
    List<Logs> getLogList(LogQuery query, PageParam param);
}

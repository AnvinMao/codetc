package com.codetc.web.controller;

import com.codetc.common.model.api.CommonPage;
import com.codetc.common.model.api.CommonResult;
import com.codetc.common.model.param.PageParam;
import com.codetc.mbg.entity.Logs;
import com.codetc.web.model.query.LogQuery;
import com.codetc.web.service.LogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by anvin on 3/8/2021.
 */
@Api(value = "操作日志")
@RestController
@RequestMapping("/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    @ApiOperation("操作日志列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<Logs>> operationLogs(
            @Valid LogQuery query,
            PageParam param) {
        List<Logs> logsList = this.logsService.getLogList(query, param);
        return CommonResult.success(logsList);
    }
}

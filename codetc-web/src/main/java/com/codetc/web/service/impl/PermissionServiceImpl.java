package com.codetc.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codetc.common.model.param.PageParam;
import com.codetc.mbg.entity.Permission;
import com.codetc.mbg.mapper.PermissionMapper;
import com.codetc.mbg.mapper.RolePermissionMapper;
import com.codetc.web.service.PermissionService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by anvin on 1/15/2021.
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> getPermissionList(PageParam param) {
        PageHelper.startPage(param.getPageIndex(), param.getPageSize());
        return list();
    }

    @Override
    public List<Permission> getRolePermission(Integer roleId) {
        return this.rolePermissionMapper.getRolePermission(roleId);
    }
}

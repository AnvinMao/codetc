package com.codetc.web.service;

import com.codetc.common.model.param.PageParam;
import com.codetc.mbg.entity.Permission;

import java.util.List;

/**
 * 权限Service
 * Created by anvin on 1/15/2021.
 */
public interface PermissionService {

    List<Permission> getPermissionList(PageParam param);

    List<Permission> getRolePermission(Integer roleId);
}

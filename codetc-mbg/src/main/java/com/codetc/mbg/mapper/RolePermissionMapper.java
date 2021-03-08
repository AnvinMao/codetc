package com.codetc.mbg.mapper;

import com.codetc.mbg.entity.Permission;
import com.codetc.mbg.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色权限表 Mapper 接口
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<Permission> getRolePermission(Integer roleId);
}

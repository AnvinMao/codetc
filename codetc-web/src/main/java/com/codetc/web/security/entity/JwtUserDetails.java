package com.codetc.web.security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.codetc.mbg.entity.Permission;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Security 用户实现
 */
@ToString
public class JwtUserDetails implements UserDetails {

    private Long userId;

    private String username;

    private String password;

    private Integer status;

    private List<Permission> permissionList;

    public JwtUserDetails() {}

    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissionList.stream()
                .filter(permission -> permission.getValue() != null && !permission.getValue().isEmpty())
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return this.status.equals(1);
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Permission> getPermissionList() {
        return this.permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

package com.codetc.web.security.component;

import com.codetc.mbg.entity.Permission;
import com.codetc.mbg.entity.User;
import com.codetc.web.security.entity.JwtUserDetails;
import com.codetc.web.service.PermissionService;
import com.codetc.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring Security 用户的业务实现
 */
@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("无效用户");
        }

        List<Permission> permissions = this.permissionService.getRolePermission(user.getRoleId());
        JwtUserDetails userDetails = new JwtUserDetails();
        userDetails.setUserId(user.getId());
        userDetails.setPassword(user.getPassword());
        userDetails.setUsername(user.getUsername());
        userDetails.setPermissionList(permissions);
        userDetails.setStatus(user.getStatus());
        return userDetails;
    }
}

package com.codetc.web.service.impl;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codetc.common.constant.RedisKeyEnum;
import com.codetc.common.exception.AuthException;
import com.codetc.common.service.RedisService;
import com.codetc.mbg.mapper.UserMapper;
import com.codetc.web.component.JwtProps;
import com.codetc.web.model.param.LoginParam;
import com.codetc.web.model.param.UserParam;
import com.codetc.web.model.vo.TokenVO;
import com.codetc.web.security.entity.JwtUserDetails;
import com.codetc.web.security.util.SecurityUtils;
import com.codetc.web.service.UserService;
import com.codetc.mbg.entity.User;
import com.codetc.web.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisService redisService;

    @Override
    public TokenVO login(LoginParam param) {
        User user = this.getByUserName(param.getUsername());
        if (user == null || !this.passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new AuthException(1101, "账号或密码错误");
        }

        JwtUserDetails userDetails = (JwtUserDetails) this.userDetailsService.loadUserByUsername(param.getUsername());
        if (!userDetails.isEnabled()) {
            throw new AuthException(1102, "账号被禁止登录");
        }
        // 缓存会话信息
        String redisKey = StrUtil.format(RedisKeyEnum.AUTH_USER_JWT_ACCESS_FORMAT.getKey(), user.getUsername());
        this.redisService.set(redisKey, userDetails, JwtProps.accessTokenExpireTime);
        // 生成会话token
        String accessToken = JwtUtils.generateAccessToken(userDetails);
        TokenVO vo = new TokenVO();
        vo.setAccessToken(accessToken);
        vo.setExpired(JwtProps.accessTokenExpireTime);
        return vo;
    }

    @Override
    public void logout() {
        JwtUserDetails userDetails = SecurityUtils.getCurrentUser();
        if (userDetails != null) {
            String redisKey = StrUtil.format(RedisKeyEnum.AUTH_USER_JWT_ACCESS_FORMAT.getKey(), userDetails.getUsername());
            this.redisService.remove(redisKey);
        }
    }

    @Override
    public TokenVO refresh() {
        JwtUserDetails userDetails = SecurityUtils.getCurrentUser();
        if (userDetails != null) {
            // 刷新redis缓存失效时间
            String redisKey = StrUtil.format(RedisKeyEnum.AUTH_USER_JWT_ACCESS_FORMAT.getKey(), userDetails.getUsername());
            this.redisService.expire(redisKey, JwtProps.accessTokenExpireTime);
            // 重新生成会话token
            String accessToken = JwtUtils.generateAccessToken(userDetails);
            TokenVO vo = new TokenVO();
            vo.setAccessToken(accessToken);
            vo.setExpired(JwtProps.accessTokenExpireTime);
            return vo;
        }

        return null;
    }

    @Override
    public User getByUserName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, username);
        return this.userMapper.selectOne(wrapper);
    }

    @Override
    public void createUser(UserParam param) {
        User user = this.getByUserName(param.getUsername());
        if (user != null) {
            throw new AuthException(1103, "用户已注册");
        }

        user = new User().setUsername(param.getUsername())
                .setNickname(StringUtils.isEmpty(param.getNickname()) ? param.getUsername() : param.getNickname())
                .setPassword(this.passwordEncoder.encode(param.getPassword()))
                .setCreateTime(LocalDateTime.now())
                .setRoleId(param.getRoleId());
        this.userMapper.insert(user);
    }
}

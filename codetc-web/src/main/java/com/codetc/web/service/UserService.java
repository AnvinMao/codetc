package com.codetc.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codetc.web.model.param.LoginParam;
import com.codetc.web.model.param.UserParam;
import com.codetc.mbg.entity.User;
import com.codetc.web.model.vo.TokenVO;

public interface UserService extends IService<User> {

    TokenVO login(LoginParam param);

    void logout();

    TokenVO refresh();

    User getByUserName(String username);

    void createUser(UserParam param);
}

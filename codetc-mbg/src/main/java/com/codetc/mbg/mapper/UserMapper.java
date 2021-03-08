package com.codetc.mbg.mapper;

import com.codetc.mbg.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

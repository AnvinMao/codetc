package com.codetc.web.model.converter;

import com.codetc.web.model.vo.UserVO;
import com.codetc.common.component.base.BaseConverter;
import com.codetc.mbg.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserConverter extends BaseConverter<User, UserVO> {
}

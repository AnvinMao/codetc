package com.codetc.web.model.converter;

import com.codetc.mbg.entity.User;
import com.codetc.web.model.vo.UserVO;
import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-08T11:50:45+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class UserConverterImpl extends UserConverter {

    @Override
    public UserVO entity2vo(User arg0) {
        if ( arg0 == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( arg0.getId() );
        userVO.setUsername( arg0.getUsername() );
        userVO.setNickname( arg0.getNickname() );
        userVO.setCreateTime( arg0.getCreateTime() );

        return userVO;
    }

    @Override
    public List<UserVO> entity2vo(List<User> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<UserVO> list = new ArrayList<UserVO>( arg0.size() );
        for ( User user : arg0 ) {
            list.add( entity2vo( user ) );
        }

        return list;
    }

    @Override
    public Page<UserVO> entity2vo(Page<User> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Page<UserVO> page = new Page<UserVO>();
        for ( User user : arg0 ) {
            page.add( entity2vo( user ) );
        }

        setPage( arg0, page );

        return page;
    }
}

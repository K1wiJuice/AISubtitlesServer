package com.AISubtitles.Server.mapper;


import com.AISubtitles.Server.domain.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {

    User getUserById(Integer userId);

    int deleteUserById(Integer user_id);

    int insertUser(User user);

}

package com.AISubtitles.Server.mapper;


import com.AISubtitles.Server.domain.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {

    User findUserByEmail(@Param("userEmail") String userEmail);

    User findUserByPhoneNumber(@Param("userPhoneNumber") String userPhoneNumber);

    @Options(useGeneratedKeys = true,keyProperty = "userId",keyColumn = "user_id")
    int regist(User user);
}

package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.UserAuths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserAuthsDao extends JpaRepository<UserAuths, Integer> {
    UserAuths findByUserId(int userId);
    UserAuths findByUserIdAndUserPassword(int userId, String userPassword);

    @Transactional
    @Modifying
    @Query(value = "update user_auths  set user_password = :userPassword  where user_id = :userId",nativeQuery = true)
    public Integer update(@Param("userPassword")String userPassword,@Param("userId") int userId);
}

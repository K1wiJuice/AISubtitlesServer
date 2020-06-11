package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承过来的方法包括增删改查
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserNameAndPassword(String usereamil,String userpassword);
}

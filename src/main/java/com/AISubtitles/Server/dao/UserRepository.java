package com.AISubtitles.Server.dao;

<<<<<<< HEAD

import com.AISubtitles.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserPhoneNumber(String userPhoneNumber);

    User findByUserEmail(String userEmail);
=======
import com.AISubtitles.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承过来的方法包括增删改查
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserNameAndPassword(String usereamil,String userpassword);
>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
}

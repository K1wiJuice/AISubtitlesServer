package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer>{
    User findByUserEmail(String userEmail);
    User findByUserPhoneNumber(String userPhoneNumber);

    User findByUserEmailAndUserPassword(String userEmail, String userPassword);
}
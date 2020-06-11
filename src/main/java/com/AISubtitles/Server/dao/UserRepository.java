package com.AISubtitles.Server.dao;


import com.AISubtitles.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserPhoneNumber(String userPhoneNumber);

    User findByUserEmail(String userEmail);
}

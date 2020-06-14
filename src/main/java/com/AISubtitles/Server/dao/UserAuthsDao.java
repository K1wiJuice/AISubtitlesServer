package com.AISubtitles.Server.dao;


import com.AISubtitles.Server.domain.UserAuths;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthsDao extends JpaRepository<UserAuths, Integer> {

}

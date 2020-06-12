package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.UserModification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModificationDao extends JpaRepository<UserModification, Integer> {
    
}
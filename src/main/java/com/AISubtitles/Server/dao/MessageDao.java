package com.AISubtitles.Server.dao;

import java.util.List;

import com.AISubtitles.Server.domain.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message, Integer> {
    List<Message> findAllByToUserId(int toUserId);
}
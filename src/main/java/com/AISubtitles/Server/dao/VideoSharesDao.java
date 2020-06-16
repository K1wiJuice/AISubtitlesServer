package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.VideoShares;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSharesDao extends JpaRepository<VideoShares, Integer> {
}

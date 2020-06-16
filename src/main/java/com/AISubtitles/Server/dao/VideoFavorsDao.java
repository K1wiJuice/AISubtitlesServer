package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.VideoFavors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoFavorsDao extends JpaRepository<VideoFavors, Integer> {
    VideoFavors findByUserIdAndVideoId(int userId, int videoId);
}
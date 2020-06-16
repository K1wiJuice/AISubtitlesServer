package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.VideoComments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentsDao extends JpaRepository<VideoComments, Integer> {
}

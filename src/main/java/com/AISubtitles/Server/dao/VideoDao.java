package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDao extends JpaRepository<Video, Integer> {
}

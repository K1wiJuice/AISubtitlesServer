package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.VideoBrowses;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoBrowsesDao extends JpaRepository<VideoBrowses, Integer> {
}

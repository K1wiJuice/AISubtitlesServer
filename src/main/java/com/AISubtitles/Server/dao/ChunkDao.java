package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChunkDao extends JpaRepository<Chunk, Integer>, JpaSpecificationExecutor<Chunk> {
}

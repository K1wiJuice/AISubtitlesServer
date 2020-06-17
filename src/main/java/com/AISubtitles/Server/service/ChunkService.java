package com.AISubtitles.Server.service;


import com.AISubtitles.Server.domain.Chunk;

public interface ChunkService {

    void saveChunk(Chunk chunk);

    boolean checkChunk(String videoNameAndUserId, Integer chunkNumber);
}

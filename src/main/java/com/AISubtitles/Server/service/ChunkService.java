package com.AISubtitles.Server.service;


import com.AISubtitles.Server.domain.Chunk;

import java.util.List;

public interface ChunkService {

    void saveChunk(Chunk chunk);

    boolean checkChunk(String videoNameAndUserId, Integer chunkNumber);

    List<String> getAllVideosUploading(String userId);

    void cancel(String videoName, Integer userId);
}

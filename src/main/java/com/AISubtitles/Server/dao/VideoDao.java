package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VideoDao extends JpaRepository<Video, Integer> {

//    @Transactional
//    @Modifying

//    @Query(value = "update video_info  set video_path = :compressedVideoPath and video_p = :videoP where video_path = :videoPath" ,nativeQuery = true)
//    public Integer update(@Param("video_path")String videoPath, @Param("video_compressedPath")String compressedVideoPath,@Param("video_p") int videoP);

}

package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.VideoWithSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 9:26 2020/6/20
 * @ Description：对带有字幕的视频的处理
 * @ Modified By：
 * @Version: 1.0$
 */
public interface VideoWithSubtitleDao extends JpaRepository<VideoWithSubtitle,Integer> {
    @Transactional
    @Modifying

    @Query(value = "insert into videowithsubtitle_info(video_path,audio_path,videowithsubtitle_path) values(videoPath,audioPath,videoWithSubtitlePath)",nativeQuery = true)
    public Integer add(@Param("videoPath") String videoPath,@Param("audioPath") String audioPath, @Param("videoWithSubtitlePath") String videowithsubtitlePath);
}

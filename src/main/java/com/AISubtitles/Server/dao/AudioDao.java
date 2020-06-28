package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 8:40 2020/6/20
 * @ Description：对音频的一些数据库操作
 * @ Modified By：
 * @Version: $
 */
public interface AudioDao extends JpaRepository<Audio,Integer> {

    @Transactional
    @Modifying

    @Query(value = "insert into audio_info(audio_path) values(outputPath)",nativeQuery = true)
    public Integer voiceChanger(@Param("audio_Path") String outputPath);

}

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
//
//    @Query(value = "update video_info  set video_path = :compressedVideoPath and video_p = :videoP where video_path = :videoPath" ,nativeQuery = true)
//    public Integer compressVideo(@Param("video_path")String videoPath, @Param("video_compressedPath")String compressedVideoPath,@Param("video_p") int videoP);

    @Query(value = "insert into video_info(video_path) values(videoWithSubtitlePath)",nativeQuery = true)
    public Integer importSubtitle(@Param("videoPath") String videoWithSubtitlePath);
    /*
     * Gavin
     * 添加对应音频的字幕路径
     * Param audioPath    音频路径
     * Param subtitlePath 语音识别生成字幕路径
     */
    @Query(value = "update video_info  set video_zhsubtitle = :subtitlePath where video_audio = :audioPath" ,nativeQuery = true)
    public Integer audio2zhSubtitle(@Param("audioPath")String audioPath, @Param("subtitlePath")String subtitlePath);
    
    /*
     * 翻译成目标语言的字幕路径
     * Param subtitlePath    视频语言字幕路径
     * Param transSubtitlePath 语音识别生成字幕路径
     */
    @Query(value = "update video_info  set video_ensubtitle = :transSubtitlePath where video_zhsubtitle = :subtitlePath" ,nativeQuery = true)
    public Integer translateSubtitle(@Param("subtitlePath")String subtitlePath, @Param("transSubtitlePath")String transSubtitlePath);




    /*
     * Gavin
     * 视频语言字幕与目标语言字幕合并字幕路径
     * Param zhSubtitlePath    视频语言字幕路径
     * Param mergedSubtitlePath 合并字幕路径
     */
    @Query(value = "update video_info  set video_enzhsubtitle = :mergedSubtitlePath where video_zhsubtitle = :zhSubtitlePath" ,nativeQuery = true)
    public Integer mergeSubtitle(@Param("zhSubtitlePath")String zhSubtitlePath, @Param("mergedSubtitlePath")String mergedSubtitlePath);

    /*
     * Gavin
     * 将字幕转为JSON格式
     * Param srt_filename    srt字幕路径
     * Param out_filename    JSON格式字幕路径
     */
    @Query(value = "update video_info  set video_enzhsubtitlejson = :out_filename where video_enzhsubtitle = :srt_filename" ,nativeQuery = true)
    public Integer srt2json(@Param("srt_filename")String srt_filename, @Param("out_filename")String out_filename);


    @Transactional
    @Modifying
    @Query(value = "update video_info set video_cover = :video_cover where video_id = :video_id", nativeQuery = true)
    Integer modifyCover(@Param("video_id") Integer video_id, @Param("video_cover") String video_cover);


    @Transactional
    @Modifying
    @Query(value = "update video_info set video_path = :video_path where video_id = :video_id", nativeQuery = true)
    Integer modifyPath(@Param("video_id") Integer video_id, @Param("video_path") String video_path);

    @Transactional
    @Modifying
    @Query(value = "update video_info set video_name = :video_name where video_id = :video_id", nativeQuery = true)
    Integer modifyName(@Param("video_id") Integer video_id, @Param("video_name") String video_name);

    @Transactional
    @Modifying
    @Query(value = "update video_info set process_progress = :process_progress where video_id = :video_id", nativeQuery = true)
    Integer modifyProgress(@Param("video_id") Integer video_id, @Param("process_progress") Double process_progress);

}

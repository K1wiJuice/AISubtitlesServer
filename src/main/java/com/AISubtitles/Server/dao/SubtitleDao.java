package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.*;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SubtitleDao extends JpaRepository<Video, Integer> {
	Subtitle findByVideoId(String videoId);

//    @Transactional
//    @Modifying
//
//    @Query(value = "update video_info  set video_path = :compressedVideoPath and video_p = :videoP where video_path = :videoPath" ,nativeQuery = true)
//    public Integer update(@Param("video_path")String videoPath, @Param("video_compressedPath")String compressedVideoPath,@Param("video_p") int videoP);
//
//	@Entity
//	@Table(name = "subtitle_info")
//	@Data
//	public class Subtitle {
//
//	    @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    @Column(name = "subtitle_id")
//	    private int videoId;
//	   @Column(name = "user_id")
//	    private int userId;
//	    @Column(name = "video_path")
//	    private  String videoPath;
//	    @Column(name = "audio_path")
//	    private  String audioPath;
//	    @Column(name = "subtitle_zh_path")
//	    private String zhSubtitlePath;
//	    @Column(name = "subtitle_en_path")
//	    private String enSubtitlePath;
//	    @Column(name = "subtitle_merge_path")
//	    private String mergeSubtitlePath;
//	    @Column(name = "subtitle_mjson_path")
//	    private String mjsonSubtitlePath;
//	   // @Column(name = "subtitle_size")
//	    //private double subtitleSize;
//	    @Column(name = "subtitle_type")
//	    private String subtitleType;
    /*
     * Gavin
     * 添加对应音频的字幕路径
     * Param audioPath    音频路径
     * Param subtitlePath 语音识别生成字幕路径
     */
    @Query(value = "update subtitle_info  set subtitle_zh_path = :subtitlePath where audio_path = :audioPath" ,nativeQuery = true)
    public Integer audio2zhSubtitle(@Param("audioPath")String audioPath, @Param("subtitlePath")String subtitlePath);
    
    /*
     * 翻译成目标语言的字幕路径
     * Param subtitlePath    视频语言字幕路径
     * Param transSubtitlePath 语音识别生成字幕路径
     */
    @Query(value = "update subtitle_info  set subtitle_en_path = :transSubtitlePath where subtitle_zh_path = :subtitlePath" ,nativeQuery = true)
    public Integer translateSubtitle(@Param("subtitlePath")String subtitlePath, @Param("transSubtitlePath")String transSubtitlePath);

    /*
     * Gavin
     * 视频语言字幕与目标语言字幕合并字幕路径
     * Param zhSubtitlePath    视频语言字幕路径
     * Param mergedSubtitlePath 合并字幕路径
     */
    @Query(value = "update subtitle_info  set subtitle_merge_path = :mergedSubtitlePath where subtitle_zh_path = :zhSubtitlePath" ,nativeQuery = true)
    public Integer mergeSubtitle(@Param("zhSubtitlePath")String zhSubtitlePath, @Param("mergedSubtitlePath")String mergedSubtitlePath);

    /*
     * Gavin
     * 将字幕转为JSON格式
     * Param srt_filename    srt字幕路径
     * Param out_filename    JSON格式字幕路径
     */
    @Query(value = "update subtitle_info  set subtitle_mjson_path = :out_filename where subtitle_merge_path = :srt_filename" ,nativeQuery = true)
    public Integer srt2json(@Param("srt_filename")String srt_filename, @Param("out_filename")String out_filename);


    @Transactional
    @Modifying
    @Query(value = "update subtitle_info set video_cover = :video_cover where video_id = :video_id", nativeQuery = true)
    Integer modifyCover(@Param("video_id") Integer video_id, @Param("video_cover") String video_path);

}

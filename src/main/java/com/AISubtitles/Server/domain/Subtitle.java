package com.AISubtitles.Server.domain;

import lombok.Data;
import lombok.ToString;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;


/**
 * @ Author     ：lzl
 * @ Date       ：Created in 9:49 2020/6/19
 * @ Description：关于字幕的数据库
 * @ Modified By：
 * @Version: 1.0$
 */

@Entity
@Table(name = "subtitle_info")
@Data
@ToString
@Proxy(lazy = false)
public class Subtitle {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Integer videoId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "video_path")
    private  String videoPath;
    @Column(name = "audio_path")
    private  String audioPath;
    @Column(name = "subtitle_zh_path")
    private String zhSubtitlePath;
    @Column(name = "subtitle_en_path")
    private String enSubtitlePath;
    @Column(name = "subtitle_merge_path")
    private String mergeSubtitlePath;
    @Column(name = "subtitle_mjson_path")
    private String mjsonSubtitlePath;
   // @Column(name = "subtitle_size")
    //private double subtitleSize;
    @Column(name = "subtitle_type")
    private String subtitleType;
    
    
    
	


	public Integer getVideoId() {
		return videoId;
	}






	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}






	public Integer getUserId() {
		return userId;
	}






	public void setUserId(Integer userId) {
		this.userId = userId;
	}






	public String getVideoPath() {
		return videoPath;
	}






	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}






	public String getAudioPath() {
		return audioPath;
	}






	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}






	public String getZhSubtitlePath() {
		return zhSubtitlePath;
	}






	public void setZhSubtitlePath(String zhSubtitlePath) {
		this.zhSubtitlePath = zhSubtitlePath;
	}






	public String getEnSubtitlePath() {
		return enSubtitlePath;
	}






	public void setEnSubtitlePath(String enSubtitlePath) {
		this.enSubtitlePath = enSubtitlePath;
	}






	public String getMergeSubtitlePath() {
		return mergeSubtitlePath;
	}






	public void setMergeSubtitlePath(String mergeSubtitlePath) {
		this.mergeSubtitlePath = mergeSubtitlePath;
	}






	public String getMjsonSubtitlePath() {
		return mjsonSubtitlePath;
	}






	public void setMjsonSubtitlePath(String mjsonSubtitlePath) {
		this.mjsonSubtitlePath = mjsonSubtitlePath;
	}






	public String getSubtitleType() {
		return subtitleType;
	}






	public void setSubtitleType(String subtitleType) {
		this.subtitleType = subtitleType;
	}






	@Override
    public String toString() {
        return "Subtitle{" +
                "videoId=" + videoId +
                ", userId=" + userId +
                ", zhSubtitlePath='" + zhSubtitlePath + '\'' +
                ", enSubtitlePath='" + enSubtitlePath + '\'' +
                ", mergeSubtitlePath='" + mergeSubtitlePath + '\'' +
                ", mjsonSubtitlePath='" + mjsonSubtitlePath + '\'' +
                ", subtitleType='" + subtitleType + '\'' +
                '}';
    }
}

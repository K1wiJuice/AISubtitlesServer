package com.AISubtitles.Server.domain;

import lombok.Data;

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
public class Subtitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtitle_id")
    private int videoId;
   @Column(name = "user_id")
    private int userId;
    @Column(name = "video_path")
    private  String videoPath;
    @Column(name = "subtitle_path")
    private String subtitlePath;
   // @Column(name = "subtitle_size")
    //private double subtitleSize;
    @Column(name = "subtitle_type")
    private String subtitleType;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }

    public void setSubtitlePath(String subtitlePath) {
        this.subtitlePath = subtitlePath;
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
                ", subtitlePath='" + subtitlePath + '\'' +
                ", subtitleType='" + subtitleType + '\'' +
                '}';
    }
}

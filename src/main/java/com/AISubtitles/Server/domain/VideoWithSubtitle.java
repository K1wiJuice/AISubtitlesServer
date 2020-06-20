package com.AISubtitles.Server.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 9:14 2020/6/20
 * @ Description：保存合并完字幕的视频信息
 * @ Modified By：
 * @Version: 1.0$
 */
@Entity
@Table(name = "videowithsubtitle_info")
@Data
public class VideoWithSubtitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videowithsubtitle_id")
    private int videowithsubtitleId;
    @Column(name = "videowithsubtitle_path")
    private  String videowithsubtitlePath;
    @Column(name = "video_path")
    private  String videoPath;
    @Column(name = "subtitle_path")
    private String subtitlePath;
    @Column(name = "type")
    private String type;

    public int getVideowithsubtitleId() {
        return videowithsubtitleId;
    }

    public void setVideowithsubtitleId(int videowithsubtitleId) {
        this.videowithsubtitleId = videowithsubtitleId;
    }

    public String getVideowithsubtitlePath() {
        return videowithsubtitlePath;
    }

    public void setVideowithsubtitlePath(String videowithsubtitlePath) {
        this.videowithsubtitlePath = videowithsubtitlePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }

    public void setSubtitlePath(String subtitlePath) {
        this.subtitlePath = subtitlePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

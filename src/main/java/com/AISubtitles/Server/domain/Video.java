package com.AISubtitles.Server.domain;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "video_info")
@Data
@Proxy(lazy = false)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Integer videoId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "video_size")
    private Double videoSize;
    @Column(name = "video_format")
    private String videoFormat;
    @Column(name = "video_cover")
    private String videoCover;
    @Column(name = "video_duration")
    private Double videoDuration;
    @Column(name = "video_favors")
    private Integer videoFavors;
    @Column(name = "video_browses")
    private Integer videoBrowses;
    @Column(name = "video_shares")
    private Integer videoShares;
    @Column(name = "video_zhsubtitle")
    private Integer videoZHSubtitle;
    @Column(name = "video_ensubtitle")
    private Integer videoENSubtitle;
    @Column(name = "video_enzhsubtitle")
    private Integer videoENZHSubtitle;
    @Column(name = "video_enzhsubtitlejson")
    private Integer videoENZHSubtitleJSON;
//    @Column(name = "video_p")
//    private int videoP;
    @Column(name = "process_progress")
    private Double processProgress;




    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Double getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(double videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public Double getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(double videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Integer getVideoFavors() {
        return videoFavors;
    }

    public void setVideoFavors(int videoFavors) {
        this.videoFavors = videoFavors;
    }

    public Integer getVideoBrowses() {
        return videoBrowses;
    }

    public void setVideoBrowses(int videoBrowses) {
        this.videoBrowses = videoBrowses;
    }

    public Integer getVideoShares() {
        return videoShares;
    }

    public void setVideoShares(int videoShares) {
        this.videoShares = videoShares;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", userId=" + userId +
                ", videoName='" + videoName + '\'' +
                ", videoPath='" + videoPath + '\'' +
                ", videoSize=" + videoSize +
                ", videoFormat='" + videoFormat + '\'' +
                ", videoCover='" + videoCover + '\'' +
                ", videoDuration=" + videoDuration +
                ", videoFavors=" + videoFavors +
                ", videoBrowses=" + videoBrowses +
                ", videoShares=" + videoShares +
                '}';
    }
}

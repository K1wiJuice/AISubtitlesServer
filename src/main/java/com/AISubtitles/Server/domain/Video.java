package com.AISubtitles.Server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "video_info")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private int videoId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "video_path")
    private String videoPath;
    @Column(name = "video_size")
    private double videoSize;
    @Column(name = "video_format")
    private String videoFormat;
    @Column(name = "video_cover")
    private String videoCover;
    @Column(name = "video_duration")
    private double videoDuration;
    @Column(name = "video_favors")
    private int videoFavors;
    @Column(name = "video_browses")
    private int videoBrowses;
    @Column(name = "video_shares")
    private int videoShares;
    @Column(name = "video_comments")
    private int videoComments;
    @Column(name = "video_collections")
    private int videoCollections;

    @Column(name = "video_zhsubtitle")
    private Integer videoZHSubtitle;
    @Column(name = "video_ensubtitle")
    private Integer videoENSubtitle;
    @Column(name = "video_enzhsubtitle")
    private Integer videoENZHSubtitle;
    @Column(name = "video_enzhsubtitlejson")
    private Integer videoENZHSubtitleJSON;

    @Column(name = "video_p")
    private Integer videoP;
    @Column(name = "audioType")
    private Integer audio_type;

    @Column(name = "process_progress")
    private Double processProgress;

    @Column(name = "identifier")
    private String identifier;

    @CreatedDate
    @Column(name = "operation_time")
    private Date createTime;

}

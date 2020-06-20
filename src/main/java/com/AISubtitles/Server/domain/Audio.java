package com.AISubtitles.Server.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 8:38 2020/6/20
 * @ Description：关于音频的数据库
 * @ Modified By：
 * @Version: 1.0$
 */
@Entity
@Table(name = "audio_info")
@Data
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audio_id")
    private int audioId;
    @Column(name = "audio_path")
    private String audioPath;
    @Column(name = "video_path")
    private String videoPath;


}

package com.AISubtitles.Server.domain;


import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
@ToString
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chunkId;

    @Column
    private Integer chunkNumber;

    @Column
    private Long chunkSize;

    @Column
    private String videoNameUserId;

    @Transient
    private MultipartFile file;
}

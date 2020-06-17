package com.AISubtitles.Server.domain;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chunkId;

    @Column
    private Integer chunkNumber;

    @Column
    private Long chunkSize;

    @Column
    private String videoNameAndUserId;

    @Transient
    private MultipartFile file;
}

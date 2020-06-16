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
@Table(name = "video_browses")
public class VideoBrowses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "browse_id")
    private int browseId;

    @Column(name = "video_id")
    private int videoId;

    @Column(name = "user_id")
    private int userId;

    @CreatedDate
    @Column(name = "operation_time")
    private Date operationTime;

}
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
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msgId;

    @Column
    private String msgType;//system, favor, collection, comment

    @Column
    private String fromUserName;

    @Column
    private int toUserId;
        
    @Column
    private String videoName;

    @Column
    private String msgContent;

    @Column
    private int msgRead;//0未读，1已读

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;
    
}
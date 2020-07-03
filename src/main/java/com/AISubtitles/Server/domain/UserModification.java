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
@Table(name = "update_user_info_event")
public class UserModification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_info_field_name", length = 255)
    private String fieldName;

    @Column(name = "user_info_field_old_value", length = 255)
    private String oldValue;

    @Column(name = "user_info_field_new_value", length = 255)
    private String newValue;

    @CreatedDate
    @Column(name = "operation_time")
    private Date operationTime;

}
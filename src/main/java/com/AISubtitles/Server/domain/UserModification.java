package com.AISubtitles.Server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//时间戳字段由数据库自动记录，不设置setEventId方法

@Entity
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

    public int getEventId() {
        return eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
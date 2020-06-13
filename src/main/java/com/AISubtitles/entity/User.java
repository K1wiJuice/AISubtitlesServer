package com.AISubtitles.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_gender")
    private String userGender;
    @Column(name = "user_birthday")
    private Date userBirthday;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "image")
    private String image;
    @Column(name = "user_phone_number")
    private String userPhoneNumber;
}

package com.AISubtitles.Server.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name", length = 20)
    private String userName;

    @Column(name="user_gender")
    private String userGender;
    
    @Column(name="user_birthday")
    private Date userBirthday;
    
    @Column(name="image", length = 255)
    private String image;
    
    @Column(name="user_email", length = 255)
    private String userEmail;
    
    @Column(name="user_password", length = 255)
    private String userPassword;

    @Column(name="user_phone_number", length = 12)
    private String userPhoneNumber;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
   
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender= userGender;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }
    
    public String getImage() {
        return userName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}

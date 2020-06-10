package com.AISubtitles.Server.domain;

import javax.xml.crypto.Data;
import java.util.List;

public class User {

    private int userId;
    private String userName;
    private String UserGender;
    private Data userBirthday;
    private List<Account> accountList;

    public User() {
    }



    //构造方法还没写好
//    public User(int userId, String userName, String userGender) {
//        this.userId = userId;
//        this.userName = userName;
//        UserGender = userGender;
//    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", UserGender='" + UserGender + '\'' +
                ", userBirthday=" + userBirthday +
                '}';
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

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
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public Data getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Data userBirthday) {
        this.userBirthday = userBirthday;
    }
}

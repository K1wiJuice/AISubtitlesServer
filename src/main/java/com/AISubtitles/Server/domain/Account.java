package com.AISubtitles.Server.domain;

public class Account {

    private String userPhoneNumber;
    private String userEmail;
    private int userWechatId;
    private int userQqId;

    @Override
    public String toString() {
        return "AccountOfUser{" +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userWechatId=" + userWechatId +
                ", userQqId=" + userQqId +
                '}';
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserWechatId() {
        return userWechatId;
    }

    public void setUserWechatId(int userWechatId) {
        this.userWechatId = userWechatId;
    }

    public int getUserQqId() {
        return userQqId;
    }

    public void setUserQqId(int userQqId) {
        this.userQqId = userQqId;
    }
}

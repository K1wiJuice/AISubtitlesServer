package com.AISubtitles.common;

public class StatusConsts {

    //注册成功，登陆成功，修改个人信息成功
    public static final int STATUS_SUCCESS = 200;

    //注册失败，邮箱重复
    public static final int STATUS_DUPLICATE_EMAIL = 601;

    //注册失败，账号重复
    public static final int STATUS_DUPLICATE_PHONE_NUMBER = 602;

    //注册失败，邮箱验证码错误
    public static final int STATUS_VERIFICATION_CODE_ERROR = 603;

    //登录失败，账号/邮箱/手机号不存在
    public static final int STATUS_ACCOUNT_NOT_EXIST = 604;

    //登录失败，密码错误
    public static final int STATUS_WRONG_PASSWORD = 605;

    //登录失败，找回密码失败，邮箱/手机验证码错误
    public static final int STATUS_RECOVER_PASSWORD_ERROR = 606;

    //修改个人信息失败，输入内容不符合格式
    public static final int STATUS_MODIFY_ERROR = 607;

    //邮箱和手机号还未绑定用户，可以创建
    public static final int STATUS_CAN_CREATE_USER = 608;

}

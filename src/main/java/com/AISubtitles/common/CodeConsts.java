package com.AISubtitles.common;

public class CodeConsts {


    //注册成功，登陆成功，修改个人信息成功
    public static final int CODE_SUCCESS = 200;

    //没有访问权限，请登录
    public static final int CODE_NEED_LOGIN = 401;

    //服务器内部错误
    public static final int CODE_SERVER_ERROR = 500;

    //注册失败，邮箱重复
    public static final int CODE_DUPLICATE_EMAIL = 601;

    //注册失败，账号重复
    public static final int CODE_DUPLICATE_PHONE_NUMBER = 602;

    //注册失败，邮箱验证码错误
    public static final int CODE_VERIFICATION_CODE_ERROR = 603;

    //登录失败，账号/邮箱/手机号不存在
    public static final int CODE_ACCOUNT_NOT_EXIST = 604;

    //登录失败，密码错误
    public static final int CODE_WRONG_PASSWORD = 605;

    //登录失败，找回密码失败，邮箱/手机验证码错误
    public static final int CODE_RECOVER_PASSWORD_ERROR = 606;

    //修改个人信息失败，点赞/收藏/分享失败，输入内容不符合格式
    public static final int CODE_MODIFY_ERROR = 607;

    //邮箱和手机号还未绑定用户，可以创建
    public static final int CODE_CAN_CREATE_USER = 608;

    //点赞/收藏失败，已经点赞/收藏过
    public static final int CODE_HAVE_DONE = 609;

    //文件块上传失败
    public static final int CODE_CHUNK_UPLOAD_FAIL = 610;

    //该块已经上传过
    public static final int CODE_CHUNK_EXIST = 611;

    //该块未上传
    public static final int CODE_CHUNK_NOT_EXITS = 612;

    //视频合并失败
    public static final int CODE_MERGED_FAILED = 613;

}

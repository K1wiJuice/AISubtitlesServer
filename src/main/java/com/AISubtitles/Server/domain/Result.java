package com.AISubtitles.Server.domain;

public class Result<T> {


    /*返回类型：
    *code：
    *2xx（请求成功）表示成功处理了请求的状态代码
    *200（成功）服务器已成功处理了请求
    *5xx（尚未实施）服务器出错
    *500（服务器内部错误）服务器遇到错误，无法完成请求
    */
    private int code;
    /*status：
    *200 注册成功，登陆成功，修改个人信息成功
    *601 注册失败，邮箱重复
    *602 注册失败，账号重复
    *603 注册失败，邮箱验证码错误
    *604 登录失败，账号/邮箱/手机号不存在
    *605 登录失败，密码错误
    *606 登录失败，找回密码失败，邮箱/手机验证码错误
    *607 修改个人信息失败，输入内容不符合格式
    */
    private int status;
    //具体返回的数据
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

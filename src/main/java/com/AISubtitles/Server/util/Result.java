package com.AISubtitles.Server.util;

import java.util.List;

/**
 *
 * @author Gavin
 *  此类需与domain.Result整合,FindPasswordServiceImpl类的状态码随之更改
 *  
 */

public class Result<T> {
	

    // 返回状态码(和http状态码类似)
    private int code;
    // 返回描述信息(接口描述)
    private String message;

    private T data;

    private List<T> list;
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getList() {

        return list;
    }

    public void setList(List<T> list) {

        this.list = list;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

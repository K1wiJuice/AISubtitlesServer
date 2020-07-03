package com.AISubtitles.Server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.Result;

/**
 * 
 * @author Gavin
 *
 */
@Service
public interface FindPasswordService {

    /**
     * 修改用户密码
     * 
     * @param password
     * @param newpassword
     * @return
     */
    public Result<User> update(String newpassword);

    /**
     * 验证邮箱验证码
     * 
     * @param emailCode
     * @param session
     * @return
     */
    Result<User> validateEmailCode(String emailCode, HttpServletResponse response, HttpSession session);

    /**
     * 验证短信验证码
     * 
     * @param SMSCode
     * @param session
     * @return
     */
    Result<User> validateSMSCode(String SMSCode, HttpSession session);

    /**
     * 查询用户信息
     * 
     * @param request
     * @return
     */
    public Result<User> select(String accountnum);

}

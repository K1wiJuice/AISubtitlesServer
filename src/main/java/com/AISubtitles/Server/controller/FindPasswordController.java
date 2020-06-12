package com.AISubtitles.Server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.service.FindPasswordService;
import com.AISubtitles.Server.domain.Result;

/**
 * 
 * @author Gavin
 * session保存服务器发送的验证码,redis存储session
 * 写在前面：为了安全性，发送验证码后不能再修改邮箱（eg：点击发送验证码跳转页面）
 *
 */
@Controller
@RequestMapping(value = "/findpassword")
public class FindPasswordController {

    @Autowired
    FindPasswordService findPasswordService;

    @ResponseBody
    @PostMapping("/selectuserinfo")
    public Result<User> select(HttpServletRequest request) {
        return findPasswordService.select(request);
    }

    /**
     * 验证邮箱验证码
     * 
     * @author Gavin
     * @param emailCode
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping("/validatecode")
    public Result<User> validateCode(@RequestParam("emailCode") String emailCode, HttpSession session) {
        return this.findPasswordService.validateCode(emailCode, session);
    }

    /**
     * 修改密码
     * 
     * @param password
     * @param newpassword
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Result<User> update(String password, String newpassword) {
        return this.findPasswordService.update(password, newpassword);
    }
}
package com.AISubtitles.Server.controller;

import com.AISubtitles.common.CodeConsts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.service.TokenService;
import com.AISubtitles.Server.utils.Md5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserAuthsDao userAuthsDao;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "user/login")
    public Result login(@RequestParam String userEmail,
                        @RequestParam String userPassword,
                        HttpServletResponse response){
        Result result = new Result();
        result.setCode(CodeConsts.CODE_SERVER_ERROR);
        result.setData(null);
        User user = userDao.findByUserEmail(userEmail);
        UserAuths userAuths = userAuthsDao.findByUserIdAndUserPassword(user.getUserId(), Md5Utils.md5(userPassword));
        if (userAuths != null) {
            result.setData(user);
            result.setCode(CodeConsts.CODE_SUCCESS);

            String token = tokenService.getToken(userAuths);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);

            return result;
        }else {
           result.setData("用户名密码错误");
           result.setCode(CodeConsts.CODE_WRONG_PASSWORD);
           return result;
        }
    }

}

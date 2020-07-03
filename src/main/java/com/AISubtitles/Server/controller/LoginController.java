package com.AISubtitles.Server.controller;

import com.AISubtitles.common.CodeConsts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.service.TokenService;
import com.AISubtitles.Server.utils.Md5Utils;
import com.AISubtitles.Server.utils.TokenUtil;

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
    public Result login(String userEmail,
                        String userPhoneNumber,
                        String userId,
                        @RequestParam String userPassword,
                        HttpServletResponse response){
        Result result = new Result();
        result.setCode(CodeConsts.CODE_SERVER_ERROR);
        result.setData(null);
        User user;
        if (userEmail != null) {
            user = userDao.findByUserEmail(userEmail);
        } else if (userPhoneNumber != null) {
            user = userDao.findByUserPhoneNumber(userPhoneNumber);
        } else {
            user = userDao.findById(Integer.parseInt(userId)).get();
        }
        if (user == null) {
            result.setData("账户不存在");
            result.setCode(CodeConsts.CODE_ACCOUNT_NOT_EXIST);
            return result;
        }
        System.out.println(user);
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

    @UserLoginToken
    @PostMapping(value = "user/updateToken")
    public Result update(HttpServletResponse response) {
        Result result = new Result();
        int userId = TokenUtil.getTokenUserId();
        UserAuths userAuths = userAuthsDao.findById(userId).get();
        String token = tokenService.getToken(userAuths);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        response.addCookie(cookie);

        result.setCode(CodeConsts.CODE_SUCCESS);
        return result;
    }

}

package com.AISubtitles.Server.controller;

import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.Server.dao.UserAuthsDao;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.domain.UserAuths;
import com.AISubtitles.Server.utils.Md5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserAuthsDao userAuthsDao;

    @PostMapping(value = "user/login")
    public Result login(@RequestParam String userEmail,
                        @RequestParam String userPassword,
                        HttpSession session){
        Result result = new Result();
        result.setCode(CodeConsts.CODE_SERVER_ERROR);
        result.setData(null);
        User user = userDao.findByUserEmail(userEmail);
        UserAuths userAuths = userAuthsDao.findByUserIdAndUserPassword(user.getUserId(), Md5Utils.md5(userPassword));
        if (userAuths != null) {
            session.setAttribute("user",user);
            result.setCode(CodeConsts.CODE_SUCCESS);
            return result;
        }else {
            result.setData("用户名密码错误");
            result.setCode(CodeConsts.CODE_WRONG_PASSWORD);
            return result;
        }
    }

    @PostMapping(value = "user/logout")
    public Result logout(HttpSession session){
        Result result = new Result();
        session.removeAttribute("user");
        result.setData(null);
        result.setCode(CodeConsts.CODE_SUCCESS);
        return result;
    }

}

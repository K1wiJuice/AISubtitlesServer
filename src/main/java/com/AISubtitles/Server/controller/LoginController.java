package com.AISubtitles.Server.controller;

import com.AISubtitles.common.CodeConsts;
import com.AISubtitles.common.StatusConsts;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;

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

    @PostMapping(value = "user/login")
    public Result login(@RequestParam String userEmail,
                        @RequestParam String userPassword,
                        HttpSession session,
                        RedirectAttributes attributes){
        Result result = new Result();
        result.setCode(500);
        result.setData(null);
        User user = userDao.findByUserEmailAndUserPassword(userEmail,userPassword);
        if (user != null) {
            user.setUserPassword(null);
            session.setAttribute("user",user);
            result.setCode(200);
            return result;
        }else {
            attributes.addFlashAttribute("message","y用户名密码错误");
            result.setCode(605);
            return result;
        }
    }

    @PostMapping(value = "user/logout")
    public Result logout(HttpSession session){
        Result result = new Result();
        result.setCode(500);
        result.setData(null);
        session.removeAttribute("user");
        result.setCode(200);
        return result;
    }

}

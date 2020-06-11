package com.AISubtitles.Server.controller;

import javax.servlet.http.HttpSession;

import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class UserController {

    @Autowired
    UserDao userDao;


    @PostMapping(value = "user/regist")
    public Result handleRegist(User user) {
        Result result = new Result();
        result.setCode(500);
        result.setData(null);

        try {
            User userByPhoneNumber = userDao.findByUserPhoneNumber(user.getUserPhoneNumber());
            User userByEmail = userDao.findByUserEmail(user.getUserEmail());
            if (userByEmail != null) {
                result.setStatus(601);
                result.setData("该邮箱已存在");
            } else if (userByPhoneNumber != null)  {
                result.setStatus(602);
                result.setData("手机号已存在");
            } else {
                userDao.save(user);
                result.setStatus(200);
                result.setCode(200);
                result.setData(user);
            }
        } catch (Exception e) {
            result.setData(e.getCause());
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping(value = "user/login")
    public String login(@RequestParam String usereamil,
                        @RequestParam String userpassword,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userDao.findByUserEmailAndUserPassword(useremail,userpassword);
        if (user != null) {
            user.setUserPassword(null);
            session.setAttribute("user",user);
            return "user/index";
        }else {
            attributes.addFlashAttribute("message","y用户名密码错误");
            return "redirect:/user/login";
        }
    }

    @PostMapping(value = "user/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:user/login";
    }


}

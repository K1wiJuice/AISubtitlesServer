package com.AISubtitles.Server.controller;

import javax.servlet.http.HttpSession;

import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public User test01(@PathVariable("userId") Integer userId) {
        return userService.test01(userId);
    }

    @GetMapping("/user")
    public User test02(User user) {
        return userService.test02(user);
    }

    @PostMapping(value = "/regist")
    public Result handleRegist(User user) {
        return userService.regist(user);
    }

    public String login(@RequestParam String usereamil,
                        @RequestParam String userpassword,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(usereamil,userpassword);
        if (user != null) {
            user.setUserPassword(null);
            session.setAttribute("user",user);
            return "user/index";
        }else {
            attributes.addFlashAttribute("message","y用户名密码错误");
            return "redirect:/user/login";
        }
    }

    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:user/login";
    }


}

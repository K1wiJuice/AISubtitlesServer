package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


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

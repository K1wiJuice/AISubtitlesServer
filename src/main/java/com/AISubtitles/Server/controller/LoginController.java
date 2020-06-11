package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.dao.UserDao;
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
    public String login(@RequestParam String useremail,
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

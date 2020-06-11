package com.AISubtitles.Server.controller;


<<<<<<< HEAD
import com.AISubtitles.Server.dao.UserRepository;
=======
>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

@RestController
=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
public class UserController {


    @Autowired
<<<<<<< HEAD
    UserRepository userRepository;

    @PostMapping("/user/regist")
    public Result handleRegist(User user) {
        Result result = new Result();
        result.setCode(500);
        result.setData(null);

        try {
            User userByPhoneNumber = userRepository.findByUserPhoneNumber(user.getUserPhoneNumber());
            User userByEmail = userRepository.findByUserEmail(user.getUserEmail());
            if (userByEmail != null) {
                result.setStatus(601);
                result.setData("该邮箱已存在");
            } else if (userByPhoneNumber != null)  {
                result.setStatus(602);
                result.setData("手机号已存在");
            } else {
                userRepository.save(user);
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

=======
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

>>>>>>> ee2c2b0cef44ee90ff420df110f29c39318c8171
}

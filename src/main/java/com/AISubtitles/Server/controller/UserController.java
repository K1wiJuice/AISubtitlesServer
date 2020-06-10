package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.User;
import com.AISubtitles.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping(value = "user/regist")
    public Result handleRegist(User user) {
        return userService.regist(user);
    }

}

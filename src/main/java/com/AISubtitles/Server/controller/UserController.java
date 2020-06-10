package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.mapper.UserMapper;
import com.AISubtitles.Server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    public UserMapper userMapper;

    @ResponseBody
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        System.out.println(userId);
        System.out.println(userMapper);
        System.out.println(userMapper.getUserById(userId));
        return userMapper.getUserById(userId);
    }


    @GetMapping("/user")
    public User insertUser(User user) {
        userMapper.insertUser(user);
        return user;
    }


}

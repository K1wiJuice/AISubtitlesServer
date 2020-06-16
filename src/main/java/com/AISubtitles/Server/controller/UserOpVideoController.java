package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.UserOpVideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOpVideoController {

    @Autowired
    UserOpVideoService userOpVideoService;

    @UserLoginToken
    @PostMapping("/userOpVideo/add")
    public Result addRecord(String type, int videoId, String content) {
        return userOpVideoService.addRecord(type, videoId, content);
    }

    @UserLoginToken
    @PostMapping("/userOpVideo/delete")
    public Result deleteRecord(String type, int videoId) {
        return userOpVideoService.deleteRecord(type, videoId);
    }

}
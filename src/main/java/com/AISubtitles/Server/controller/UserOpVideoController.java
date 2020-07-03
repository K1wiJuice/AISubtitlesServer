package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.annotation.UserLoginToken;
import com.AISubtitles.Server.dao.UserDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.UserOpVideoService;
import com.AISubtitles.common.CodeConsts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOpVideoController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserOpVideoService userOpVideoService;
    
    @UserLoginToken
    @PostMapping("/userOpVideo/add")
    public Result addRecord(String type, int videoId, String content) {
        return userOpVideoService.addRecord(type, videoId, content);
    }

    @UserLoginToken
    @PostMapping("/userOpVideo/search")
    public Result searchRecord(int videoId) {
        return userOpVideoService.searchRecord(videoId);
    }

    @PostMapping("/findVideoInfo")
    public Result findVideoInfo(int videoId) {
        return userOpVideoService.findVideoInfo(videoId);
    }

    @UserLoginToken
    @PostMapping("/userOpVideo/delete")
    public Result deleteRecord(String type, int videoId) {
        return userOpVideoService.deleteRecord(type, videoId);
    }

    @PostMapping("/video/all")
    public Result findAllVideo(int begin, int end) {
        return userOpVideoService.allVideo(begin, end);
    }

    @UserLoginToken
    @PostMapping("/video/own")
    public Result ownVideo(int begin, int end) {
        return userOpVideoService.ownVideo(begin, end);
    }

    @UserLoginToken
    @PostMapping("/video/collections")
    public Result collectionVideo(int begin, int end) {
        return userOpVideoService.collectionVideo(begin, end);
    }

    @PostMapping("/video/search")
    public Result search(String key, int begin, int end) {
        return userOpVideoService.searchVideo("%"+key+"%", begin, end);
    }

}
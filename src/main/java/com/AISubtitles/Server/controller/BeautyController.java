package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.common.CodeConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BeautyController {

    @Autowired
    BeautifyService beautifyService;

    @GetMapping("/beautify")
    public Result beautify(Integer videoId, String newVideo, int white, int smooth, int facelift, int eye) {
        beautifyService.setThreadsNums(3);
        return beautifyService.beautify(videoId, newVideo, white, smooth, facelift, eye);
    }
}

package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.GenerateCoverPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenerateCoverPageController {

    @Autowired
    GenerateCoverPageService generateCoverPageService;

    @GetMapping("/generateCoverPage")
    public Result generateCoverPage(Integer videoId, String time) {
        return generateCoverPageService.generateCoverPage(videoId, time);
    }
}

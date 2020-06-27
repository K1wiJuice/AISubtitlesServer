package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.VideoFilterService;
import com.AISubtitles.common.CodeConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilterController {

    @Autowired
    VideoFilterService videoFilterService;

    @GetMapping("/filter")
    public Result addFilter(Integer videoId,
                            String newVideo,
                            Integer table) {
        videoFilterService.setThreadsNums(3);
        return videoFilterService.filter(videoId, newVideo, table);
    }

}

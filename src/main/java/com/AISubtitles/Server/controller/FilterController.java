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
    public Result addFilter(String video,
                            String newVideo,
                            Integer table) {
        Result result = new Result();
        videoFilterService.setThreadsNums(3);
        try {
            videoFilterService.filter(video, newVideo, table);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("滤镜添加成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("滤镜添加失败");
        }

        return result;
    }

}

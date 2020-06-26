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
        Result result = new Result();
        beautifyService.setThreadsNums(3);
        try {
            beautifyService.beautify(videoId, newVideo, white, smooth, facelift, eye);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("美颜添加成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("美颜添加失败");
        }

        return result;
    }
}

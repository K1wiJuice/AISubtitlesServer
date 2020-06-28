package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.Server.service.VideoFilterService;
import com.AISubtitles.Server.service.VideoSupportService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VideoProcessController {

    @Autowired
    VideoFilterService videoFilterService;
    VideoSupportService videoSupportService;

    @Autowired
    BeautifyService beautifyService;

    @PostMapping("/videoProcess")
    public Result videoProcess(JSONArray jsonArray) {
        Result result = new Result();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String operationType = jsonObject.getString("operationType");
            if ("filter".equals(operationType)) {
                videoFilterService.setThreadsNums(3);
                Integer videoId = jsonObject.getInteger("videoId");
                String newVideo = jsonObject.getString("newVideo");
                Integer table = jsonObject.getInteger("table");
                videoFilterService.filter(videoId, newVideo, table);
            } else if ("beautify".equals(operationType)) {
                beautifyService.setThreadsNums(3);
                Integer videoId = jsonObject.getInteger("videoId");
                String newVideo = jsonObject.getString("newVideo");
                Integer white = jsonObject.getInteger("white");
                Integer smooth = jsonObject.getInteger("smooth");
                Integer facelift = jsonObject.getInteger("facelift");
                Integer eye = jsonObject.getInteger("eye");
                beautifyService.beautify(videoId, newVideo, white, smooth, facelift, eye);
            }else if("compressVideo".equals(operationType)){
                Integer videoId = jsonObject.getInteger("videoId");
                String videoPath = jsonObject.getString("videoPath");
                String compressedVideoPath = jsonObject.getString("compressedVideoPath");
                Integer videoP = jsonObject.getInteger("videoP");
                videoSupportService.compressVideo(videoPath,compressedVideoPath,videoP);
            }else if("importSubtitle".equals(operationType)){
                Integer videoId = jsonObject.getInteger("videoId");
                String videoPath = jsonObject.getString("videoPath");
                String subtitlePath = jsonObject.getString("subtitlePath");
                String videoWithSubtitlePath = jsonObject.getString("videoWithSubtitlePath");
                videoSupportService.importSubtitle(videoPath,subtitlePath,videoWithSubtitlePath);
            }else if("voiceChanger".equals(operationType)){
                Integer videoId = jsonObject.getInteger("videoId");
                String voicePath = jsonObject.getString("audioPath");
                String outputPath = jsonObject.getString("outputPath");
                Integer type = jsonObject.getInteger("type");
                videoSupportService.voiceChanger(voicePath,outputPath,type);
            }
        }

        return result;
    }
}

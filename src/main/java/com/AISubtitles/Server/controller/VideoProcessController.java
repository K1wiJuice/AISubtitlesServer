package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.Server.service.VideoFilterService;
import com.AISubtitles.Server.service.VideoSupportService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VideoProcessController {

    @Autowired
    VideoFilterService videoFilterService;
    VideoSupportService videoSupportService;

    @Autowired
    BeautifyService beautifyService;

    @Autowired
    VideoDao videoDao;

    @PostMapping("/videoProcess")
    public Result videoProcess(JSONArray jsonArray) {
        videoFilterService.setThreadsNums(3);
        beautifyService.setThreadsNums(3);

        Result videoProcessResult = new Result();
        Video processedVideo = new Video();
        Integer videoId;
        List<String> tempVideosPath = new ArrayList();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            String operationType = jsonObject.getString("operationType");
            if (i == 0) videoId = jsonObject.getInteger("videoId");
            else videoId = processedVideo.getVideoId();

            if ("filter".equals(operationType)) {
                String newVideoPath = jsonObject.getString("newVideo");
                Integer table = jsonObject.getInteger("table");
                String newVideo = newVideoPath.substring(0, newVideoPath.length()-4) + i + ".mp4";
                videoProcessResult = videoFilterService.filter(videoId, newVideo, table);
            } else if ("beautify".equals(operationType)) {
                String newVideoPath = jsonObject.getString("newVideo");
                String newVideo = newVideoPath.substring(0, newVideoPath.length()-4) + i + ".mp4";
                Integer white = jsonObject.getInteger("white");
                Integer smooth = jsonObject.getInteger("smooth");
                Integer facelift = jsonObject.getInteger("facelift");
                Integer eye = jsonObject.getInteger("eye");
                videoProcessResult = beautifyService.beautify(videoId, newVideo, white, smooth, facelift, eye);
            }else if("compressVideo".equals(operationType)){
                videoId = jsonObject.getInteger("videoId");
                String videoPath = jsonObject.getString("videoPath");
                String compressedVideoPath = jsonObject.getString("compressedVideoPath");
                Integer videoP = jsonObject.getInteger("videoP");
                videoSupportService.compressVideo(videoPath,compressedVideoPath,videoP);
            }else if("importSubtitle".equals(operationType)){
                videoId = jsonObject.getInteger("videoId");
                String videoPath = jsonObject.getString("videoPath");
                String subtitlePath = jsonObject.getString("subtitlePath");
                String videoWithSubtitlePath = jsonObject.getString("videoWithSubtitlePath");
                videoSupportService.importSubtitle(videoPath,subtitlePath,videoWithSubtitlePath);
            }else if("voiceChanger".equals(operationType)){
                videoId = jsonObject.getInteger("videoId");
                String voicePath = jsonObject.getString("audioPath");
                String outputPath = jsonObject.getString("outputPath");
                Integer type = jsonObject.getInteger("type");
                videoSupportService.voiceChanger(voicePath,outputPath,type);
            }

            if (i == 0) {
                processedVideo = (Video) videoProcessResult.getData();
                videoDao.save(processedVideo);
            } else {
                Video temp = (Video)videoProcessResult.getData();
                processedVideo.setVideoPath(temp.getVideoPath());
                processedVideo.setVideoName(temp.getVideoName());
                videoDao.modifyPath(processedVideo.getVideoId(), processedVideo.getVideoPath());
                videoDao.modifyName(processedVideo.getVideoId(), processedVideo.getVideoName());
            }

            if (i != jsonArray.size()-1) {
                tempVideosPath.add(processedVideo.getVideoPath());
            } else {
                for (String tempVideoPath: tempVideosPath) {
                    new File(tempVideoPath).delete();
                }
            }

        }

        return videoProcessResult;
    }
}

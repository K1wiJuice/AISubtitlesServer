package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.Server.service.VideoFilterService;
import com.AISubtitles.Server.service.VideoSupportService;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VideoProcessController {

    @Autowired
    VideoFilterService videoFilterService;
    
    @Autowired
    VideoSupportService videoSupportService;

    @Autowired
    BeautifyService beautifyService;

    @Autowired
    VideoDao videoDao;

    @PostMapping("/getProgress")
    public Result getProcess(int videoId) {
        Result result = new Result();
        if(videoDao.findById(videoId).get().getProcessProgress() == null) {
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData(0);
            return result;
        }
        double process = videoDao.findById(videoId).get().getProcessProgress();
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(process);
        return result;
    }

    @PostMapping("/process")
    public Result videoProcess(String jsonString) throws UnsupportedEncodingException {
        jsonString = java.net.URLDecoder.decode(jsonString, "utf-8");
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        videoFilterService.setThreadsNums(3);
        beautifyService.setThreadsNums(3);

        Result videoProcessResult = new Result();
        int videoId;
        List<String> tempVideosPath = new ArrayList();

        for (int i = 0; i < jsonArray.size(); i++) {

            videoProcessResult.setCode(CodeConsts.CODE_SERVER_ERROR);

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            String operationType = jsonObject.getString("operationType");
            videoId = jsonObject.getInteger("videoId");
            
            Video video = videoDao.findById(videoId).get();

            if(i == 0) {
                video.setProcessProgress(0.0);
                videoDao.saveAndFlush(video);
            }

            if ("filter".equals(operationType)) {
                String newVideo = video.getIdentifier() + "-" + i + ".mp4";
                Integer table = jsonObject.getInteger("table");
                videoProcessResult = videoFilterService.filter(videoId, newVideo, table);
            } else if ("beautify".equals(operationType)) {
                String newVideo = video.getIdentifier() + "-" + i + ".mp4";
                Integer white = jsonObject.getInteger("white");
                Integer smooth = jsonObject.getInteger("smooth");
                Integer facelift = jsonObject.getInteger("facelift");
                Integer eye = jsonObject.getInteger("eye");
                videoProcessResult = beautifyService.beautify(videoId, newVideo, white, smooth, facelift, eye);
            } else if ("voiceChanger".equals(operationType)){
                Integer audioType = jsonObject.getInteger("audioType");
                videoProcessResult = videoSupportService.replaceAudio(videoId,audioType);
            } else {
                if (!"importSubtitle".equals(operationType)) continue;
            }

            Double progress = (1.0+i) / (jsonArray.size()+1);
            System.out.println(progress);
            System.out.println(videoProcessResult.getCode());
            video.setProcessProgress(progress);
            videoDao.modifyProgress(videoId, progress);
            
            if(videoProcessResult.getCode() == CodeConsts.CODE_SUCCESS) {
                Video temp = (Video) videoProcessResult.getData();
                video.setVideoPath(temp.getVideoPath());
                videoDao.saveAndFlush(video);
            }

            if (i != jsonArray.size()-1) {
                tempVideosPath.add(video.getVideoPath());
            } else {
                for (String tempVideoPath: tempVideosPath) {
                    new File(tempVideoPath).delete();
                }
                if(videoProcessResult.getCode() == CodeConsts.CODE_SUCCESS) {
                    video.setVideoPath(video.getVideoPath().substring(12));
                    videoDao.saveAndFlush(video);
                }

                videoSupportService.importSubtitle(videoId, "zh");
                videoSupportService.importSubtitle(videoId, "en");
                videoSupportService.importSubtitle(videoId, "merge");
                progress = 1.0;
                
                System.out.println(progress);
                System.out.println(videoProcessResult.getCode());

                video.setProcessProgress(progress);
                videoDao.saveAndFlush(video);

            }

        }

        

        return videoProcessResult;
    }
}

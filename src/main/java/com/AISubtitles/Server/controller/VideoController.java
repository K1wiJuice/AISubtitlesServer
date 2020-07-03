package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.ExecuteCommandService;
import com.AISubtitles.Server.service.VideoSupportService;
import com.AISubtitles.common.CodeConsts;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class VideoController {

    private String pythonExe;

    VideoController() {
        this.pythonExe = "python";
    }

    public void setPythonExe(final String path) {
        this.pythonExe = path;
    }
    @Autowired
    VideoDao videoDao;



    @GetMapping("/download")
    public void download(HttpServletResponse response, int videoId, String type) throws Exception {
        Video video = videoDao.findById(videoId).get();

        //获取文件输入流
        String path = "/home/ubuntu/video/" + video.getIdentifier() + "/"+video.getIdentifier() +"-" + type + ".mp4";

        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(new File(path, ""));

        //附件下载
        response.setHeader("content-disposition", "attachment;fileName="+ URLEncoder.encode(video.getVideoName(), "UTF-8")+"."+video.getVideoFormat());

        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();

        IOUtils.copy(fileInputStream, outputStream);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(outputStream);

    }

}

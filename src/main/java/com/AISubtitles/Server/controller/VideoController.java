package com.AISubtitles.Server.controller;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.ExecuteCommandService;
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
    AudioDao audioDao;
    VideoWithSubtitleDao videoWithSubtitleDao;


    @RequestMapping("/upload")
    public void upload(@RequestParam("videoToUpload")MultipartFile videoToUpload) throws Exception{

        //文件名
        String videoPath = videoToUpload.getOriginalFilename();

        //获取到的是个绝对路径，我们只需要最后边的文件名
        String videoName = videoPath.substring(videoPath.lastIndexOf("\\")+1);

        //文件后缀
        String extension = "." + FilenameUtils.getExtension(videoToUpload.getOriginalFilename());

        //文件大小
        long size = videoToUpload.getSize();

        //文件类型
        String contentType = videoToUpload.getContentType();

        //处理根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static/files";

        //第一个字符是/不过我不知道为什么...
        String dateDirPath = realPath.substring(1) + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File dateDir = new File(dateDirPath);
        if (!dateDir.exists()) dateDir.mkdirs();

        //处理文件上传
        videoToUpload.transferTo(new File(dateDir, videoName));

        //将文件信息放入数据库
        Video video = new Video();
        video.setUserId(1);
        video.setVideoName(videoName);
        video.setVideoPath(dateDirPath);
        video.setVideoSize(size);
        video.setVideoFormat(contentType);
        video.setVideoDuration(20);
        video.setVideoFavors(0);
        video.setVideoBrowses(0);
        video.setVideoShares(0);
        System.out.println(video);
        videoDao.save(video);

    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception {
        List<Video> videos = videoDao.findAll();
        Video video = videos.get(0);

        //获取文件输入流
        String path = video.getVideoPath()+"/"+video.getVideoName();

        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(new File(path, ""));

        //附件下载
        response.setHeader("content-disposition", "attachment;fileName="+video.getVideoName());

        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();

        IOUtils.copy(fileInputStream, outputStream);
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(outputStream);

    }
        @GetMapping("/compress")
        public Result compressVideo(final String pyFilePath, final String videoPath, final String compressedVideoPath,
                                    final int b) throws IOException, InterruptedException {
            Result result = new Result();
            try {
                final List<String> commList = new ArrayList<>(
                        Arrays.asList(this.pythonExe, pyFilePath, videoPath, compressedVideoPath, "" + b));
                ExecuteCommandService.exec((com.sun.tools.javac.util.List<String>) commList);
                videoDao.update(videoPath,compressedVideoPath, b);
                result.setCode(200);

            }catch (Exception e){
                e.printStackTrace();
                result.setCode(500);
            }
            return result;
        }

@GetMapping("/inputSubtitle")
    public Result importSubtitle(final String pyFilePath, final String videoPath, final String subtitlePath,
                               final String videoWithSubtitlePath) throws IOException, InterruptedException {
    Result result = new Result();
    try {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, videoPath, subtitlePath, videoWithSubtitlePath));
        ExecuteCommandService.exec((com.sun.tools.javac.util.List<String>) commList);
        videoWithSubtitleDao.add(videoPath, subtitlePath, videoWithSubtitlePath);
        result.setCode(200);

    }catch (Exception e){
        e.printStackTrace();
        result.setCode(500);
    }
    return result;
}

@GetMapping("/exportAudio")
public Result exportAudio(final String pyFilePath, final String videoPath, final String audioPath)
        throws IOException, InterruptedException {
        Result result = new Result();
        try{
                 final List<String> commList = new ArrayList<>(Arrays.asList(this.pythonExe, pyFilePath, videoPath, audioPath));
                 ExecuteCommandService.exec((com.sun.tools.javac.util.List<String>) commList);
                 audioDao.add(videoPath,audioPath);
                 result.setCode(200);
        }catch (Exception e){
                e.printStackTrace();
                result.setCode(500);
        }
         return  result;
}
}

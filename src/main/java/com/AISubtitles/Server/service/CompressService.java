package com.AISubtitles.Server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;

import org.springframework.beans.factory.annotation.Autowired;

public class CompressService {

    private String pythonExe;

    CompressService() {
        this.pythonExe = "python";
    }

    public void setPythonExe(final String path) {
        this.pythonExe = path;
    }
    
    @Autowired
    VideoDao videoDao;

    public Result compressVideo(final String pyFilePath, final String videoPath, final String compressedVideoPath,
        final int b) throws IOException, InterruptedException {
        Result result = new Result();
        try {
        final List<String> commList = new ArrayList<>(
        Arrays.asList(this.pythonExe, pyFilePath, videoPath, compressedVideoPath, "" + b));
        ExecuteCommandService.exec((com.sun.tools.javac.util.List<String>) commList);
        // videoDao.update(videoPath,compressedVideoPath, b);
        result.setCode(200);

        }catch (Exception e){
        e.printStackTrace();
        result.setCode(500);
        }
    return result;
}
    
}
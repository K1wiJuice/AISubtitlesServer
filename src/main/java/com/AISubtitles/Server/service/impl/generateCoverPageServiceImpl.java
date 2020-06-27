package com.AISubtitles.Server.service.impl;


import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.service.GenerateCoverPageService;
import com.AISubtitles.Server.utils.MediaProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class generateCoverPageServiceImpl implements GenerateCoverPageService {


    @Autowired
    VideoDao videoDao;

    @Override
    public void generateCoverPage(Integer videoId, String time) {

        String videoPath = videoDao.findById(videoId).get().getVideoPath();

        String pagePath = "src/main/resources/imgs/" + videoId + "--" + "coverPage.jpg";

        MediaProcess.generateCoverPage(videoPath, pagePath, time);

        System.out.println(pagePath);
        videoDao.modifyCover(videoId, pagePath);
    }


}

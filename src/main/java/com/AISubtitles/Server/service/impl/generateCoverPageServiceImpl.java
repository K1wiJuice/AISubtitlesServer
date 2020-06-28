package com.AISubtitles.Server.service.impl;


import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.GenerateCoverPageService;
import com.AISubtitles.Server.utils.MediaProcess;
import com.AISubtitles.common.CodeConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class generateCoverPageServiceImpl implements GenerateCoverPageService {


    @Autowired
    VideoDao videoDao;

    @Override
    public Result generateCoverPage(Integer videoId, String time) {

        Result result = new Result();

        String videoPath = videoDao.findById(videoId).get().getVideoPath();

        String pagePath = "src/main/resources/imgs/" + videoId + "--" + "coverPage.jpg";

        try {
            MediaProcess.generateCoverPage(videoPath, pagePath, time);
            System.out.println(pagePath);
            videoDao.modifyCover(videoId, pagePath);

            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("封面生成成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("封面生成失败");
        }

        return result;
    }


}

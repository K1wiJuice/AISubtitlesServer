package com.AISubtitles.Server.service.impl;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.Server.utils.TencentAI;
import com.AISubtitles.Server.utils.tencentai.FaceFusion;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FaceFusionServiceImpl implements FaceFusionService {


    @Autowired
    VideoDao videoDao;

    @Override
    public Result faceFusion(MultipartFile img, Integer videoId, String MaterialId) {
        Result result = new Result();
        String imgPath = "";
        try {
            img.transferTo(new File("src/main/resources/imgs", "src/main/resources/imgs/" + img.getOriginalFilename()));
            imgPath = "src/main/resources/imgs/" + img.getOriginalFilename();
//            imgPath = "src/main/resources/imgs/baby.jpg";

            String newImagePath = imgPath.substring(0, imgPath.length()-4) + "--" + videoId + "--coverPage.jpg";
            TencentAI.facefusion(imgPath, newImagePath, MaterialId);
            videoDao.modifyCover(videoId, newImagePath);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("换脸成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("换脸失败");
        } finally {
            new File(imgPath).delete();
        }

        return result;
    }

    @Override
    public JSONArray getDescribeMaterialList() {
        return FaceFusion.getDescribeMaterialList();
    }

}

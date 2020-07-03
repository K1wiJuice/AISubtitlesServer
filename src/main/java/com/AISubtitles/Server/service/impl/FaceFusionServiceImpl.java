package com.AISubtitles.Server.service.impl;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.Server.utils.Md5Utils;
import com.AISubtitles.Server.utils.TencentAI;
import com.AISubtitles.Server.utils.tencentai.FaceFusion;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FaceFusionServiceImpl implements FaceFusionService {


    @Autowired
    VideoDao videoDao;

    @Value("${image-path}")
    private String imageSavePath;

    @Override
    public Result faceFusion(MultipartFile img, Integer videoId, String MaterialId) {
        Result result = new Result();
        String imgPath = "";
        try {
            imgPath = "/home/ubuntu/imgs" + "/" + img.getOriginalFilename();
//            imgPath = "src/main/resources/imgs/baby.jpg";

            byte[] bytes = img.getBytes();
            Files.write(Paths.get(imgPath), bytes);

            String identifier = Md5Utils.md5(new String(bytes));
            String newImagePath = imageSavePath + "/" + identifier + "." + img.getOriginalFilename().split("\\.")[img.getOriginalFilename().split("\\.").length-1];
            System.out.println(newImagePath);
            
            TencentAI.facefusion(imgPath, newImagePath, MaterialId);
            videoDao.modifyCover(videoId, newImagePath.substring(12));
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData(videoDao.findById(videoId));
            result.setData("换脸成功");
        } catch (Exception e) {
            System.out.println(e.toString());
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

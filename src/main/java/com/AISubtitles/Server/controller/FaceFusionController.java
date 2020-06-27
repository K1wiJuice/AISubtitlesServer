package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FaceFusionController {

    @Autowired
    FaceFusionService faceFusionService;

    @PostMapping("/faceFusion")
    public Result faceFusion(MultipartFile img,
                             Integer videoId,
                             String MaterialId) {
        return faceFusionService.faceFusion(img, videoId, MaterialId);
    }


    @GetMapping("/materialList")
    public Result getDescribeMaterialList() {
        Result result = new Result();
        JSONArray jsonArray = faceFusionService.getDescribeMaterialList();
        result.setCode(CodeConsts.CODE_SUCCESS);
        result.setData(jsonArray);
        return result;
    }

}

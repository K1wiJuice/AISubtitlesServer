package com.AISubtitles.Server.controller;


import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.common.CodeConsts;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FaceFusionController {

    @Autowired
    FaceFusionService faceFusionService;

    @PostMapping("/faceFusion")
    public Result faceFusion(String imgPath,
                             String newImagePath,
                             String MaterialId) {
        Result result = new Result();
        try {
            faceFusionService.faceFusion(imgPath, newImagePath, MaterialId);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("换脸成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("换脸失败");
        }
        return result;
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

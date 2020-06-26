package com.AISubtitles.Server.service.impl;

import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.Server.utils.TencentAI;
import com.AISubtitles.Server.utils.tencentai.FaceFusion;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class FaceFusionServiceImpl implements FaceFusionService {


    @Override
    public void faceFusion(String imgPath, String newImagePath, String MaterialId) {
        TencentAI.facefusion(imgPath, newImagePath, MaterialId);
    }

    @Override
    public JSONArray getDescribeMaterialList() {
        return FaceFusion.getDescribeMaterialList();
    }

}

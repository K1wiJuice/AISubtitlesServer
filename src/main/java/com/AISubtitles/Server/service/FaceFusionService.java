package com.AISubtitles.Server.service;


import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface FaceFusionService {

    void faceFusion(String imgPath, String newImagePath, String MaterialId);

    JSONArray getDescribeMaterialList();

}

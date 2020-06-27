package com.AISubtitles.Server.service;


import com.AISubtitles.Server.domain.Result;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FaceFusionService {

    Result faceFusion(MultipartFile img, Integer VideoId, String MaterialId);

    JSONArray getDescribeMaterialList();

}

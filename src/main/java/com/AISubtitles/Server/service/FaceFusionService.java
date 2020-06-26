package com.AISubtitles.Server.service;


import org.springframework.stereotype.Service;

@Service
public interface FaceFusionService {

    void faceFusion(String imgPath, String newImagePath, String MaterialId);

}

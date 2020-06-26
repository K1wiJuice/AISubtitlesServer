package com.AISubtitles.Server;


import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.Server.service.VideoFilterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FilterTest {

    private static String videoPath = "C:\\Users\\10636\\Desktop\\lj\\test.mp4";

    private String newVideoPath = "C:\\Users\\10636\\Desktop\\lj\\test_new.mp4";

    @Autowired
    VideoFilterService videoFilterService;

    @Autowired
    BeautifyService beautifyService;

    @Autowired
    FaceFusionService faceFusionService;

    @Test
    public void testFilter() throws Exception {
        videoFilterService.setThreadsNums(3);
        videoFilterService.filter(videoPath, newVideoPath, 5);
    }

    @Test
    public void testBeauty() {
        try {
            beautifyService.beautify(videoPath, newVideoPath, 100, 100, 100, 100);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void faceFusion() {
        String imagePath = "src/main/resources/imgs/Aragaki.jpg";
        String outputPath = "src/main/resources/imgs/fusion.jpg";
        String m = "qc_303269_803589_6";
        faceFusionService.faceFusion(imagePath, outputPath, m);
    }
}

package com.AISubtitles.Server;


import com.AISubtitles.Server.service.BeautifyService;
import com.AISubtitles.Server.service.FaceFusionService;
import com.AISubtitles.Server.service.GenerateCoverPageService;
import com.AISubtitles.Server.service.VideoFilterService;
import com.AISubtitles.Server.utils.MediaProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FilterTest {

    private static String videoPath = "C:/Users/10636/Desktop/lj/test.mp4";

    private String newVideoPath = "test_new.mp4";

    @Autowired
    VideoFilterService videoFilterService;

    @Autowired
    BeautifyService beautifyService;

    @Autowired
    FaceFusionService faceFusionService;

    @Autowired
    GenerateCoverPageService generateCoverPageService;

    @Test
    public void testFilter() throws Exception {
        videoFilterService.setThreadsNums(3);
        System.out.println(videoFilterService.filter(3, newVideoPath, 5));
    }

    @Test
    public void testBeauty() {
        beautifyService.setThreadsNums(3);
        beautifyService.beautify(3, newVideoPath, 100, 100, 100, 100);
    }

    @Test
    public void faceFusion() {
        MultipartFile imagePath = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
        String m = "qc_303269_803589_6";
        faceFusionService.faceFusion(imagePath, 2, m);
    }

    @Test
    public void coverPage() {
        generateCoverPageService.generateCoverPage(1, "00:00:09");
    }
}

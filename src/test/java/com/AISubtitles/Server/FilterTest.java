package com.AISubtitles.Server;


import com.AISubtitles.Server.service.VideoFilterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FilterTest {

    private static String videoPath = "C:\\Users\\24113\\Desktop\\lj\\test.mp4";

    @Autowired
    VideoFilterService videoFilterService;

    @Test
    public void testFilter() throws Exception {
        videoFilterService.setThreadsNums(3);
        videoFilterService.filter(videoPath, 0);
    }
}

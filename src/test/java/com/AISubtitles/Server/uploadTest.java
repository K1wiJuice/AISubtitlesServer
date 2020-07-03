package com.AISubtitles.Server;

import com.AISubtitles.Server.controller.UploadController;
import com.AISubtitles.Server.controller.VideoController;
import com.AISubtitles.Server.domain.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class uploadTest {

    private String uploadFolder = "./files";

    @Test
    public void mergeTest() {
        Video video = new Video();
        video.setVideoShares(0);
        video.setVideoBrowses(0);
        video.setVideoFavors(0);
        video.setVideoDuration(200);
        video.setVideoFormat("mp4");
        video.setVideoName("node-v14.4.0-x64.msi");
        video.setVideoSize(30187520);
        video.setVideoCover("videoCover");
        video.setUserId(1);

        String videoPath = uploadFolder + "/" + "node-v14.4.0-x64.msi" + "--" + 1 + "node-v14.4.0-x64.msi";
        String folder = uploadFolder + "/" + "node-v14.4.0-x64.msi" + "--" + 1;
        UploadController.merge(videoPath, folder, "node-v14.4.0-x64.msi");
        video.setVideoPath(videoPath);
        System.out.println(video);
    }
}

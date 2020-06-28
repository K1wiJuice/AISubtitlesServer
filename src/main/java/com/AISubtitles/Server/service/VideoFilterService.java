package com.AISubtitles.Server.service;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.common.CodeConsts;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@Service
public class VideoFilterService {

    @Autowired
    VideoDao videoDao;

    private static int threadsNums;

    private static ImageProcessService imp;

    private static String exePath = "src/main/resources/lib/ffmpeg.exe";
    private static String imageFormat = FrameProcessService.imageFormat;

    private static String imageFolderPath = "src/main/resources/tables/shot";
    private static String newImageFolderPath = "src/main/resources/tables/shot_new";
    private static String imagePath = imageFolderPath + "/%d" + imageFormat;
    private static String newImagePath = newImageFolderPath + "/%d" + imageFormat;

    private static String black = "src/main/resources/tables/hb.jpg";
    private static String hf = "src/main/resources/tables/hf.jpg";
    private static String mk = "src/main/resources/tables/mk.jpg";
    private static String jd = "src/main/resources/tables/jd.jpg";
    private static String movie = "src/main/resources/tables/movie.jpg";
    private static String natural = "src/main/resources/tables/natural.jpg";


    private static String audioPath = "src/main/resources/tables/audio.mp3";

    private String videosPath = "src/main/resources/videos";

    private static String[] tables;

    public VideoFilterService() {
        tables = new String[]{black, hf, mk, jd, movie, natural};
    }

    public void setThreadsNums(int threadsNums) {
        this.threadsNums = threadsNums;
    }

    public Result filter(Integer videoId, String newVideoName, int tableIndex) {

        Result result = new Result();

        CountDownLatch latch = new CountDownLatch(threadsNums);
        String table = tables[tableIndex];

        Video oldVideo = videoDao.findById(videoId).get();

        String video = oldVideo.getVideoPath();
        String videoPath = videosPath + "/" + newVideoName;

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FrameProcessService fp = new FrameProcessService();
        try {
            fp.split(exePath, video, imageFolderPath, audioPath);
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("视频分帧失败");
            return result;
        }
        System.out.println("分帧成功");

        File file = new File(imageFolderPath);
        File[] files = file.listFiles();
        int nums = files.length;

        imp = new ImageProcessService(table);
        for (int i = 0; i < threadsNums; i++) {
            FilterThread testThread = new FilterThread(latch, nums*i/threadsNums+1, nums*(i+1)/threadsNums);
            testThread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e){}

        deleteFolder(imageFolderPath);
        System.out.println("帧处理成功");

//        String videoPath = video.substring(0, video.length()-4) + "_" + table.substring(table.lastIndexOf("\\") + 1, table.length()-4) + video.substring(video.length()-4);
        try {
            fp.integrate(exePath, videoPath, newImageFolderPath, video, audioPath);
            result.setCode(CodeConsts.CODE_SUCCESS);
            result.setData("滤镜添加成功");
        } catch (Exception e) {
            result.setCode(CodeConsts.CODE_SERVER_ERROR);
            result.setData("视频合帧失败");
            return result;
        }
        deleteFolder(newImageFolderPath);
        System.out.println("合帧成功");

        Video newVideo = new Video();
        newVideo.setVideoPath(videoPath);
        newVideo.setVideoBrowses(0);
//        newVideo.setVideoDuration(oldVideo.getVideoDuration());
        newVideo.setVideoFavors(0);
//        newVideo.setVideoFormat(oldVideo.getVideoFormat());
        newVideo.setVideoName(newVideoName);
        newVideo.setVideoShares(0);
//        newVideo.setVideoSize(oldVideo.getVideoSize());
//        newVideo.setVideoCover(oldVideo.getVideoCover());
//        newVideo.setVideoENSubtitle(oldVideo.getVideoENSubtitle());
//        newVideo.setVideoENZHSubtitle(oldVideo.getVideoENZHSubtitle());
//        newVideo.setVideoENZHSubtitleJSON(oldVideo.getVideoENZHSubtitleJSON());
//        newVideo.setVideoZHSubtitle(oldVideo.getVideoZHSubtitle());
        result.setData(newVideo);

        return result;
    }

    class FilterThread extends Thread{

        private int start, end;
        private CountDownLatch latch;

        public FilterThread(CountDownLatch latch, int start, int end) {
            this.latch = latch;
            this.start = start;
            this.end = end;
        }


        public void run() {
            for(int i = start; i <= end; i++) {
                String tempImagePath = String.format(imagePath, i);
                String tempNewImagePath = String.format(newImagePath, i);
                imp.changeColor(tempImagePath, tempNewImagePath);
            }
            latch.countDown();
        }
    }

    public void deleteFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
    }
}

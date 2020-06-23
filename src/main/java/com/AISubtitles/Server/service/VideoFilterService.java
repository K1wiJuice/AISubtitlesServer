package com.AISubtitles.Server.service;

import com.AISubtitles.filter.FrameProcess;
import com.AISubtitles.filter.ImageProcess;
import org.opencv.core.Core;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.CountDownLatch;


@Service
public class VideoFilterService {

    private static int threadsNums;

    private static ImageProcess imp;

    private static String exePath = "C:\\Users\\24113\\Downloads\\ffmpeg-20200615-9d80f3e-win64-static\\bin\\ffmpeg.exe";
    private static String imageFormat = FrameProcess.imageFormat;

    private static String imageFolderPath = "C:\\Users\\24113\\Desktop\\lj\\shot";
    private static String newImageFolderPath = "C:\\Users\\24113\\Desktop\\lj\\shot_new";
    private static String imagePath = imageFolderPath + "/%d" + imageFormat;
    private static String newImagePath = newImageFolderPath + "/%d" + imageFormat;

    private static String black = "C:\\Users\\24113\\Desktop\\lj\\tables\\hb.jpg";
    private static String ghost = "C:\\Users\\24113\\Desktop\\lj\\tables\\ghost.jpg";
    private static String stars = "C:\\Users\\24113\\Desktop\\lj\\tables\\stars.jpg";
    private static String jd = "C:\\Users\\24113\\Desktop\\lj\\tables\\jd.jpg";
    private static String movie = "C:\\Users\\24113\\Desktop\\lj\\tables\\movie.jpg";


    private static String[] tables;

    public VideoFilterService() {
        tables = new String[]{black, ghost, stars, jd, movie};
    }


    public void setThreadsNums(int threadsNums) {
        this.threadsNums = threadsNums;
    }


    public void filter(String video, int tableIndex) throws Exception {

        CountDownLatch latch = new CountDownLatch(threadsNums);
        String table = tables[tableIndex];

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        FrameProcess fp = new FrameProcess();
        fp.split(exePath, video, imageFolderPath);
        System.out.println("分帧成功");

        File file = new File(imageFolderPath);
        File[] files = file.listFiles();
        int nums = files.length;

        imp = new ImageProcess(table);
        for (int i = 0; i < threadsNums; i++) {
            FilterThread testThread = new FilterThread(latch, nums*i/threadsNums+1, nums*(i+1)/threadsNums);
            testThread.start();
        }

        latch.await();
        deleteFolder(imageFolderPath);
        System.out.println("帧处理成功");

        String videoPath = video.substring(0, video.length()-4) + "_" + table.substring(table.lastIndexOf("\\") + 1, table.length()-4) + video.substring(video.length()-4);
        fp.integrate(exePath, videoPath, newImageFolderPath, video);
        deleteFolder(newImageFolderPath);
        System.out.println("合帧成功");
    }

    static class FilterThread extends Thread{

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

    public static void deleteFolder(String folderPath) {
        File file = new File(folderPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
    }
}

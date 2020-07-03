package com.AISubtitles.Server.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrameProcessService {

    private int fps;
    public static final String imageFormat = ".jpg";

    /*这个方法的输入ffmpegApp是ffmpeg.exe这个程序的位置,例如"D:/ffmpeg/bin/ffmpeg.exe"
     * videoFilename是需要视频的位置，例如"C:/Users/Lenovo/Desktop/测试文件夹/重生.mp4"
     * path是一个文件夹，用于存储图片*/
    public void split(String ffmpegApp, String videoFilename,String path, String audioPath)throws IOException,InterruptedException {
        info(ffmpegApp,videoFilename);
        String COMMAND = "%s -i %s -r %s -f image2 %s";
        String newPath = path + "/%d" + imageFormat;
        String cmd = String.format(COMMAND, ffmpegApp, videoFilename, fps, newPath);

        final List<String> commList = new ArrayList<>(
            Arrays.asList(ffmpegApp, "-i", videoFilename, "-r", "" + fps, "-f", "image2", newPath));
        ExecuteCommandService.exec(commList);

        System.out.println("提取声音");
        voice(ffmpegApp, videoFilename, audioPath);
    }

    /*这个方法的输入ffmpegApp是ffmpeg.exe这个程序的位置,例如"D:/ffmpeg/bin/ffmpeg.exe"
     * videoFilename是需要视频的位置，例如"C:/Users/Lenovo/Desktop/测试文件夹/重生.mp4"
     * path是一个文件夹，用于存储图片
     * prevideo是初始视频的地址*/
    public void integrate(String ffmpegApp, String videoFilename,String path,String prevideo, String audioPath)throws IOException,InterruptedException {
        info(ffmpegApp,prevideo);
        String newPath = path + "/%d" + imageFormat;

        final List<String> commList = new ArrayList<>(
            Arrays.asList(ffmpegApp, "-r", "" + fps, "-i", newPath, "-i", audioPath, "-vf", "fps="+fps, videoFilename));
        ExecuteCommandService.exec(commList);

        File audio = new File(audioPath);
        audio.delete();
    }

    private void info(String ffmpegApp, String videoFilename) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ffmpegApp);
        buffer.append(" -i ");
        buffer.append(videoFilename);
        try {
            Process process = Runtime.getRuntime().exec(buffer.toString());
            InputStream in = process.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=br.readLine())!=null) {
                System.out.println(line);
                if(line.contains("fps")) {
                    String[] definitions = line.split(",");
                    for(int i=0; i<definitions.length; i++) {
                        if (definitions[i].contains("fps")) {
                            String definition = definitions[i].trim().split(" ")[0];
                            fps = Integer.valueOf(definition);
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void voice(String ffmpegApp, String videoFilename, String audioPath)throws IOException {
        final List<String> commList = new ArrayList<>(
            Arrays.asList(ffmpegApp, "-i", videoFilename, "-f", "mp3", "-vn", audioPath));
        ExecuteCommandService.exec(commList);
    }
}

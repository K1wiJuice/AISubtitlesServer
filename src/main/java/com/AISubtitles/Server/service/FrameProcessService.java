package com.AISubtitles.Server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        Process process = Runtime.getRuntime().exec(cmd); // 执行调用perl命令
        InputStream is = process.getErrorStream(); // 获取perl进程的输出流
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // 缓冲读入
        StringBuilder buf = new StringBuilder(); // 保存perl的输出结果流
        String line = null;
        while((line = br.readLine()) != null) buf.append(line); // 循环等待进程结束
        voice(ffmpegApp, videoFilename, audioPath);
    }

    /*这个方法的输入ffmpegApp是ffmpeg.exe这个程序的位置,例如"D:/ffmpeg/bin/ffmpeg.exe"
     * videoFilename是需要视频的位置，例如"C:/Users/Lenovo/Desktop/测试文件夹/重生.mp4"
     * path是一个文件夹，用于存储图片
     * prevideo是初始视频的地址*/
    public void integrate(String ffmpegApp, String videoFilename,String path,String prevideo, String audioPath)throws IOException,InterruptedException {
        info(ffmpegApp,prevideo);
        String COMMAND = "%s -r %s -i %s -i %s -vf fps=%s %s";
        String newPath = path + "/%d" + imageFormat;
        String cmd = String.format(COMMAND, ffmpegApp, fps, newPath, audioPath, fps, videoFilename);

        Process process = Runtime.getRuntime().exec(cmd); // 执行调用perl命令
        InputStream is = process.getErrorStream(); // 获取perl进程的输出流
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // 缓冲读入
        StringBuilder buf = new StringBuilder(); // 保存perl的输出结果流
        String line = null;
        while((line = br.readLine()) != null) buf.append(line); // 循环等待进程结束
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

    public void voice(String ffmpegApp, String videoFilename, String audioPath)throws IOException,InterruptedException {
        String COMMAND = "%s -i %s -f mp3 -vn %s";
        String cmd = String.format(COMMAND, ffmpegApp, videoFilename ,audioPath);

        Process process = Runtime.getRuntime().exec(cmd); // 执行调用perl命令
        java.io.InputStream is = process.getErrorStream(); // 获取perl进程的输出流
        BufferedReader br = new BufferedReader(new InputStreamReader(is)); // 缓冲读入
        StringBuilder buf = new StringBuilder(); // 保存perl的输出结果流
        String line = null;
        while((line = br.readLine()) != null) buf.append(line); // 循环等待进程结束
    }
}

package com.AISubtitles.Server.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubtitleSupport {

    private String pythonExe;

    SubtitleSupport() {
        this.pythonExe = "python";
    }

    public void setPythonExe(final String path) {
        this.pythonExe = path;
    }

    /**
     * 压缩视频：根据给定的码率，在指定路径生成一个视频文件
     * 
     * @author PY
     * @param pyFilePath          执行用的python脚本
     * @param videoPath           需要压缩的视频路径
     * @param compressedVideoPath 压缩后的视频路径
     * @param b                   调整道的码率大小
     * @throws IOException
     * @throws InterruptedException
     */
    public void compressVideo(final String pyFilePath, final String videoPath, final String compressedVideoPath,
            final int b) throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, videoPath, compressedVideoPath, "" + b));
        ExecuteCommand.exec(commList);
    }

    /**
     * 导出音频：在指定的路径上生成一个视频的音频文件
     * 
     * @author PY
     * @param pyFilePath 执行用的python脚本
     * @param videoPath  需要导出音频的视频路径
     * @param audioPath  音频生成路径
     * @throws IOException
     * @throws InterruptedException
     */
    public void exportAudio(final String pyFilePath, final String videoPath, final String audioPath)
            throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(Arrays.asList(this.pythonExe, pyFilePath, videoPath, audioPath));
        ExecuteCommand.exec(commList);
    }

    /**
     * 导入字幕：将字幕文件导入道视频中，并在指定的路径上生成这个带有字幕的视频
     * 
     * @author PY
     * @param pyFilePath            执行用的python脚本
     * @param videoPath             需要导入字幕的视频文件路径
     * @param subtitlePath          字幕路径
     * @param videoWithSubtitlePath 带有字幕的视频的生成路径
     * @throws IOException
     * @throws InterruptedException
     */
    public void importSubtitle(final String pyFilePath, final String videoPath, final String subtitlePath,
            final String videoWithSubtitlePath) throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, videoPath, subtitlePath, videoWithSubtitlePath));
        ExecuteCommand.exec(commList);
    }

    /**
     * 音频转字幕：给音频文件在指定路径上生成其字幕文件
     * 
     * @author PY
     * @param pyFilePath   执行用的python脚本
     * @param audioPath    需要生成字幕的音频路径
     * @param subtitlePath 字幕的生成路径
     * @throws IOException
     * @throws InterruptedException
     */
    public void audio2zhSubtitle(final String pyFilePath, final String audioPath, final String subtitlePath)
            throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, audioPath, subtitlePath));
        ExecuteCommand.exec(commList);
    }

    /**
     * 翻译字幕：给出源语言和目标语言，将字幕文件翻译，并生成翻译好的字幕文件
     * 
     * @author
     * @param pyFilePath        执行用的python脚本
     * @param subtitlePath      原始字幕文件的路径
     * @param transSubtitlePath 翻译后的字幕文件路径
     * @param source            源语言
     * @param target            目标语言
     * @throws IOException
     * @throws InterruptedException
     */
    public void translateSubtitle(final String pyFilePath, final String subtitlePath, final String transSubtitlePath,
            final String source, final String target) throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, subtitlePath, transSubtitlePath, source, target));
        ExecuteCommand.exec(commList);
    }

    /**
     * 合并字幕：将中英文字幕文件合并
     * 
     * @author
     * @param pyFilePath         执行用的python脚本
     * @param zhSubtitlePath     中文字幕文件路径
     * @param enSubtitlePath     英文字幕文件路径
     * @param mergedSubtitlePath 合并之后的字幕文件路径
     * @throws IOException
     * @throws InterruptedException
     */
    public void mergeSubtitle(final String pyFilePath, final String zhSubtitlePath, final String enSubtitlePath,
            final String mergedSubtitlePath) throws IOException, InterruptedException {
        final List<String> commList = new ArrayList<>(
                Arrays.asList(this.pythonExe, pyFilePath, zhSubtitlePath, enSubtitlePath, mergedSubtitlePath));
        ExecuteCommand.exec(commList);
    }
}
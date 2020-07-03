package com.AISubtitles.Server.utils;

import com.AISubtitles.Server.utils.tencentai.FaceFusion;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TencentAI {

    private static String pythonExe = "python";

    /**
     * 根据不同的系统，更换python执行器
     *
     * @param str
     * @author PY
     */
    public void setPythonExe(String str) {
        pythonExe = str;
    }

    /**
     * 获取人脸融合模板列表素材信息。这一部分主要用于传给前端，告诉他们我们可进行换脸的素材有哪些。
     *
     * @return 一个JSONArray，其中的每个JSONObject有两个键值对，一个是MaterialId，表示模板id，为String类型，另一个是Url，表示模板图片的网络地址
     */
    public static JSONArray getDescribeMaterialList() {
        return FaceFusion.getDescribeMaterialList();
    }

    /**
     * 人脸融合
     *
     * @param imgPath    用户人脸
     * @param outputPath 融合后的人脸
     * @param MaterialId 人脸融合模板id
     */
    public static void facefusion(String imgPath, String outputPath, String MaterialId) {
        FaceFusion.facefusion(imgPath, outputPath, MaterialId);
    }

    /**
     * 音频转为字幕
     *
     * @param pyFilePath     python文件路径
     * @param audioPath      输入音频文件的路径
     * @param zhSubtitlePath 输入字幕文件的路径
     * @author PY
     */
    public static void audio2zhSubtitle(String pyFilePath, String audioPath, String zhSubtitlePath) {
        List<String> commList = new ArrayList<>(Arrays.asList(pythonExe, pyFilePath, audioPath, zhSubtitlePath));
        ExecuteCommand.exec(commList);
    }

    /**
     * 翻译字幕
     *
     * @param pyFilePath        python文件路径
     * @param subtitlePath      输入的字幕文件路径
     * @param transSubtitlePath 翻译后字幕文件的路径
     * @param source            源语言
     * @param target            目标语言
     * @author PY
     */
    public static void translate(String pyFilePath, String subtitlePath, String transSubtitlePath, String source, String target) {
        List<String> commList = new ArrayList<>(Arrays.asList(pythonExe, pyFilePath, subtitlePath, transSubtitlePath, source, target));
        ExecuteCommand.exec(commList);
    }

    /**
     * 合并字幕文件
     *
     * @param pyFilePath         python文件路径
     * @param zhSubtitlePath     中文字幕文件路径
     * @param enSubtitlePath     英文字幕文件路径
     * @param mergedSubtitlePath 合成后的字幕文件路径
     * @author PY
     */
    public static void mergeSubtitle(String pyFilePath, String zhSubtitlePath, String enSubtitlePath, String mergedSubtitlePath) {
        List<String> commList = new ArrayList<>(Arrays.asList(pythonExe, pyFilePath, zhSubtitlePath, enSubtitlePath, mergedSubtitlePath));
        ExecuteCommand.exec(commList);
    }

    /**
     * 语音合成
     *
     * @param pyFilePath python文件路径
     * @param text       文本
     * @param voiceType  声音类型
     * @param langType   语言类型
     * @param outputPath 输出音频的路径
     */
    public static void textToVoice(String pyFilePath, String text, String voiceType, String langType, String outputPath) {
        List<String> commList = new ArrayList<>(Arrays.asList(pythonExe, pyFilePath, text, voiceType, langType, outputPath));
        ExecuteCommand.exec(commList);
    }

}

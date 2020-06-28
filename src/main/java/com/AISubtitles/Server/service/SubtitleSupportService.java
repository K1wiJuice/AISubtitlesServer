package com.AISubtitles.Server.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;

import com.AISubtitles.Server.dao.*;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Subtitle;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javassist.expr.NewArray;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 20:16 2020/6/18
 * @ Description：对视频和字幕的一些操作
 * @ Modified By：
 * @Version: 1.0$
 */
public class SubtitleSupportService {
	private String pythonExe;

    public SubtitleSupportService() {
        this.pythonExe = "python";
    }

    public void setPythonExe(final String path) {
        this.pythonExe = path;
    }
    
    Subtitle sub = new Subtitle();
    @Autowired
    VideoDao videoDao;
    AudioDao audioDao;
    SubtitleDao subtitleDao;
    VideoWithSubtitleDao videoWithSubtitleDao;

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
        ExecuteCommandService.exec(commList);
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
    public Result exportAudio(final String videoId)
            throws IOException, InterruptedException {
    	sub = subtitleDao.findByVideoId(videoId);
    	String pyFilePath = "python/export_audio.py";
    	String videoPath = sub.getVideoPath();
    	String audioPath;
    	if (sub.getAudioPath() == null) {
    		audioPath = "files/"+videoId+"/"+videoId+"_audio.mp3"; 		
    	}
    	else audioPath = sub.getZhSubtitlePath();
    	Result result = new Result();
        try {
        	final List<String> commList = new ArrayList<>(
        			Arrays.asList(this.pythonExe, pyFilePath, videoPath, audioPath));
            ExecuteCommandService.exec(commList);
            subtitleDao.export_audio(videoId, audioPath);
            result.setCode(200);
            result.setData("导出音频成功！");

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setData("导出音频失败！");
        }
        return result;
        
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
        ExecuteCommandService.exec(commList);
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
    public Result audio2zhSubtitle(String videoId)throws IOException, InterruptedException {
    	sub = subtitleDao.findByVideoId(videoId);
    	String pyFilePath = "python/audio2zhSubtitle.py";
    	String audioPath = sub.getAudioPath();
    	String subtitlePath;
    	if (sub.getZhSubtitlePath() == null) {
    		subtitlePath = "files/"+videoId+"/"+videoId+"_zhsub.srt"; 		
    	}
    	else subtitlePath = sub.getZhSubtitlePath();   	
    	Result result = new Result();
        try {
            final List<String> commList = new ArrayList<>(
                    Arrays.asList(this.pythonExe, pyFilePath, audioPath, subtitlePath));
            ExecuteCommandService.exec(commList);
            subtitleDao.audio2zhSubtitle(audioPath,subtitlePath);
            result.setCode(200);
            result.setData("语音识别成功！");

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setData("语音识别失败！");
        }
        return result;
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
    public Result translateSubtitle(final String videoId, String source, String target) 
    		throws IOException, InterruptedException {
    	if (source == null || source == "") {
    		source="zh";
    	}
    	if (target == null || target == "") {
    		target="en";
    	}
    	sub = subtitleDao.findByVideoId(videoId);
    	String pyFilePath = "python/demo_translate.py";
    	String subtitlePath = sub.getZhSubtitlePath();
    	String transSubtitlePath;
    	if (sub.getEnSubtitlePath() == null) {
    		transSubtitlePath = "files/"+videoId+"/"+videoId+"_ensub.srt"; 		
    	}
    	else transSubtitlePath = sub.getEnSubtitlePath();
    	Result result = new Result();
        try {
            final List<String> commList = new ArrayList<>(
                    Arrays.asList(this.pythonExe, pyFilePath, subtitlePath, transSubtitlePath, source, target));
            ExecuteCommandService.exec(commList);
            subtitleDao.translateSubtitle(subtitlePath,transSubtitlePath);
            result.setCode(200);
            result.setData("字幕翻译成功！");

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setData("字幕翻译失败！");
        }
        return result;
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
    public Result mergeSubtitle(final String videoId) throws IOException, InterruptedException {
    	sub = subtitleDao.findByVideoId(videoId);
    	String pyFilePath = "python/demo_merge.py";
    	String zhSubtitlePath = sub.getZhSubtitlePath();
    	String enSubtitlePath = sub.getEnSubtitlePath();
    	String mergedSubtitlePath;
    	if (sub.getMergeSubtitlePath() == null) {
    		mergedSubtitlePath = "files/"+videoId+"/"+videoId+"_mergesub.srt"; 		
    	}
    	else mergedSubtitlePath = sub.getEnSubtitlePath();
    	Result result = new Result();
        try {
            final List<String> commList = new ArrayList<>(
                    Arrays.asList(this.pythonExe, pyFilePath, zhSubtitlePath, enSubtitlePath, mergedSubtitlePath));
            ExecuteCommandService.exec(commList);
            subtitleDao.mergeSubtitle(zhSubtitlePath,mergedSubtitlePath);
            result.setCode(200);
            result.setData("字幕合并成功！");

        }catch (Exception e){
            e.printStackTrace();
            result.setCode(500);
            result.setData("字幕合并失败！");
        }
        return result;
    }

/**
 * srt格式字幕转json格式并返回数据
 * 
 * @param  inputPath   srt字幕路径
 * @throws IOException 
 * 
 */
public Result subtitleSrt2json(final String videoId) {
	sub = subtitleDao.findByVideoId(videoId);
	String inputPath = sub.getMergeSubtitlePath();
	Result result = new Result();
	try{	
	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "UTF-8"));
	String indexline,timeline,textline_zh,textline_en,kongline;
	JSONArray subtitle = new JSONArray();
    while ((indexline = reader.readLine()) != null && (timeline = reader.readLine()) != null && 
			(textline_zh = reader.readLine()) != null && (textline_en = reader.readLine()) != null &&(kongline = reader.readLine()) != null)
	{
		String rebegin = "(.*?) -->";
		String reend = "(.*?)(--> )(.*)";
		List<String> list = new ArrayList<String>();
		List<String> extvounoLists = new ArrayList<String>();
		Pattern pabegin = Pattern.compile(rebegin);
		Pattern paend = Pattern.compile(reend);
		Matcher mbegin = pabegin.matcher(timeline);
		Matcher mend = paend.matcher(timeline);
		JSONObject subs = new JSONObject();
		while (mend.find()) {  
            int i = 1;  
            list.add(mend.group(i));
            subs.put("end", mend.group(i+2));
            i++;  
        } 
		while (mbegin.find()) {  
            int i = 1;  
            list.add(mbegin.group(i));
            subs.put("begin", mbegin.group(i));
            i++;  
        }
		JSONArray texts = new JSONArray();
        texts.add(textline_zh);
        texts.add(textline_en);
        subs.put("texts", texts);
		subtitle.add(subs);
	}
    reader.close();
    result.setCode(200);
    result.setData(JSONObject.toJSONString(subtitle));
	//return JSONObject.toJSONString(subtitle);
    }
	catch (IOException e) {
		e.printStackTrace();
        result.setCode(500);
        result.setData("返回json数据失败！");
	}
	
	return result;
    }    
}



















package com.AISubtitles.Server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.ExecuteCommandService;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 19:14 2020/6/19 
 * @ Description：对字幕的导入，转换，合并等操作
 * @ Modified By：Updated by Gavin at 19:29 2020/6/25 --Version1.1$
 * @Version: 1.1$
 */

@RestController
public class SubtitleController {
	
    private String pythonExe;

    public SubtitleController() {
        this.pythonExe = "python";
    }

    public void setPythonExe(final String path) {
        this.pythonExe = path;
    }
    @Autowired
    VideoDao videoDao;
    AudioDao audioDao;
    VideoWithSubtitleDao videoWithSubtitleDao;
    
    @GetMapping("/audio2zhSubtitle")
  public Result audio2zhSubtitle(final String pyFilePath, final String audioPath, 
		  final String subtitlePath) throws IOException, InterruptedException {
      Result result = new Result();
      try {
          final List<String> commList = new ArrayList<>(
                  Arrays.asList(this.pythonExe, pyFilePath, audioPath, subtitlePath));
          ExecuteCommandService.exec(commList);
          videoDao.audio2zhSubtitle(audioPath,subtitlePath);
          result.setCode(200);

      }catch (Exception e){
          e.printStackTrace();
          result.setCode(500);
      }
      return result;
    }
      
      /*
       * 
       * source:视频语言
       * target:目标语言
       */
      
      @GetMapping("/translateSubtitle")
      public Result translateSubtitle(final String pyFilePath, final String subtitlePath, final String transSubtitlePath,
              final String source, final String target) throws IOException, InterruptedException {
          Result result = new Result();
          try {
              final List<String> commList = new ArrayList<>(
                      Arrays.asList(this.pythonExe, pyFilePath, subtitlePath, transSubtitlePath, source, target));
              ExecuteCommandService.exec(commList);
              videoDao.translateSubtitle(subtitlePath,transSubtitlePath);
              result.setCode(200);

          }catch (Exception e){
              e.printStackTrace();
              result.setCode(500);
          }
          return result;
      }
      
      @GetMapping("/mergeSubtitle")
      public Result mergeSubtitle(final String pyFilePath, final String zhSubtitlePath, final String enSubtitlePath,
              final String mergedSubtitlePath) throws IOException, InterruptedException {
          Result result = new Result();
          try {
              final List<String> commList = new ArrayList<>(
                      Arrays.asList(this.pythonExe, pyFilePath, zhSubtitlePath, enSubtitlePath, mergedSubtitlePath));
              ExecuteCommandService.exec(commList);
              videoDao.mergeSubtitle(zhSubtitlePath,mergedSubtitlePath);
              result.setCode(200);

          }catch (Exception e){
              e.printStackTrace();
              result.setCode(500);
          }
          return result;
      }
      
      @GetMapping("/srt2json")
      public Result srt2json(final String pyFilePath, final String srt_filename, final String out_filename
              ) throws IOException, InterruptedException {
          Result result = new Result();
          try {
              final List<String> commList = new ArrayList<>(
                      Arrays.asList(this.pythonExe, pyFilePath, srt_filename, out_filename));
              ExecuteCommandService.exec(commList);
              videoDao.srt2json(srt_filename,out_filename);
              result.setCode(200);

          }catch (Exception e){
              e.printStackTrace();
              result.setCode(500);
          }
          return result;
      }
  }



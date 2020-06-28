package com.AISubtitles.Server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.ExecuteCommandService;
import com.AISubtitles.Server.service.SubtitleSupportService;
import com.AISubtitles.Server.service.UserOpVideoService;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 19:14 2020/6/19 
 * @ Description：对字幕的导入，转换，合并等操作
 * @ Modified By：Updated by Gavin at 19:29 2020/6/25 --Version1.1$
 * @Version: 1.1$
 */

@RestController
public class SubtitleController {
	  @Autowired
      SubtitleSupportService subtitleSupportService;
	  
	  @PostMapping("/SubtitleSupport/audio&sub")
	  public Result AudioSub(final String videoId) 
	 	      throws IOException, InterruptedException {    	
    	  subtitleSupportService.exportAudio(videoId);
    	  return subtitleSupportService.audio2zhSubtitle(videoId);
      }
      
	  @PostMapping("/SubtitleSupport/getSubjson")
	  public Result getSubjson(final String videoId, String source, String target) 
			  throws IOException, InterruptedException{
		  subtitleSupportService.audio2zhSubtitle(videoId);
		  subtitleSupportService.translateSubtitle(videoId, source, target);
		  subtitleSupportService.mergeSubtitle(videoId);
		  return subtitleSupportService.subtitleSrt2json(videoId);
	  }
	  
	  @PostMapping("/SubtitleSupport/exportaudio")
      public Result export_audio(final String videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.exportAudio(videoId);     
      }
	  
      @PostMapping("/SubtitleSupport/audio2zhSubtitle")
      public Result audio2zhSubtitle(final String videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.audio2zhSubtitle(videoId);     
      }
      
      @PostMapping("/SubtitleSupport/translateSubtitle")
      public Result translateSubtitle(final String videoId, String source, String target) 
    		  throws IOException, InterruptedException {
          return subtitleSupportService.translateSubtitle(videoId, source, target);
      }
      
      @PostMapping("/SubtitleSupport/mergeSubtitle")
      public Result mergeSubtitle(final String videoId) throws IOException, InterruptedException {
         return subtitleSupportService.mergeSubtitle(videoId); 
      }
      
      /**
       * Java版本返回json数据
       * @param pyFilePath
       * @param srt_filename
       * @param out_filename
       * @return
       * @throws IOException
       * @throws InterruptedException
       */
      @PostMapping("/SubtitleSupport/srt2json")
      public Result srt2json(final String videoId) throws IOException {
    	  return subtitleSupportService.subtitleSrt2json(videoId);
      }
      
      /**
       * python版本生成json文件
       * @param pyFilePath
       * @param srt_filename
       * @param out_filename
       * @return
       * @throws IOException
       * @throws InterruptedException
       */
//      @PostMapping("/srt2json")
//      public Result srt2json(final String pyFilePath, final String srt_filename, final String out_filename
//              ) throws IOException, InterruptedException {
//          Result result = new Result();
//          try {
//              final List<String> commList = new ArrayList<>(
//                      Arrays.asList(this.pythonExe, pyFilePath, srt_filename, out_filename));
//              ExecuteCommandService.exec(commList);
//              videoDao.srt2json(srt_filename,out_filename);
//              result.setCode(200);
//
//          }catch (Exception e){
//              e.printStackTrace();
//              result.setCode(500);
//          }
//          return result;
//      }
  }



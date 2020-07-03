package com.AISubtitles.Server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.ExecuteCommandService;
import com.AISubtitles.Server.service.SubtitleSupportService;
import com.AISubtitles.Server.service.UserOpVideoService;
import com.AISubtitles.Server.service.VideoSupportService;
import com.alibaba.fastjson.JSONArray;

/**  @ Auth ：lzl
 * 
 * @ Date       ：Created in 19:1 @ Description：对字幕的导入，转换，合并等操作
 * 
 * @ Modified By：Updated by Gavin at 19:29 2020/6/25 --Version1.1$
 * @Version: 1.1$
 */

@RestController
public class SubtitleController {

    @Autowired
    @Resource
    SubtitleSupportService subtitleSupportService;

	  @PostMapping("/SubtitleSupport/getSubjson")
	  public Result getSubjson(Integer videoId) 
			  throws IOException, InterruptedException{
		  return subtitleSupportService.dsubtitleSrt2json(videoId);
	  }
	  
	  @GetMapping("/SubtitleSupport/exportaudio")
      public Result export_audio(Integer videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.exportAudio(videoId);     
      }
	  
	 @PostMapping(value = "/SubtitleSupport/json2srt") 
     //@PostMapping("/SubtitleSupport/json2srt/{videoId}")
	  public Result json2srt(String subtitle, Integer videoId) 
			  throws IOException {
            subtitle = java.net.URLDecoder.decode(subtitle, "utf-8");
            System.out.println(subtitle);
            JSONArray subtitleArray = JSONArray.parseArray(subtitle);
    	    return subtitleSupportService.subtitleJson2srt(subtitleArray, videoId);
      }
  
      @GetMapping("/SubtitleSupport/audio2zhSubtitle")
      public Result audio2zhSubtitle(Integer videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.audio2zhSubtitle(videoId);     
      }
      
      @GetMapping("/SubtitleSupport/translateSubtitle")
      public Result translateSubtitle(Integer videoId, String source, String target) 
    		  throws IOException, InterruptedException {
          return subtitleSupportService.translateSubtitle(videoId, source, target);
      }
      
      @GetMapping("/SubtitleSupport/mergeSubtitle")
      public Result mergeSubtitle(Integer videoId) throws IOException, InterruptedException {
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
      @GetMapping("/SubtitleSupport/srt2json")
      public Result srt2json(Integer videoId) throws IOException {
    	  return subtitleSupportService.dsubtitleSrt2json(videoId);
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



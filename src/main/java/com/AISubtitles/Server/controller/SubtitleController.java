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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AISubtitles.Server.dao.AudioDao;
import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.dao.VideoWithSubtitleDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.service.ExecuteCommandService;
import com.AISubtitles.Server.service.SubtitleSupportService;
import com.AISubtitles.Server.service.UserOpVideoService;
import com.alibaba.fastjson.JSONArray;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 19:14 2020/6/19 
 * @ Description：对字幕的导入，转换，合并等操作
 * @ Modified By：Updated by Gavin at 19:29 2020/6/25 --Version1.1$
 * 				  Updated by Gavin at 20:46 2020/6/29 --Version1.2$
 * @Version: 1.2$
 */

@RestController
public class SubtitleController {
	  @Autowired
	  @Resource
      SubtitleSupportService subtitleSupportService;
	  
	  @GetMapping("/SubtitleSupport/audiosub/{videoId}")
	  public Result AudioSub(@PathVariable(name = "videoId") Integer videoId) 
	 	      throws IOException, InterruptedException {   
		  System.out.println(videoId);
    	  subtitleSupportService.exportAudio(videoId);
    	  return subtitleSupportService.audio2zhSubtitle(videoId);
      }
      
	  @GetMapping("/SubtitleSupport/getSubjson/{videoId}")
	  public Result getSubjson(@PathVariable(name = "videoId") Integer videoId, String source, String target) 
			  throws IOException, InterruptedException{
		  
		  subtitleSupportService.translateSubtitle(videoId, source, target);
		  subtitleSupportService.mergeSubtitle(videoId);
		  return subtitleSupportService.dsubtitleSrt2json(videoId);
	  }
	  
	  @GetMapping("/SubtitleSupport/exportaudio/{videoId}")
      public Result export_audio(@PathVariable(name = "videoId") Integer videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.exportAudio(videoId);     
      }
	  
	 @RequestMapping(value = "/SubtitleSupport/json2srt/{videoId}", method = {RequestMethod.POST}) 
     //@PostMapping("/SubtitleSupport/json2srt/{videoId}")
	  public Result json2srt(@RequestBody JSONArray subtitle, @PathVariable(name = "videoId") Integer videoId) 
			  throws IOException {
    	  return subtitleSupportService.subtitleJson2srt(subtitle, videoId);
      }
  
      @GetMapping("/SubtitleSupport/audio2zhSubtitle/{videoId}")
      public Result audio2zhSubtitle(@PathVariable(name = "videoId") Integer videoId) 
	 	      throws IOException, InterruptedException {    	
    	  return subtitleSupportService.audio2zhSubtitle(videoId);     
      }
      
      @GetMapping("/SubtitleSupport/translateSubtitle/{videoId}")
      public Result translateSubtitle(@PathVariable(name = "videoId") Integer videoId, String source, String target) 
    		  throws IOException, InterruptedException {
          return subtitleSupportService.translateSubtitle(videoId, source, target);
      }
      
      @GetMapping("/SubtitleSupport/mergeSubtitle/{videoId}")
      public Result mergeSubtitle(@PathVariable(name = "videoId") Integer videoId) throws IOException, InterruptedException {
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
      @GetMapping("/SubtitleSupport/srt2json/{videoId}")
      public Result dsrt2json(@PathVariable(name = "videoId") Integer videoId) throws IOException {
    	  return subtitleSupportService.dsubtitleSrt2json(videoId);
      }
      @GetMapping("/SubtitleSupport/sdsrt2json/{videoId}")
      public Result sdsrt2json(@PathVariable(name = "videoId") Integer videoId, @RequestParam(name = "type") String type) 
    		  throws IOException {
    	  return subtitleSupportService.sdsubtitleSrt2json(videoId,type);
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



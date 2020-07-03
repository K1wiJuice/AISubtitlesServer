package com.AISubtitles.Server.service;

import com.AISubtitles.Server.dao.VideoDao;
import com.AISubtitles.Server.domain.Result;
import com.AISubtitles.Server.domain.Video;
import com.AISubtitles.common.CodeConsts;
import org.opencv.core.Core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class BeautifyService {

	@Autowired
	VideoDao videoDao;

	private String exePath = "ffmpeg";
	private String imageFolderPath;
	private String newImageFolderPath;
	private String imagePath;
	private String newImagePath;

	private static Imagebase64Service imagebase64Service;
	private static BeautifyPicService beautifyPicService;

	@Value("${video-path}")
	private String videosPath;

	private static int threadsNums;

	public BeautifyService() {
		imagebase64Service = new Imagebase64Service();
		beautifyPicService = new BeautifyPicService();
		threadsNums = 8;
	}

	public void setThreadsNums(int threadsNums) {
		this.threadsNums = threadsNums;
	}

	public Result beautify(Integer videoId, String newVideoName, int white, int smooth, int facelift, int eye) {

		Result result = new Result();

		CountDownLatch latch = new CountDownLatch(threadsNums);

		Video oldVideo = videoDao.findById(videoId).get();
		System.out.println(oldVideo);
		String videoPath = "/home/ubuntu" + oldVideo.getVideoPath();
		System.out.println(videoPath);
		String newVideoPath = videosPath + "/" + oldVideo.getIdentifier() + "/" + newVideoName;
		System.out.println(newVideoPath);

		imageFolderPath = videosPath + "/" + oldVideo.getIdentifier() + "/shot";
		File imgFile = new File(imageFolderPath);
		if(!imgFile.exists() && !imgFile.isDirectory()) imgFile.mkdir();
		imagePath = imageFolderPath + "/%d" + ".jpg";

		newImageFolderPath = videosPath + "/" + oldVideo.getIdentifier() + "/shot_new";
		imgFile = new File(newImageFolderPath);
		if(!imgFile.exists() && !imgFile.isDirectory()) imgFile.mkdir();
		newImagePath = newImageFolderPath + "/%d" + ".jpg";

		String audioFolderPath = "/home/ubuntu/video/" + oldVideo.getIdentifier() + "/audio";
		imgFile = new File(audioFolderPath);
		if(!imgFile.exists() && !imgFile.isDirectory()) imgFile.mkdir();
		String audioPath = audioFolderPath + "/audio.mp3";

		System.out.println("分帧开始");
		FrameProcessService frameProcessService = new FrameProcessService();
		try {
			frameProcessService.split(exePath, videoPath, imageFolderPath, audioPath);
		} catch (Exception e) {
			result.setCode(CodeConsts.CODE_SERVER_ERROR);
			result.setData("视频分帧失败");
			return result;
		}
		System.out.println("分帧成功");

		File file = new File(imageFolderPath);
		System.out.println(file);
		File[] files = file.listFiles();
		int nums = files.length;
		for (int i = 0; i < threadsNums; i++) {
			FilterThread testThread = new FilterThread(latch, nums*i/threadsNums+1, nums*(i+1)/threadsNums, white, smooth, facelift, eye);
			testThread.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {}


		deleteFolder(imageFolderPath);
		System.out.println("处理成功");
		try {
			frameProcessService.integrate(exePath, newVideoPath, newImageFolderPath, videoPath, audioPath);
			result.setCode(CodeConsts.CODE_SUCCESS);
		} catch (Exception e) {
			result.setCode(CodeConsts.CODE_SERVER_ERROR);
			result.setData("视频合帧失败");
			return result;
		}

		deleteFolder(newImageFolderPath);
		deleteFolder(audioFolderPath);

		System.out.println("和帧成功");
		Video newVideo = new Video();
		newVideo.setVideoPath(newVideoPath);
		
		result.setData(newVideo);

		return result;
	}

	class FilterThread extends Thread{

		private int start, end;
		private CountDownLatch latch;
		private int white;
		private int smooth;
		private int facelift;
		private int eye;

		public FilterThread(CountDownLatch latch, int start, int end,
							int white, int smooth, int facelift, int eye) {
			this.latch = latch;
			this.start = start;
			this.end = end;
			this.white = white;
			this.smooth = smooth;
			this.facelift = facelift;
			this.eye = eye;
		}


		public void run() {
			for(int i = start; i <= end; i++)
			{
				String tempImagePath = String.format(imagePath, i);
				String tempNewImagePath = String.format(newImagePath, i);
				String base = imagebase64Service.getImageBinary(tempImagePath);
				String temp = beautifyPicService.beautify_api("AKIDJGwlLenBRIvN246Y3JwOZJQIthYkKDTH", "gBSUNZOxJRL1QpxoNgj5C2dyC9p2B4tp", base, white, smooth, facelift, eye);
				imagebase64Service.base64StringToImage(temp,tempNewImagePath);
			}
			latch.countDown();
		}
	}

	public void deleteFolder(String folderPath) {
		File file = new File(folderPath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

}
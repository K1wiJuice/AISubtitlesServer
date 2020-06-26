package com.AISubtitles.Server.service;

import org.opencv.core.Core;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
public class BeautifyService {

	private static String exePath = "src/main/resources/lib/ffmpeg.exe";
	private static String imageFolderPath = "src/main/resources/tables/shot";
	private static String newImageFolderPath = "src/main/resources/tables/shot_new";
	private static String imagePath = imageFolderPath + "/%d" + ".jpg";
	private static String newImagePath = newImageFolderPath + "/%d" + ".jpg";

	private static Imagebase64Service imagebase64Service;
	private static BeautifyPicService beautifyPicService;

	private static String audioPath = "src/main/resources/tables/audio.mp3";

	private static int threadsNums;

	public BeautifyService() {
		imagebase64Service = new Imagebase64Service();
		beautifyPicService = new BeautifyPicService();
		threadsNums = 8;
	}

	public static void setThreadsNums(int threadsNums) {
		BeautifyService.threadsNums = threadsNums;
	}

	public void beautify(String videoPath, String newVideo, int white, int smooth, int facelift, int eye) throws IOException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(threadsNums);

		System.out.println("分帧开始");
		FrameProcessService frameProcessService = new FrameProcessService();
		frameProcessService.split(exePath, videoPath, imageFolderPath, audioPath);
		System.out.println("分帧成功");

		File file = new File(imageFolderPath);
    	File[] files = file.listFiles();
    	int nums = files.length;
		for (int i = 0; i < threadsNums; i++) {
			FilterThread testThread = new FilterThread(latch, nums*i/threadsNums+1, nums*(i+1)/threadsNums, white, smooth, facelift, eye);
			testThread.start();
		}
		latch.await();


		deleteFolder(imageFolderPath);
		System.out.println("处理成功");
		frameProcessService.integrate(exePath, newVideo, newImageFolderPath, videoPath, audioPath);

		deleteFolder(newImageFolderPath);
		System.out.println("和帧成功");
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
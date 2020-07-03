package com.AISubtitles.Server.service;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;


public class ImageProcessService {

	private Mat table;

	public ImageProcessService(String tablePath) {
		this.table = Imgcodecs.imread(tablePath);
	}

	public void changeColor(String imagePath, String newImagePath) {
		Mat image = Imgcodecs.imread(imagePath);
		for (int x = 0; x < image.width(); x++) {
			for (int y = 0; y < image.height(); y++) {
				double[] data = image.get(y, x);
				double b = data[0], g = data[1], r = data[2];
				int temp_y = (int) ((int)(g / 4) + (int)(b / 32) * 64);
				int temp_x = (int) ((int)(r / 4) + (int)((b % 32) / 4) * 64);
				double[] temp_data = this.table.get(temp_y, temp_x);
				data[0] = temp_data[0];
				data[1] = temp_data[1];
				data[2] = temp_data[2];
				image.put(y, x, data);
			}
		}
		MatOfInt matOfInt = new MatOfInt(Imgcodecs.IMWRITE_JPEG_QUALITY, 80);  // 保存JPG图片的质量 0~100
		Imgcodecs.imwrite(newImagePath, image, matOfInt);
	}
}

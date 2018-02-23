package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	private final static double THRESHOLD = 200.5;
	
	public static void main(String[] args) {
		try {
			BufferedImage readeImage = readeImage("image/1.jpg");
			saveImage(edgeDetection(readeImage,THRESHOLD),"out/1.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage readeImage(String path) throws FileNotFoundException, IOException {
		File picture=new File(path);
		return ImageIO.read(new FileInputStream(picture));
	}
	
	private static void saveImage(BufferedImage bi,String path) throws IOException {
		ImageIO.write(bi, "png", new File(path));
	}
	
	private static BufferedImage edgeDetection(BufferedImage readeImage,double threshold) {
		int width = readeImage.getWidth();
		int height = readeImage.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int RGB ;
				if ((calcPixel(readeImage.getRGB(j, i), 255.0, threshold)
						+ calcPixel(readeImage.getRGB(j + 1 >= width ? j : j + 1, i), -255.0, threshold)
						+ calcPixel(readeImage.getRGB(j, i), 255.0, threshold)
						+ calcPixel(readeImage.getRGB(j, i + 1 >= height ? i : i + 1), -255.0, threshold)) / 4.0 == 0.0) {
					RGB = new Color(0,0,0).getRGB();
				} else {
					RGB = new Color(255,255,255).getRGB();
				}
				bi.setRGB(j, i, RGB);
			}
		}
		return bi;
	}
	
	private static double calcPixel(int rgb ,double out ,double threshold) {
		int red = (rgb & 0xff0000) >> 16;  
		int greed = (rgb & 0xff00) >> 8;  
		int black = (rgb & 0xff);  
		if(0.299 * red + 0.587 * greed + 0.114 * black > threshold) {
			return 0.0;
		} else {
			return out;
		}
	}

}

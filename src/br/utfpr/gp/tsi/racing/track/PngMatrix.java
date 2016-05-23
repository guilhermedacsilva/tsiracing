package br.utfpr.gp.tsi.racing.track;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class PngMatrix {

	public static int[][] convertImage2Matrix(BufferedImage image) {
		Raster raster = image.getData();
		int[][] matrix = new int[1000][1000];
		int[] pixel = new int[3];
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				raster.getPixel(i, j, pixel);
				if (isPixelGrass(pixel)) {
					matrix[i][j] = Track.PIXEL_GRASS;
					
				} else if (isPixelRoad(pixel)) {
					matrix[i][j] = Track.PIXEL_ROAD;
					
				} else if (isPixelStart(pixel)) {
					matrix[i][j] = Track.PIXEL_START;
					
				} else if (isPixelCurve(pixel)) {
					matrix[i][j] = Track.PIXEL_CURVE;
					
				} else {
					throw new RuntimeException("Pixel undefined: x,y = " + i + "," + j);
				}
			}
		}
		return matrix;
	}
	
	/**
	 * grass is white
	 */
	private static boolean isPixelGrass(int[] pixel) {
		return pixel[0] == 255 && pixel[1] == 255 && pixel[2] == 255;
	}
	
	/**
	 * road is black
	 */
	private static boolean isPixelRoad(int[] pixel) {
		return pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0;
	}
	
	/**
	 * start is red
	 */
	private static boolean isPixelStart(int[] pixel) {
		return pixel[0] == 255 && pixel[1] == 0 && pixel[2] == 0;
	}
	
	/**
	 * curve is green
	 */
	private static boolean isPixelCurve(int[] pixel) {
		return pixel[0] == 0 && pixel[1] == 255 && pixel[2] == 0;
	}
	
}

package br.utfpr.gp.tsi.racing.util;

public class Geometry {

	/**
	 * 
	 * @param a first point of line
	 * @param b second point of line
	 * @param p point to know the side
	 * @return -1 to left. +1 to right. 0 to center.
	 */
	public static int getSideOfTheLine(int ax, int ay, int bx, int by, int px, int py) {
		return (int) Math.signum( (bx-ax)*(py-ay) - (by-ay)*(px-ax) );
	}
	
	public static double calcDistanceBetweenPoints(int x1, int y1, int x2, int y2) {
		return Math.abs(
					Math.sqrt(
						Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)
					)
				);
	}
	
}

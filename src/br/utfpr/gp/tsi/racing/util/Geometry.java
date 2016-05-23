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
	
}

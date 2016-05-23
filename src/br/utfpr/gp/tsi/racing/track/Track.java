package br.utfpr.gp.tsi.racing.track;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.utfpr.gp.tsi.racing.Game;
import br.utfpr.gp.tsi.racing.util.ListCircular;

public class Track {
	public static final int GRASS = 0;
	public static final int ROAD = 1;
	static final int START = 2;
	static final int CURVE = 3;
	
	private int[][] matrix;
	private Point start;
	private ArrayList<FixedPoint> curveList;
	private ArrayList<FixedPoint> roadList;
	/**
	 * store the roadPoint before the curvePoint
	 */
	private ArrayList<FixedPoint> roadBeforeCurveList;
	
	public Track(String name) throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResourceAsStream("res/"+name+".png"));
		matrix = PngMatrix.convertImage2Matrix(image);
		findStartPoint();
		buildRoadPoints();
	}

	private void findStartPoint() {
		for (int x = 0; x < 1000; x++) {
			for (int y = 0; y < 1000; y++) {
				if (matrix[x][y] == START) {
					start = new Point(x, y);
					matrix[x][y] = ROAD;
					return;
				}
			}
		}
	}
	
	private void buildRoadPoints() {
		roadList = new ArrayList<FixedPoint>(50000);
		curveList = new ArrayList<FixedPoint>(100);
		roadBeforeCurveList = new ArrayList<FixedPoint>(100);
		final FixedPoint startPoint = new FixedPoint(start.x, start.y);
		
		FixedPoint oldPoint = new FixedPoint(start.x, start.y-1);
		FixedPoint point = oldPoint;
		FixedPoint newPoint = startPoint;
		
		do {
			roadList.add(newPoint);
			oldPoint = point;
			point = newPoint;
			newPoint = searchNextPoint(oldPoint, point);
		} while (!newPoint.equals(startPoint) && roadList.size() < 50000);

		
//		if (Game.DEBUG) {
//			System.out.println("Curves: " + curveList.size());
//			for (FixedPoint p : curveList) {
//				System.out.println(p.x + " / " + p.y);
//			}
//		}
		
		if (roadList.size() >= 50000) {
			System.out.println("Error: Road size 50000+");
			System.exit(1);
		}
		
		if (Game.DEBUG) {
			System.out.println("*** Road size: " + roadList.size());
		}
		
	}
	
	/**
	 * @param oldPoint prevent go back
	 * @param point look the adjacent points
	 * @return
	 */
	private FixedPoint searchNextPoint(FixedPoint oldPoint, FixedPoint point) {
		int newX = 0,newY = 0,i,j;
		int countFounds = 0;
		FixedPoint newPoint = null;
		boolean hasCurve = false;
		
		for (j = 1; j >= -1; j--) {
			for (i = 1; i >= -1; i--) {
				if (j == 0 && i == 0) continue;
				
				newX = point.x+i;
				newY = point.y+j;
				
				if (isValidPoint(newX, newY)) {
					if (matrix[newX][newY] == ROAD && (oldPoint.x != newX || oldPoint.y != newY)) {
						countFounds++;
						newPoint = new FixedPoint(newX, newY);
					
					} else if (matrix[newX][newY] == CURVE) {
						curveList.add(new FixedPoint(newX, newY));
						matrix[newX][newY] = GRASS;
						hasCurve = true;
					}
				}
			}
		}
		
		if (countFounds == 0) {
			System.out.println("Error: Could not build road from track.\nx,y: " + point.x + "," + point.y);
			System.exit(1);
		} else if (countFounds == 1) {
			if (hasCurve) roadBeforeCurveList.add(newPoint);
			return newPoint;
		}
		System.out.println("Error: Building road: multiple paths.\nx,y: " + point.x + "," + point.y);
		System.exit(1);
		return null;
	}

	private boolean isValidPoint(int x, int y) {
		return x >= 0 && x <= 1000 && y >= 0 && y <= 1000;
	}
	
	public int calculateClosestRoadPoint(int roadIndex, Point location) {
		double distanceOld = FixedPoint.calculateDistance(location, roadList.get(roadIndex));
		double distanceNew = distanceOld;
		int index = roadIndex;
		while (distanceNew <= distanceOld) {
			index = ListCircular.next(roadList, index);
			distanceOld = distanceNew;
			distanceNew = FixedPoint.calculateDistance(location, roadList.get(index));
		}
		
		// try more points, because some curves need it
		int indexTry = index;
		for (int i = 0; i < 100; i++) {
			indexTry = ListCircular.next(roadList, indexTry);
			distanceNew = FixedPoint.calculateDistance(location, roadList.get(indexTry));
			if (distanceNew <= distanceOld) {
				distanceOld = distanceNew;
				index = indexTry;
//				System.out.println("hit " + location.toString());
			}
		}
		
		
//		if (Game.DEBUG) System.out.println("Closest point: " + roadIndex + ". Distance: " + distanceOld);
		
		return ListCircular.previous(roadList, index);
	}

	public Point getStart() {
		return start;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public ArrayList<FixedPoint> getCurveList() {
		return curveList;
	}
	
	public ArrayList<FixedPoint> getRoadList() {
		return roadList;
	}
	
	public ArrayList<FixedPoint> getRoadBeforeCurveList() {
		return roadBeforeCurveList;
	}

}

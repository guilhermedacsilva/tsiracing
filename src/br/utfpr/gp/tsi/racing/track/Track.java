package br.utfpr.gp.tsi.racing.track;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.utfpr.gp.tsi.racing.Debug;
import br.utfpr.gp.tsi.racing.util.CircularList;;

public class Track {
	public static final int PIXEL_GRASS = 0;
	public static final int PIXEL_ROAD = 1;
	public static final int PIXEL_START = 2;
	public static final int PIXEL_CURVE = 3;
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	private int[][] matrix;
	private Point start;
	private ArrayList<FixedPoint> curveList;
	private ArrayList<FixedPoint> roadList;
	
	/**
	 * store the roadPoint before the curvePoint
	 */
	private ArrayList<FixedPoint> roadBeforeCurveList;
	
	public Track(int trackNumber) throws IOException {
		final String trackFilePath = "res/"+trackNumber+".png";
		final BufferedImage image = ImageIO.read(getClass().getResourceAsStream(trackFilePath));
		matrix = PngMatrix.convertImage2Matrix(image);
		findStartPoint();
		buildRoadPoints();
	}

	private void findStartPoint() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (matrix[x][y] == PIXEL_START) {
					start = new Point(x, y);
					matrix[x][y] = PIXEL_ROAD;
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
		
		if (roadList.size() >= 50000) {
			System.out.println("Error: Road size 50000+");
			System.exit(1);
		}
		
		Debug.print("*** Road size: " + roadList.size());
	}
	
	/**
	 * @param oldPoint prevent go back
	 * @param point verify the adjacent points
	 * @return
	 */
	private FixedPoint searchNextPoint(FixedPoint oldPoint, FixedPoint point) {
		int newX = 0, newY = 0, offsetX, offsetY;
		int countFounds = 0;
		FixedPoint newPoint = null;
		boolean hasCurve = false;
		
		for (offsetY = 1; offsetY >= -1; offsetY--) {
			for (offsetX = 1; offsetX >= -1; offsetX--) {
				if (offsetY == 0 && offsetX == 0) continue;
				
				newX = point.x+offsetX;
				newY = point.y+offsetY;
				
				if (isValidPoint(newX, newY)) {
					if (matrix[newX][newY] == PIXEL_ROAD && (oldPoint.x != newX || oldPoint.y != newY)) {
						countFounds++;
						newPoint = new FixedPoint(newX, newY);
					
					} else if (matrix[newX][newY] == PIXEL_CURVE) {
						curveList.add(new FixedPoint(newX, newY));
						matrix[newX][newY] = PIXEL_GRASS;
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
		return x >= 0 && x <= WIDTH && y >= 0 && y <= HEIGHT;
	}
	
	public int calculateClosestRoadPoint(int roadIndex, Point location) {
		double distanceOld = FixedPoint.calculateDistance(location, roadList.get(roadIndex));
		double distanceNew = distanceOld;
		int index = roadIndex;
		while (distanceNew <= distanceOld) {
			index = CircularList.next(roadList, index);
			distanceOld = distanceNew;
			distanceNew = FixedPoint.calculateDistance(location, roadList.get(index));
		}
		
		// try more points, because some curves need it
		int indexTry = index;
		for (int i = 0; i < 100; i++) {
			indexTry = CircularList.next(roadList, indexTry);
			distanceNew = FixedPoint.calculateDistance(location, roadList.get(indexTry));
			if (distanceNew <= distanceOld) {
				distanceOld = distanceNew;
				index = indexTry;
				Debug.print("hit " + location.toString());
			}
		}
		
		Debug.print("Closest point: " + roadIndex + ". Distance: " + distanceOld);
		
		return CircularList.previous(roadList, index);
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

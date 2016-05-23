package br.utfpr.gp.tsi.racing.screen.jpct;

import br.utfpr.gp.tsi.racing.track.Track;
import br.utfpr.gp.tsi.racing.util.Geometry;

import com.threed.jpct.GenericVertexController;
import com.threed.jpct.SimpleVector;

/**
 * Transforms the plane vertex.
 * It elevates the track, so it will be higher than the sea/water.
 */
public class TrackVertexController extends GenericVertexController {
	private static final long serialVersionUID = 1L;
	private static final int SEARCH_RANGE = 75;
	private static final float HEIGHT_ROAD = 3f;
	private static final float HEIGHT_WATER = 0;
	
	private int[][] matrix;
	
	public TrackVertexController(int[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * matrix 1000,1000
	 * 0 = -50
	 * 1000 = 50
	 * 
	 * 3d = (m/10 - 50)
	 * 10 * (3d + 50) = m
	 */
	@Override
	public void apply() {
		SimpleVector[] source = getSourceMesh();
		SimpleVector[] dest = getDestinationMesh();
		int offsetX , offsetY;
		for (int i = 0; i < source.length; i++) {
			dest[i].x = source[i].x;
			dest[i].y = source[i].y;
			dest[i].z = source[i].z;
			
			if (dest[i].x != 50 && dest[i].y != 50) {
				offsetX = (int) (10 * (source[i].x + 50));
				offsetY = (int) (10 * (source[i].y + 50));
				dest[i].z -= calculateHeight(offsetX, offsetY);
			}
		}
	}
	
	/**
	 * If the point is close enough to the road, than it will return HEIGHT_ROAD.
	 */
	private float calculateHeight(int centerX, int centerY) {
		int matrixX, matrixY;
		double minDistanceFound = 100;
		double distanceBetweenPoints;
		for (int offsetX = -SEARCH_RANGE; offsetX <= SEARCH_RANGE; offsetX++) {
			for (int offsetY = -SEARCH_RANGE; offsetY <= SEARCH_RANGE; offsetY++) {
				matrixX = centerX+offsetX;
				matrixY = centerY+offsetY;
				if (matrixX < 0 || matrixX > 999 || matrixY < 0 || matrixY > 999) {
					continue;
				}
				if (matrix[matrixX][matrixY] == Track.PIXEL_ROAD) {
					distanceBetweenPoints = Geometry.calcDistanceBetweenPoints(centerX, centerY, matrixX, matrixY);
					if (distanceBetweenPoints < minDistanceFound) {
						minDistanceFound = distanceBetweenPoints;
					}
				}
			}
		}
		if (minDistanceFound < 0 || minDistanceFound > 70) {
			return HEIGHT_WATER;
		}
		return HEIGHT_ROAD;
	}
	
}

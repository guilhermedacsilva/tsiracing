package br.utfpr.gp.tsi.racing.screen.jpct;

import br.utfpr.gp.tsi.racing.track.Track;

import com.threed.jpct.GenericVertexController;
import com.threed.jpct.SimpleVector;

public class TrackVertexController extends GenericVertexController {
	private static final long serialVersionUID = 1L;
	private static final int SEARCH_RANGE = 75;
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
		SimpleVector[] s = getSourceMesh();
		SimpleVector[] d = getDestinationMesh();
		int mx , my;
		for (int i = 0; i < s.length; i++) {
			d[i].x = s[i].x;
			d[i].y = s[i].y;
			d[i].z = s[i].z;
			
			if (d[i].x == 50 || d[i].y == 50) continue;
			
			mx = (int) (10 * (s[i].x + 50));
			my = (int) (10 * (s[i].y + 50));
			d[i].z -= calculateHeight(mx, my);
		}
	}
	
	private float calculateHeight(int x, int y) {
		int mx, my;
		double closest = 100, distance;
		for (int i = -SEARCH_RANGE; i <= SEARCH_RANGE; i++) {
			for (int j = -SEARCH_RANGE; j <= SEARCH_RANGE; j++) {
				mx = x+i;
				my = y+j;
				if (mx < 0 || mx > 999 || my < 0 || my > 999) {
					continue;
				}
				if (matrix[mx][my] == Track.ROAD) {
					distance = Math.abs(Math.sqrt(
							Math.pow(x-mx, 2) + Math.pow(y-my, 2)
							));
					if (distance < closest) {
						closest = distance;
					}
				}
			}
		}
		if (closest < 0 || closest > 70) {
			return 0;
		}
		return 3f;
	}
	
}

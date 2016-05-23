package br.utfpr.gp.tsi.racing.track;

import java.awt.Point;

import br.utfpr.gp.tsi.racing.util.Geometry;

public final class FixedPoint {
	public final int x;
	public final int y;
	
	public FixedPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static double calculateDistance(Point p1, FixedPoint p2) {
		return Geometry.calcDistanceBetweenPoints(p1.x, p1.y, p2.x, p2.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedPoint other = (FixedPoint) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}

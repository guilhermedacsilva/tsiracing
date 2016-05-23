package br.utfpr.gp.tsi.racing.car;

import java.awt.Point;

public interface ICar {
	
	String getName();

	Point getLocation();
	
	double getAngleRadians();

	double getAngleDegrees();
	
}

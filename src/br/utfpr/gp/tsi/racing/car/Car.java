package br.utfpr.gp.tsi.racing.car;

import java.awt.Point;

import br.utfpr.gp.tsi.racing.track.FixedPoint;
import br.utfpr.gp.tsi.racing.track.Track;
import br.utfpr.gp.tsi.racing.util.Geometry;
import br.utfpr.gp.tsi.racing.util.ListCircular;

public class Car implements ICar {
	private static final double MAX_REAL_SPEED = 10;
	private static final double FACTOR_JS_SPEED = 200/MAX_REAL_SPEED;
	private static final int MAX_CENTER_DISTANCE = 30;

	private static JsCarInterpreter jsInterpreter;
	private static Track track;
	
	static final int PEDAL_NOTHING = 0;
	static final int PEDAL_ACCELERATE = 1;
	static final int PEDAL_BRAKE = 2;
	static final int WHEEL_NOTHING = 0;
	static final int WHEEL_LEFT = 1;
	static final int WHEEL_RIGHT = 2;

	private static final double SPEED_ACCELERATE = 0.35;
	private static final double SPEED_BRAKE = 3 * SPEED_ACCELERATE;
	private static final double WHEEL_TURN = Math.PI/20;

	private String name;
	private int jsCarIndex;
	private boolean bugged = false;
	
	private Point location;
	private int roadIndex = 0;
	private FixedPoint roadPoint;
	private int curveIndex = 0;
	private FixedPoint curvePoint;
	private int circuitFinished = 0;
	private int circuitIndex = 0;
	
	private double angleRadians;
	private double speed = 0;
	private int pedal;
	private int wheel;
	private int center = 0;
	private int curveDistanceOld;
	private int curveDistance;
	private int curveSide;
	
	private long noScreenIterationCount;
	
	public static void init(JsCarInterpreter interpreter, Track track) {
		Car.jsInterpreter = interpreter;
		Car.track = track;
	}

	public Car(String name) throws Exception {
		this.name = name;
		location = track.getStart().getLocation();
		angleRadians = Math.PI/2; // up
		roadPoint = track.getRoadList().get(roadIndex);
		curvePoint = track.getCurveList().get(curveIndex);

		try {
			jsCarIndex = jsInterpreter.createCarJsVariable(name);
		} catch (Exception e) {
			bugged = true;
		}
		updateCurveDistanceSide();
	}

	public void play() {
		if (bugged) return;
		
		calculateJsParameters();
		
		boolean ok = playJs();
		if (!ok) return;
		
		updateStats();
		moveCar();
	}

	private void calculateJsParameters() {
		roadIndex = track.calculateClosestRoadPoint(roadIndex, location);
		roadPoint = track.getRoadList().get(roadIndex);
		
		if (roadIndex < circuitIndex) circuitFinished++;
		circuitIndex = roadIndex;
		
		center = calculateDistanceFromCenter(location);
		calculateCurve();
	}

	private int calculateDistanceFromCenter(final Point location) {		
		final int nextIndex = ListCircular.next(track.getRoadList(), roadIndex);
		final FixedPoint nextPoint = track.getRoadList().get(nextIndex);
		
		final int sign = Geometry.getSideOfTheLine(roadPoint.x, roadPoint.y, 
				nextPoint.x, nextPoint.y, 
				location.x, location.y);
		
//		if (Game.DEBUG) {
//			System.out.println("distance from center: " + (sign * FixedPoint.calculateDistance(location, roadPoint)));
//		}

		final int newRoadIndex = track.calculateClosestRoadPoint(roadIndex, location);
		final FixedPoint newRoadPoint = track.getRoadList().get(newRoadIndex);
		
		return (int) (sign * FixedPoint.calculateDistance(location, newRoadPoint));
	}

	private void calculateCurve() {
		
		curveDistanceOld = curveDistance;
		curveDistance = (int)FixedPoint.calculateDistance(location, curvePoint);
		if (curveDistance <= 50 && curveDistance >= curveDistanceOld) { 
			curveIndex = ListCircular.next(track.getCurveList(), curveIndex);
			curvePoint = track.getCurveList().get(curveIndex);
			updateCurveDistanceSide();
		}
		
//		if (Game.DEBUG) {
//			System.out.println("Curve index/distance/side: " + 
//						curveIndex + " / " + curveDistance + " / " + curveSide);
//		}
	}
	
	private void updateCurveDistanceSide() {
		curveDistance = (int)FixedPoint.calculateDistance(location, curvePoint);

		final int nextIndex = ListCircular.next(track.getRoadList(), roadIndex);
		final FixedPoint nextPoint = track.getRoadList().get(nextIndex);
		final FixedPoint beforeCurvePoint = track.getRoadBeforeCurveList().get(curveIndex);
		
		curveSide = Geometry.getSideOfTheLine(nextPoint.x, nextPoint.y, 
				beforeCurvePoint.x, beforeCurvePoint.y, 
				curvePoint.x, curvePoint.y);
	}

	private boolean playJs() {
		
//		if (Game.DEBUG && name.equals("Presley")) {
//			System.out.println("rspeed="+speed+"|jspeed="+(int)(FACTOR_JS_SPEED * speed));
//		}
		
		JsParameters.INSTANCE.update(
				center, 
				(int)(FACTOR_JS_SPEED * speed), 
				curveDistance, 
				curveSide);
		try {
			jsInterpreter.playCar(jsCarIndex, JsParameters.INSTANCE);
			pedal = jsInterpreter.getCarPedal(jsCarIndex);
			wheel = jsInterpreter.getCarWheel(jsCarIndex);
			
//			if (Game.DEBUG) {
//				System.out.println("car["+name+"] pedal="+pedal+" wheel="+wheel);
//			}
			
		} catch (Exception e) {
			System.err.println("\nErro na programação do BOT!\nPode ser erro de sintaxe ou loop infinito.");
			bugged = true;
			return false;
		}
		return true;
	}
	
	private void updateStats() {
		if (pedal == PEDAL_ACCELERATE) {
			speed += SPEED_ACCELERATE;
		} else if (pedal == PEDAL_BRAKE) {
			speed -= SPEED_BRAKE;
		}
		if (speed > MAX_REAL_SPEED) {
			speed = MAX_REAL_SPEED;
		} else if (speed < 0) {
			speed = 0;
		}
		if (wheel == WHEEL_LEFT) {
			angleRadians -= WHEEL_TURN;
		} else if (wheel == WHEEL_RIGHT) {
			angleRadians += WHEEL_TURN;
		}
		if (angleRadians > 359) {
			angleRadians = 0;
		} else if (angleRadians < 0) {
			angleRadians = 359;
		}
	}

	private void moveCar() {
		final int newX = (int) (location.x + Math.cos(angleRadians) * speed);
		final int newY = (int) (location.y + Math.sin(angleRadians) * speed);
		if (!isFarFromCenter(newX, newY)) {
			location.x = newX;
			location.y = newY;
		} else {
			speed = 0;
		}
		
//		if (Game.DEBUG) {
//			System.out.println(name + "| vel:" + speed + "| angle: " + angleRadians);
//		}
	}
	
	private boolean isFarFromCenter(final int x, final int y) {
		return Math.abs(calculateDistanceFromCenter(new Point(x, y))) > MAX_CENTER_DISTANCE;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public double getAngleRadians() {
		return angleRadians;
	}
	
	public double getAngleDegrees() {
		return Math.toDegrees(angleRadians);
	}
	
//	public int getDistanceRunned() {
//		return circuitIndex + circuitFinished * track.getRoadList().size();
//	}
	
	public int getLaps() {
		return circuitFinished;
	}
	
	public long getNoScreenIterationCount() {
		return noScreenIterationCount;
	}
	
	public void setNoScreenIterationCount(long noScreenIterationCount) {
		this.noScreenIterationCount = noScreenIterationCount;
	}
	
	public boolean isBugged() {
		return bugged;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Car other = (Car) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
		
	
}

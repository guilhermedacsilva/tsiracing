package br.utfpr.gp.tsi.racing.car;

import org.json.JSONObject;

public class JsParameters {
	private static final JsParameters SINGLETON = new JsParameters();
	private int centerDistance;
	private int speed;
	private int curveDistance;
	private int curveSide;
	
	private JsParameters() {}

	public static JsParameters reuseSingleton(
			int centerDistance,
			int speed,
			int curveDistance,
			int curveSide) {
		
		SINGLETON.centerDistance = centerDistance;
		SINGLETON.speed = speed;
		SINGLETON.curveDistance = curveDistance;
		SINGLETON.curveSide = curveSide;
		return SINGLETON;
	}

	public String generateJson() {
		return new JSONObject(this).toString();
	}

	public int getCenterDistance() {
		return centerDistance;
	}

	public void setCenterDistance(int center) {
		this.centerDistance = center;
	}

	public int getCurveDistance() {
		return curveDistance;
	}
	
	public void setCurveDistance(int curveDistance) {
		this.curveDistance = curveDistance;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public String getCurveSide() {
		return curveSide == -1 ? JsEngine.ATRRIBUTE_WHEEL_LEFT : JsEngine.ATRRIBUTE_WHEEL_RIGHT;
	}
	
	public void setCurveSide(String curveSide) {}
	
}

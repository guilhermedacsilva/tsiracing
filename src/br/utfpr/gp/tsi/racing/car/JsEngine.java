package br.utfpr.gp.tsi.racing.car;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import br.utfpr.gp.tsi.racing.Debug;
import br.utfpr.gp.tsi.racing.util.ReaderUtil;

public class JsEngine {
	public static final String ATRRIBUTE_PEDAL = "pedal";
	public static final String ATRRIBUTE_PEDAL_ACCELERATE = "accelerate";
	public static final String ATRRIBUTE_PEDAL_BRAKE = "brake";
	public static final String ATRRIBUTE_WHEEL = "wheel";
	public static final String ATRRIBUTE_WHEEL_LEFT = "left";
	public static final String ATRRIBUTE_WHEEL_RIGHT = "right";
	private static final String FORMAT_CAR_FILE_PATH = "cars/%s.js";
	private static final String FORMAT_JS_CREATE_CAR_VARIABLE = "car%d = %s;";
	private static final String FORMAT_JS_CALL_PLAY = "car%d.play(%s)";
	private static final String FORMAT_JS_GET_ATTRIBUTE = "car%d.%s";
	private static final String ENGINE_NAME = "nashorn";
	
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
	private static int lastCarIndex = 0;
	
	private JsEngine() {}
	
	/**
	 * @return carIndex 
	 */
	public static int createCarJsVariable(String filename) throws ScriptException {
		String carJs = null;
		try {
			carJs = ReaderUtil.readFile(String.format(FORMAT_CAR_FILE_PATH, filename));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		lastCarIndex++;
		engine.eval(String.format(FORMAT_JS_CREATE_CAR_VARIABLE, lastCarIndex, carJs));
		return lastCarIndex;
		
	}
	
	public static void playCar(final int carIndex, final JsParameters parameters) throws ScriptException {
		final String jsCodeCallPlay = String.format(FORMAT_JS_CALL_PLAY, carIndex, parameters.generateJson());
		engine.eval(jsCodeCallPlay);
		
		Debug.print(parameters.generateJson());
	}
	
	public static int getCarPedal(final int carIndex) throws Exception {
		final String jsPedalValue = getCarAttribute(carIndex, ATRRIBUTE_PEDAL);
		
		if (ATRRIBUTE_PEDAL_ACCELERATE.equals(jsPedalValue)) {
			return Car.PEDAL_ACCELERATE;
		
		} else if (ATRRIBUTE_PEDAL_BRAKE.equals(jsPedalValue)) {
			return Car.PEDAL_BRAKE;
		}
		return Car.PEDAL_NOTHING;
	}
	
	public static int getCarWheel(final int carIndex) throws Exception {
		final String jsWheelValue = getCarAttribute(carIndex, ATRRIBUTE_WHEEL);
		
		if (ATRRIBUTE_WHEEL_LEFT.equals(jsWheelValue)) {
			return Car.WHEEL_LEFT;
		
		} else if (ATRRIBUTE_WHEEL_RIGHT.equals(jsWheelValue)) {
			return Car.WHEEL_RIGHT;
		}
		return Car.WHEEL_NOTHING;
	}

	private static String getCarAttribute(final int carIndex, final String atrributeName) throws Exception {
		final String jsCodeGetAttribute = String.format(FORMAT_JS_GET_ATTRIBUTE, carIndex, atrributeName);
		return String.valueOf(engine.eval(jsCodeGetAttribute));
	}

}

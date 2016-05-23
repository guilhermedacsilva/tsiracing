package br.utfpr.gp.tsi.racing.car;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import br.utfpr.gp.tsi.racing.util.ReaderUtil;

public class JsCarInterpreter {
	private static final String ATRRIBUTE_PEDAL = "pedal";
	private static final String ATRRIBUTE_PEDAL_ACCELERATE = "acelerar";
	private static final String ATRRIBUTE_PEDAL_BRAKE = "frear";
	private static final String ATRRIBUTE_WHEEL = "volante";
	private static final String ATRRIBUTE_WHEEL_LEFT = "esquerda";
	private static final String ATRRIBUTE_WHEEL_RIGHT = "direita";
	private static SafeInterpreter safeInterpreter;
	private static ScriptEngine engine;
	private int carIndex;
	
	public JsCarInterpreter() {
		final ScriptEngineManager engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("nashorn");
		carIndex = 0;
	}
	
	/**
	 * @return carIndex
	 */
	public int createCarJsVariable(String filename) throws Exception {
		carIndex++;
		final String carJsObject = ReaderUtil.read("cars/"+filename+".js");
		engine.eval("car"+carIndex+" = " + carJsObject + ";");
		return carIndex;
	}
	
	public void playCar(final int carIndex, final JsParameters parameters) throws Exception {
		final String command = "car"+carIndex+".jogar("+parameters.generateJson()+")";
//		if (safeInterpreter == null) {
			engine.eval(command);
//		} else {
//			safeInterpreter.playCar(command);
//		}		
//		if(Game.DEBUG) System.out.println(parameters.generateJson());
	}
	
	public int getCarPedal(final int carIndex) throws Exception {
		final String jsPedal = getCarAttribute(carIndex, ATRRIBUTE_PEDAL);
		if (ATRRIBUTE_PEDAL_ACCELERATE.equals(jsPedal)) {
			return Car.PEDAL_ACCELERATE;
		} else if (ATRRIBUTE_PEDAL_BRAKE.equals(jsPedal)) {
			return Car.PEDAL_BRAKE;
		}
		return Car.PEDAL_NOTHING;
	}
	
	public int getCarWheel(final int carIndex) throws Exception {
		final String jsWheel = getCarAttribute(carIndex, ATRRIBUTE_WHEEL);
		if (ATRRIBUTE_WHEEL_LEFT.equals(jsWheel)) {
			return Car.WHEEL_LEFT;
		} else if (ATRRIBUTE_WHEEL_RIGHT.equals(jsWheel)) {
			return Car.WHEEL_RIGHT;
		}
		return Car.WHEEL_NOTHING;
	}

	private String getCarAttribute(final int carIndex, final String atrribute) throws Exception {
		return (String)engine.eval("car"+carIndex+"."+atrribute);
	}

	
	public static void setSafeMode() {
		safeInterpreter = new SafeInterpreter(engine);
	}

}

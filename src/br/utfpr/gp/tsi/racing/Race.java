package br.utfpr.gp.tsi.racing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.Display;

import br.utfpr.gp.tsi.racing.car.Car;
import br.utfpr.gp.tsi.racing.car.JsCarInterpreter;
import br.utfpr.gp.tsi.racing.screen.IScreen;
import br.utfpr.gp.tsi.racing.util.GameFPS;

public class Race {
	public static final int LAPS = 3;
	private static final long MAX_TIME = 600 * 1000; // 10 min
	private IScreen screen;
	private List<Car> carList;
	private List<String> carWinList;
	// no screen
	private int carsRunning;

	public void init(IScreen screen, List<Car> carList) {
		this.screen = screen;
		this.carList = carList;
	}

	public void run(int speed) {
		final long start = System.currentTimeMillis();
		carWinList = new ArrayList<String>(carList.size());
		
		if (screen == null) {
			runNoScreen(start);
		} else {
			runScreen(start, speed);
		}
	}
	
	private void runScreen(long start, int speed) {
		final GameFPS fps = new GameFPS(30 * speed);
		Car car;
		Iterator<Car> iterator;
		
		outerLoop:
		while (System.currentTimeMillis()-start < MAX_TIME
				&& !Display.isCloseRequested()) {

			for (iterator = carList.iterator(); iterator.hasNext();) {
				car = iterator.next();
				car.play();
				if (car.getLaps() == LAPS) {
					carWinList.add(car.getName());
					iterator.remove();
					if (carList.isEmpty()) break outerLoop;
				
				} else if (car.isBugged()) {
					iterator.remove();
					if (carList.isEmpty()) break outerLoop;
				}
			}
			
			screen.update();
			fps.sleep();
		}
	}
	
	void decrementCarsRunning() {
		carsRunning--;
	}
	
	boolean isCarRunning() {
		return carsRunning > 0;
	}
	
//	private void runNoScreenThreads(long start) {
//		final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
//		System.out.println("MAX_THREADS: " + MAX_THREADS);
//		carsRunning = 0;
//		int currentCarIndex = 0;
//		
//		while (currentCarIndex < carList.size()) {
//			if (carsRunning < MAX_THREADS) {
//				new RaceThread(this, carList.get(currentCarIndex)).start();
//				currentCarIndex++;
//				carsRunning++;
//			} else {
//				try {
//					Thread.sleep(5000);
//				} catch (Exception e) {}
//			}
//		}
//		
//		while (isCarRunning()) {
//			try {
//				Thread.sleep(5000);
//			} catch (Exception e) {}
//		}
//		
//		carList.sort(CarClassification.INSTANCE);
//		for (Car car : carList) {
//			carWinList.add(car.getName());
//		}
//	}
	
	private void runNoScreen(long start) {
		JsCarInterpreter.setSafeMode();
		Car car;
		Iterator<Car> iterator;
		int i = 0;
		
		outerLoop:
		while (System.currentTimeMillis()-start < MAX_TIME) {
			i++;
			for (iterator = carList.iterator(); iterator.hasNext();) {
				car = iterator.next();
				car.play();
				if (car.getLaps() == LAPS) {
					carWinList.add(car.getName());
					iterator.remove();
					if (carList.isEmpty()) break outerLoop;
				
				} else if (car.isBugged()) {
					iterator.remove();
					if (carList.isEmpty()) break outerLoop;
				}
			}
		}
//		System.out.print("LOOP PERFORMANCE: "+i+" ... ");
//		System.out.println((System.currentTimeMillis()-start)/1000);
	}
	
	public List<String> getCarWinList() {
		return carWinList;
	}

}

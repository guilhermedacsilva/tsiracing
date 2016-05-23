package br.utfpr.gp.tsi.racing;

import br.utfpr.gp.tsi.racing.car.Car;

public class RaceThread extends Thread {
	private static final long MAX_TIME = 60 * 1000; // 1 min
	private Race race;
	private Car car;
	
	public RaceThread(Race race, Car car) {
		this.race = race;
		this.car = car;
	}
	
	@Override
	public void run() {
		final long start = System.currentTimeMillis();
		long i = 0;
		car.setNoScreenIterationCount(0);
		
		while (System.currentTimeMillis()-start < MAX_TIME) {
			car.play();
			i++;
			if (car.getLaps() == Race.LAPS) {
				car.setNoScreenIterationCount(i);
				break;
			}
		}
		
		race.decrementCarsRunning();
	}
	
}

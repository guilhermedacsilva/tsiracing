package br.utfpr.gp.tsi.racing.car;

import java.util.Comparator;

public class CarClassification implements Comparator<Car> {
	public static final CarClassification INSTANCE = new CarClassification();

	@Override
	public int compare(Car o1, Car o2) {
		return (int)Math.signum(o1.getNoScreenIterationCount() - o2.getNoScreenIterationCount());
	}
	
}

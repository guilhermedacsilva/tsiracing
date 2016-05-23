package br.utfpr.gp.tsi.racing.car;

import java.util.Comparator;

/**
 * Compares to know who is winning.
 */
public class CarComparator implements Comparator<Car> {
	public static final CarComparator INSTANCE = new CarComparator();

	@Override
	public int compare(Car o1, Car o2) {
		return (int)Math.signum(o1.getNoScreenIterationCount() - o2.getNoScreenIterationCount());
	}
	
}

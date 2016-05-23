package br.utfpr.gp.tsi.racing.car;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CarLoader {
	private static File[] FILES;

	public static List<Car> loadJs() throws Exception {
		final List<Car> carList = new ArrayList<Car>(100);
		final File carsFolder = new File("cars");
		if (!carsFolder.exists()) {
			System.err.println("A pasta cars não existe.");
			System.exit(1);
		}
		FILES = carsFolder.listFiles();
		String name;
		for (File file : FILES) {
			if (file.isFile() && isJavascript(file)) {
				name = getNameWithoutJs(file);
				carList.add(new Car(name));
			}
		}
		if (carList.isEmpty()) {
			System.err.println("Nenhum carro encontrado.");
			System.exit(1);
		}
		return carList;
	}

	private static boolean isJavascript(File file) {
		return file.getName().endsWith(".js");
	}
	
	private static String getNameWithoutJs(File file) {
		return file.getName().substring(0, file.getName().length()-3);
	}
	
	public static boolean isTextureExists(String texture) {
		texture += ".jpg";
		for (File file : FILES) {
			if (file.isFile() && texture.equals(file.getName())) {
				return true;
			}
		}
		return false;
	}

}

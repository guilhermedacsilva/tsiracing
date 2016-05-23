package br.utfpr.gp.tsi.racing.car;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CarJsFileLoader {
	private static File[] FILES;

	public static List<Car> loadJs() {
		final List<Car> carList = new ArrayList<Car>(100);
		final File carsFolder = new File("cars");
		if (!carsFolder.exists()) {
			System.err.println("The \"cars\" folder does not exist.");
			System.exit(1);
		}
		FILES = carsFolder.listFiles();
		String name;
		for (File file : FILES) {
			if (isJavascriptFile(file)) {
				name = getNameWithoutJs(file);
				carList.add(new Car(name));
			}
		}
		if (carList.isEmpty()) {
			System.err.println("No car found.");
			System.exit(1);
		}
		return carList;
	}

	private static boolean isJavascriptFile(File file) {
		return file.isFile() && file.getName().endsWith(".js");
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

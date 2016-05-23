package br.utfpr.gp.tsi.racing;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.threed.jpct.Config;

import br.utfpr.gp.tsi.racing.car.Car;
import br.utfpr.gp.tsi.racing.car.CarLoader;
import br.utfpr.gp.tsi.racing.car.JsCarInterpreter;
import br.utfpr.gp.tsi.racing.screen.IScreen;
import br.utfpr.gp.tsi.racing.screen.jpct.Screen3D;
import br.utfpr.gp.tsi.racing.track.Track;

public class Game {
	public static final boolean DEBUG = false;
	private static final String OPTION_NO_SCREEN = "semtela";
	private List<Car> carList;
	private IScreen screen;
	private boolean optionNoScreen = false;
	private String optionTrack = "1";
	private int optionSpeed = 1;
	private final Race race = new Race();
	
	public Game(String[] args) throws Exception {
		for (String option : args) {
			if (OPTION_NO_SCREEN.equals(option)) {
				optionNoScreen = true;
			} else if (option.startsWith("pista")) {
				optionTrack = String.valueOf(option.charAt(5));
				if (!optionTrack.equals("1")
						&& !optionTrack.equals("2")) {
					System.exit(1);
				}
			} else if (option.startsWith("velocidade")) {
				optionSpeed = Integer.parseInt(String.valueOf(option.charAt(10)));
				if (optionSpeed < 1 || optionSpeed > 4) optionSpeed = 1;
			}
		}
	}
	
	void run() throws Exception {
		load();
		startRace();
		saveResults();
	}

	private void load() throws Exception {
		Track track = new Track(optionTrack);	
		Car.init(new JsCarInterpreter(), track);
		carList = CarLoader.loadJs();
		
		if (optionNoScreen) return;

		Config.maxPolysVisible = 100000;
		screen = new Screen3D();
		screen.setCarList(carList);
		screen.setTrack(track);
	}

	private void startRace() {
		race.init(screen, carList);
		race.run(optionSpeed);
	}

	private void saveResults() throws IOException {
		final File file = new File("resultado.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		final PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.write(String.join("\n", race.getCarWinList()));
		writer.flush();
		writer.close();
	}
	
}

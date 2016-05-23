package br.utfpr.gp.tsi.racing;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import br.utfpr.gp.tsi.racing.car.Car;
import br.utfpr.gp.tsi.racing.car.CarJsFileLoader;
import br.utfpr.gp.tsi.racing.screen.IScreen;
import br.utfpr.gp.tsi.racing.screen.jpct.Screen3D;
import br.utfpr.gp.tsi.racing.track.Track;

public class Game {
	
	private GameOptions options;
	private List<Car> carList;
	private IScreen screen;
	private Race race;
	
	public Game(String[] args) {
		options = new GameOptions(args);
	}
	
	void run() throws IOException {
		loadResources();
		startRace();
		saveResults();
	}

	private void loadResources() throws IOException {
		Track track = new Track(options.getTrackNumber());	
		Car.init(track);
		carList = CarJsFileLoader.loadJs();
		
		if (options.shouldShowScreen()) {
			screen = new Screen3D();
			screen.setCarList(carList);
			screen.setTrack(track);
		}
	}

	private void startRace() {
		race = new Race(screen, carList);
		race.run(options.getSpeed());
	}

	private void saveResults() throws IOException {
		final File file = new File("result.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		final PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.write(String.join("\n", race.getCarWinList()));
		writer.flush();
		writer.close();
	}
	
}

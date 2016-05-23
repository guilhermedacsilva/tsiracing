package br.utfpr.gp.tsi.racing;

public class GameOptions {
	private static final String OPTION_NO_SCREEN = "noscreen";
	private static final String OPTION_TRACK = "track";
	private static final String OPTION_SPEED = "speed";

	private boolean noScreen = false;
	private int trackNumber = 1;
	private int speed = 1;

	public GameOptions(String[] args) {
		for (String option : args) {
			processOption(option);
		}
	}
	
	private void processOption(String option) {
		if (OPTION_NO_SCREEN.equals(option)) {
			noScreen = true;
			
		} else if (option.startsWith(OPTION_TRACK) && option.length() == 6) {
			trackNumber = Integer.parseInt(String.valueOf(option.charAt(5)));
			if (trackNumber < 1 || trackNumber > 2) {
				trackNumber = 1;
			}
			
		} else if (option.startsWith(OPTION_SPEED) && option.length() == 6) {
			speed = Integer.parseInt(String.valueOf(option.charAt(5)));
			if (speed < 1 || speed > 4) {
				speed = 1;
			}
		}
	}
	
	public boolean shouldShowScreen() {
		return !noScreen;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getTrackNumber() {
		return trackNumber;
	}
	
}

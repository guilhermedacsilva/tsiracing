package br.utfpr.gp.tsi.racing.util;

public class GameFPS {
	private long oldTime;
	private long elapsedTime;
	private long frameTime;
	
	public GameFPS(int fps) {
		frameTime = 1000/fps;
		oldTime = System.currentTimeMillis();
	}
	
	public void sleep() {
		elapsedTime = System.currentTimeMillis() - oldTime;
		if (elapsedTime < frameTime) {
			try {
				Thread.sleep(frameTime - elapsedTime);
			} catch (Exception e) {}
		}
		oldTime = System.currentTimeMillis();
	}
	
}

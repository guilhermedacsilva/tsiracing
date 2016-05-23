package br.utfpr.gp.tsi.racing.car;

import javax.script.ScriptEngine;

public class SafeInterpreter extends Thread {
	public final ScriptEngine engine;
	
	public SafeInterpreter(final ScriptEngine engine) {
		this.engine = engine;
	}

	public void playCar(final String cmd) {
		final EvalThread thread = new EvalThread(engine, cmd);
		thread.start();
		
		final long START_TIME = System.currentTimeMillis();
		
		int i = 1;
		while (!thread.isFinished()
				&& System.currentTimeMillis() - START_TIME < 10000) {
			try {
				Thread.sleep(i);
			} catch (Exception e) {}
			i += 50;
		}
		
		if (!thread.isFinished()) {
			thread.stop();
			throw new RuntimeException("Demorou mais de 10 segundos para rodar o método do carro!");
		}
	}

	
	
	public static class EvalThread extends Thread {
		private final ScriptEngine engine;
		private final String command;
		private boolean finished;
		
		public EvalThread(final ScriptEngine engine, final String command) {
			this.engine = engine;
			this.command = command;
			finished = false;
		}
		
		public void run() {
			finished = false;
			try {
				engine.eval(command);
			} catch (Exception e) {}
			finished = true;
		}
		
		public boolean isFinished() {
			return finished;
		}
		
	}
	
}

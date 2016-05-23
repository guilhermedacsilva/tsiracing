package br.utfpr.gp.tsi.racing;

import java.io.File;
import java.lang.reflect.Field;

import br.utfpr.gp.tsi.racing.util.NoOutput;

public class Main {

	public static void main(String[] args) throws Exception {
		fixPathForLoadNativeLibrary();
		
		if (!Game.DEBUG) disablePrintToStdout();
		
		new Game(args).run();
	}
	
	private static void fixPathForLoadNativeLibrary() throws Exception {
		String pathKey = "java.library.path";
		String pathValue = "lib" + File.pathSeparator + System.getProperty(pathKey);
		System.setProperty(pathKey, pathValue);
		Field sysPaths = ClassLoader.class.getDeclaredField("sys_paths");
		sysPaths.setAccessible(true);
		sysPaths.set(null, null);
	}
	
	private static void disablePrintToStdout() throws Exception {
		System.setOut(new NoOutput());
	}

	
	
}

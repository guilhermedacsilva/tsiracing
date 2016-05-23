package br.utfpr.gp.tsi.racing;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import br.utfpr.gp.tsi.racing.util.NoOutput;

public class Main {

	public static void main(String[] args) throws IOException {
		fixPathForLoadNativeLibrary();
		
		if (!Debug.ON) disablePrintToStdout();
		
		new Game(args).run();
	}
	
	/**
	 * Adds the folders: lib, lib/windows, lib/linux, lib/macosx
	 */
	private static void fixPathForLoadNativeLibrary() {
		String pathKey = "java.library.path";
		String pathValue = "lib" + File.pathSeparator + 
				"lib/windows" + File.pathSeparator + 
				"lib/linux" + File.pathSeparator + 
				"lib/macosx" + File.pathSeparator + 
				System.getProperty(pathKey);
		System.setProperty(pathKey, pathValue);
		try {
			Field sysPaths = ClassLoader.class.getDeclaredField("sys_paths");
			sysPaths.setAccessible(true);
			sysPaths.set(null, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void disablePrintToStdout() {
		System.setOut(new NoOutput());
	}
	
}

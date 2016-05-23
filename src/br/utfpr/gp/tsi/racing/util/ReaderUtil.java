package br.utfpr.gp.tsi.racing.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public final class ReaderUtil {
	
	public static String readAll(Reader reader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder builder = new StringBuilder();
		String line;
		while ( (line = bufferedReader.readLine() ) != null) {
			builder.append(line).append("\n");
		}
		bufferedReader.close();
		return builder.toString();
	}
	
	public static String readFile(String filename) throws IOException {
		return readAll(new FileReader(filename));
	}
	
}

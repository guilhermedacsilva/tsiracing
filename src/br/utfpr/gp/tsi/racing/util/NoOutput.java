package br.utfpr.gp.tsi.racing.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Discarts the output
 */
public class NoOutput extends PrintStream {

	public NoOutput() throws FileNotFoundException {
		super(new OutputStream() {
			@Override
			public void write(int b) throws IOException {}
		});
	}
	
}

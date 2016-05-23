package br.utfpr.gp.tsi.racing;

public class Debug {
	public static final boolean ON = false;
	
	public static void print(String msg) {
		if (ON) {
			System.out.println(msg);
		}
	}
	
}

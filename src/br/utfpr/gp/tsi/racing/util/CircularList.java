package br.utfpr.gp.tsi.racing.util;

import java.util.List;

public class CircularList {
	
	public static int next(List<?> list, int index) {
		if (index < list.size()-1) {
			return index+1;
		}
		return 0;		
	}
	
	public static int previous(List<?> list, int index) {
		if (index <= 0) {
			return list.size()-1;
		}
		return index-1;
	}
}

package de.deepchess.util;

public class MathUtil {
	
	public static double sigmoid(double x) {
		return (1/( 1 + Math.pow(Math.E,(-1*(x*16-8)))));
	}
	
}

package de.deepchess.util;

public class MathUtil {
	
	public static double sigmoid(double x) {
		return (1/( 1 + Math.pow(Math.E,(-1*(x*16-8)))));
	}
	
	public static double lerp(double a, double b, double speed) {
		a=a + (b-a)*speed;
		return a;
	}
	
}

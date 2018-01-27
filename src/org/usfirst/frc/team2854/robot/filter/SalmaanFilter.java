package org.usfirst.frc.team2854.robot.filter;

public class SalmaanFilter extends KalmanFilterSimple{
	double a, b, c, d;
	SlimMatrix transitionMatrix = new SlimMatrix(1, 4);
	public SalmaanFilter(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	//Kalman filter for 1 value at first, use weighted average to use multiple
	double z = a;


}

package org.usfirst.frc.team2854.map.math;

import java.awt.geom.Point2D;

public class RobotPosition extends Vector {
	private double theta;

	public RobotPosition(double x, double y) {
		super(x, y);
	}

	public RobotPosition() {
		super(0, 0);
	}

	public double getTheta() {
		return theta;

	}

	public void setLocation(double x, double y) {
		super.setX(x);
		super.setY(y);
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	public void rotate(double theta) {
		this.theta += theta;
	}
}

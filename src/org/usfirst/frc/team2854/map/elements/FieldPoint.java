package org.usfirst.frc.team2854.map.elements;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.math.RobotPosition;
import org.usfirst.frc.team2854.map.math.Vector;

public class FieldPoint extends Point2D implements MapDrawable, FieldShape {
	private double x, y;

	public FieldPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Mat m, Vector translation, Color c) {
		byte[] colorByte = new byte[] {(byte) c.getBlue(), (byte) c.getGreen(), (byte) c.getRed()};
		int x = (int)translation.getX();
		int y = (int)translation.getY();
		
		int size = 2;
		for(int i = -size; i < size; i++) {
			for(int j = -size; j < size; j++) {
				m.put(y+i, x+j, colorByte);
			}
		}
	}

	@Override
	public boolean isWithinBounds(RobotPosition rp) {
		return false; //cant be within a point?
	}

	

}

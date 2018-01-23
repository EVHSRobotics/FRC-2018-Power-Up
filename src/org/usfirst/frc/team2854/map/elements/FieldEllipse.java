package org.usfirst.frc.team2854.map.elements;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.math.RobotPosition;
import org.usfirst.frc.team2854.map.math.Vector;

public class FieldEllipse extends Ellipse2D implements FieldShape, MapDrawable {// TODO Do this later,
	private Vector major, minor;
	private double x0, y0;

	public FieldEllipse(Vector major, Vector minor, double x0, double y0) {
		if (Vector.dot(major, minor) == 0) {
			this.major = major;
			this.minor = minor;
			this.x0 = x0;
			this.y0 = y0;
		} else {
			System.out.println("no");
		}
	}

	public FieldEllipse(double major, double minor, double theta) {
		// TODO make this later	
	}

	@Override
	public boolean isWithinBounds(RobotPosition rp) {
		Vector u = new Vector(rp.getX() - x0, rp.getY() - y0);
		double quantity = Math.pow(Vector.dot(u, major) / Math.pow(major.length(), 2), 2)
				+ Math.pow(Vector.dot(u, minor) / Math.pow(minor.length(), 2), 2);
		return (quantity <= 1);
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFrame(double arg0, double arg2, double arg4, double arg6) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Mat m, Vector translation, Color c) {
		double step = .1;
		byte[] colorByte = new byte[] {(byte) c.getBlue(), (byte) c.getGreen(), (byte) c.getRed()};
		for(double theta = 0; theta < 2*Math.PI; theta += step) {
			int x = (int)(major.length() * Math.cos(theta) + translation.getX());
			int y = (int)(minor.length() * Math.sin(theta) + translation.getY()); //TODO kinda yoloed, should double check
			m.put(y, x, colorByte);
		}
		
	}


}

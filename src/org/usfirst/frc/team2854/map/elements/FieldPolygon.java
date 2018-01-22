package org.usfirst.frc.team2854.map.elements;

import java.awt.Color;

import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.math.RobotPosition;
import org.usfirst.frc.team2854.map.math.Vector;
import org.usfirst.frc.team2854.map.elements.FieldShape;

public class FieldPolygon implements FieldShape {
	public FieldPolygon(double[] x, double[] y, int points) {
		super();
	}

	@Override
	public boolean isWithinBounds(RobotPosition rp) {
		// TODO It doesn't seem that the geom package has a Polygon class
		return true;
	}

	@Override
	public void draw(Mat m, Vector translation, Color c) {
		// TODO Auto-generated method stub
		
	}



}

package org.usfirst.frc.team2854.map.elements;

import org.usfirst.frc.team2854.map.math.Vector;

public class FieldCircle extends FieldEllipse {

	public FieldCircle(double radius, double x0, double y0) {
		super(new Vector(radius, 0), new Vector(0, radius), x0, y0);
	}

}

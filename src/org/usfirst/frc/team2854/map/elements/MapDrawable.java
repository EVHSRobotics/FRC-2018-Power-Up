package org.usfirst.frc.team2854.map.elements;

import java.awt.Color;

import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.math.Vector;

public interface MapDrawable {

	public void draw(Mat m, Vector translation, Color c);
	
}

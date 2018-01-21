package org.usfirst.frc.team2854.PID;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * @author Kevin Palani
 * Class that represents a Dummy PID Output
 */
public class DummyPIDOutput implements PIDOutput {

	private double output;
	
	/**
	 * Default constructor
	 */
	public DummyPIDOutput() {
		output = 0;
	}
	
	/** 
	 * Sets PID output value to output.
	 */
	@Override
	public void pidWrite(double output) {
		//System.out.println("PId writing");
		this.output = output;
	}

	public double getOutput() {
		return output;
	}

	
	
}

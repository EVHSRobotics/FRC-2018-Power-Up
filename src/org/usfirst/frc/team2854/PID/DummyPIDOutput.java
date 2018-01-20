package org.usfirst.frc.team2854.PID;

import edu.wpi.first.wpilibj.PIDOutput;

public class DummyPIDOutput implements PIDOutput {

	private double output;
	
	public DummyPIDOutput() {
		output = 0;
	}
	
	@Override
	public void pidWrite(double output) {
		//System.out.println("PId writing");
		this.output = output;
	}

	public double getOutput() {
		return output;
	}

	
	
}

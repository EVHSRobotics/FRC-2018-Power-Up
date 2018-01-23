package org.usfirst.frc.team2854.map;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SensorBoard;


public class EncoderBased implements MapInput {

	private SensorBoard sensors;
	private boolean init = true;

	
	public EncoderBased() {
		sensors = Robot.getSensors();
	} 
	
	@Override
	public double getDeltaForward() {
		return sensors.getVelocity();
	}

	
	@Override
	public double getRotation() {
		return sensors.getNavX().getAngle();
	}

	@Override
	public void update() {
		if(init) {
			init();
			init = false;
		}
		
	
	}
	
	public void init() {

	}

}

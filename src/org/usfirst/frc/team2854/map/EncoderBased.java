package org.usfirst.frc.team2854.map;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SensorBoard;


public class EncoderBased implements MapInput {

	private SensorBoard sensors;
	private long startTime = 0, lastTime = 0;
	private double deltaTime = 0;
	private boolean init = true;
	private double velocity = 0;
	
	private int leftValue, rightValue;
	private int lastLeft, lastRight;
	
	public EncoderBased() {
		sensors = Robot.getSensors();
		
		//leftT = ((DriveTrain)(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN))).getLeftT1();
		//rightT = ((DriveTrain)(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN))).getRightT1();

	} 
	
	@Override
	public double getDeltaForward() {
		return 0;
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
		
	
		lastTime = startTime;
	}
	
	public void init() {

	}

}

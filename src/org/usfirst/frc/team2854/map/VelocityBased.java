package org.usfirst.frc.team2854.map;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SensorBoard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VelocityBased implements MapInput {

	private SensorBoard sensors;
	
	private long lastTime, currentTime, deltaTime;
	private boolean init = false;
	
	public VelocityBased() {
		sensors = Robot.getSensors();
		lastTime = System.nanoTime();
		currentTime = System.nanoTime();
		deltaTime = 0;
	}
	
	
	@Override
	public double getDeltaForward() {
		// TODO Auto-generated method stub
		return sensors.getNavX().getVelocityX() * (double)(deltaTime)/(1E9d);
	}

	@Override
	public double getRotation() {
		// TODO Auto-generated method stub
		return sensors.getNavX().getAngle();
	}

	@Override
	public void update() {
		if(!init) {
			init();
			init = true;
		}
		currentTime = System.nanoTime();
		deltaTime = (currentTime - lastTime);
		
		SmartDashboard.putNumber("NavX Vel X", sensors.getNavX().getVelocityX());
		SmartDashboard.putNumber("NavX Vel Y", sensors.getNavX().getVelocityY());
		SmartDashboard.putNumber("NavX Vel Z", sensors.getNavX().getVelocityZ());
		
		lastTime = currentTime;
	}


	private void init() {
		currentTime = System.nanoTime();
		lastTime = System.nanoTime();
	}
	
	

}

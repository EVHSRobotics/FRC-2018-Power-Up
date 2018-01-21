package org.usfirst.frc.team2854.robot.commands;

import java.util.function.DoubleFunction;
import java.util.function.Supplier;

import com.team2854.mapauto.Driveable;

import edu.wpi.first.wpilibj.command.Command;

/**
 *A generic drive forward command using encoders, which can use a default, parameterized, or user supplied function to determine speed
 */
public class DriveDistance extends Command {

	private Driveable drive;
	private double theshDistance;
	private Supplier<Double> encoders;
	private DoubleFunction<Double> speedOpp;
	private double startingDist;
	private double distance;
		
	
	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target angle in degrees relative to current angle, Ex. To turn by 90 degrees pass in 90
	 * @param SpeedOpp supplies the function for converting normalized distance difference (0-1) to the speed(0-1)
	 * @param threshHoldDistance the minimum distance from targe tto stop. Will not go past target
	 * @param angle Implement a function or lambda that passes in the gyro value to get the current angle.
	 * The easiest way to pass in the gyro is the use the lambda expression () -> new Double(gyro.getValue()), where gyro.getValue() is your way of getting the acumelated angle from the gyro
	 */
    public DriveDistance(Driveable drive, double targetDistance, DoubleFunction<Double> speedOpp, double threshHoldDistance, Supplier<Double> encoders) {
       	requires(drive);
    	this.distance = targetDistance;
    	this.drive = drive;
    	this.encoders = encoders;
    	this.speedOpp = speedOpp;
    	this.theshDistance = threshHoldDistance;
    }

	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target distance
	 * @param maxSpeed the maximum turn speed, will move at this speed if robot has traveled less than 75% of the distance
	 * @param minSpeed the minimum turn speed, will approach this speed as the distance 0
	 * @param threshHoldDistance the minimum distance from target to stop. Will not go past target
	 * @param angle Implement a function or lambda that passes in the gyro value to get the current angle.
	 * The easiest way to pass in the gyro is the use the lambda expression () -> new Double(gyro.getValue()), where gyro.getValue() is your way of getting the acumelated angle from the gyro
	 */
    public DriveDistance(Driveable drive, double target, double maxSpeed, double minSpeed, double threshHoldDistance, Supplier<Double> gyro) {
    	this(drive, target, (value) -> (Math.min(value, .25)) / .25d * (maxSpeed - minSpeed) + minSpeed, threshHoldDistance, gyro);
    }
    
	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment <br>
	 * The max speed is 100%, min speed is 0%, and threshold distance is 0 
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target distance to drive
	 * @param angle Implement a function or lambda that passes in the encoder value to get the current distance.
	 * The easiest way to pass in the encoder is the use the lambda expression () -> new Double(encoder.getValue()), where encoder.getValue() is your way of getting the distance from an encoder
	 */
    public DriveDistance(Driveable drive, double target, Supplier<Double> gyro) {
    	this(drive, target, 1, 0, 0, gyro);
    }

	// Called just before this Command runs the first time
	protected void initialize() {
		startingDist = encoders.get();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	double currentAngle = encoders.get();
    	double angleDiff = Math.abs(currentAngle - startingDist - distance);
    	double speed = speedOpp.apply(angleDiff);
    	drive.drive(speed, speed);
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return encoders.get() - startingDist - theshDistance >= distance;
	}

	// Called once after isFinished returns true	
	protected void end() {
		System.out.println("Ending");
		drive.drive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to runs
	protected void interrupted() {
		drive.drive(0, 0);
	}
}

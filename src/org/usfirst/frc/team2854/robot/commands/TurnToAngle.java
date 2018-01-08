package org.usfirst.frc.team2854.robot.commands;

import java.util.function.DoubleFunction;
import java.util.function.Supplier;

import com.team2854.mapauto.Driveable;

/**
 * A generic turn to angle using a gyro, which can use a default, parameterized, or user supplied function to determine speed
 */
public class TurnToAngle extends TurnCommand {

	private Driveable drive;
	private double threshAngle;
	private Supplier<Double> gyro;
	private DoubleFunction<Double> speedOpp;
	private double startingAngle;
		
	
	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target angle in degrees relative to current angle, Ex. To turn by 90 degrees pass in 90
	 * @param SpeedOpp supplies the function for converting the angle difference (0-180) to the speed(0-1)
	 * @param theshHoldAngle, the minimum angle difference to stop
	 * @param angle Implement a function or lambda that passes in the gyro value to get the current angle.
	 * The easiest way to pass in the gyro is the use the lambda expression () -> new Double(gyro.getValue()), where gyro.getValue() is your way of getting the acumelated angle from the gyro
	 */
    public TurnToAngle(Driveable drive, double target, DoubleFunction<Double> speedOpp, double threshHoldAngle, Supplier<Double> gyro) {
    	super(target);
    	requires(drive);
    	this.drive = drive;
    	this.gyro = gyro;
    	this.targetAngle = target;
    	this.speedOpp = speedOpp;
    	this.threshAngle = threshHoldAngle;
    }

	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target angle in degrees relative to current angle, Ex. To turn by 90 degrees pass in 90
	 * @param maxSpeed the maximum turn speed, will turn at this speed if the angle difference is greater than 90
	 * @param minSpeed the minimum turn speed, will approach this speed as the angle difference approaches 0
	 * @param theshHoldAngle, the minimum angle difference to stop
	 * @param angle Implement a function or lambda that passes in the gyro value to get the current angle.
	 * The easiest way to pass in the gyro is the use the lambda expression () -> new Double(gyro.getValue()), where gyro.getValue() is your way of getting the acumelated angle from the gyro
	 */
    public TurnToAngle(Driveable drive, double target, double maxSpeed, double minSpeed, double threshHoldAngle, Supplier<Double> gyro) {
    	this(drive, target, (value) -> (Math.min(value, 90)) / 90d * (maxSpeed - minSpeed) + minSpeed, threshHoldAngle, gyro);
    }
    
	/**
	 * 
	 * Make sure your subsystem that is used for driving extends the <code>Drivable</code> class <br>
	 * and implements the <code>drive</code> method<br>
	 * Only tank drive is supported at the moment <br>
	 * The max speed is 100%, min speed is 0%, and threshold angle is 5 degrees
	 * 
	 * @param drive The Subsystem indented for driving
	 * @param target The target angle in degrees relative to current angle, Ex. To turn by 90 degrees pass in 90
	 * @param angle Implement a function or lambda that passes in the gyro value to get the current angle.
	 * The easiest way to pass in the gyro is the use the lambda expression () -> new Double(gyro.getValue()), where gyro.getValue() is your way of getting the acumelated angle from the gyro
	 */
    public TurnToAngle(Driveable drive, double target, Supplier<Double> gyro) {
    	this(drive, target, 1, 0, 5, gyro);
    }

	// Called just before this Command runs the first time
	protected void initialize() {
		startingAngle = gyro.get();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	double currentAngle = gyro.get();
    	double angleDiff = Math.abs(currentAngle - startingAngle - targetAngle);
    	double speed = speedOpp.apply(angleDiff);
    	if(targetAngle > (currentAngle-startingAngle)) {
    		//turn right
    		drive.drive(speed, -speed);
    	} else {
    		//turn left1
    		drive.drive(-speed, speed);
    	}
    }

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(gyro.get() - startingAngle - targetAngle) < threshAngle;
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

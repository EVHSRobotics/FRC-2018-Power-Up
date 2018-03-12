package org.usfirst.frc.team2854.robot;

/**
 * This is a config file for global variables, such as saftey features and speed
 * constants PID contants are in the PIDConstant class
 */
public class Config {
	
	//0,500,3750,4500

	public static double manuelSpeedMultiplier = 1;
	public static double totalDriveSpeedMultiplier = 1;

	public static double driveEncoderCyclesPerRevolution = 256; // TODO use this value in speed calculations

	public static double fastTarget = 8250; // in units per 100 ms
	public static double slowTarget = 4000;

	public static double upTargetSpeed = 24000 *  1 / 2d;

	public static double downTargetSpeed = -24000 * 1 / 3d;

	public static double upperClawLimit = 4500;
	
	public static double lowElevator = 1500;
	public static double midElevator = 10000;
	public static double highElevator= 20000;
	

	public static double turnDistance;
	

	//public static double cyclesToInches = 96726.5d / 261.47d; a//FIXME wrong for comp robo b
	public static double cyclesToInches = 77219.5 / (15d*12d);

}

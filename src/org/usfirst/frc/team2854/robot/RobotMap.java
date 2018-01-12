package org.usfirst.frc.team2854.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//FIXME Must be remapped for new robot
	 
	 //0 leftMotor, inverted
	 //1 rightMotor not inverted
	 //2 rightMotor not inverted
	 //3  leftMotor, inverted
	
	public static int leftTalonID1 = 1;
	public static int leftTalonID2 = 2;
	public static int rightTalonID1 = 0;
	public static int rightTalonID2 = 3;
	
	public static int shifterUp = 3;     //     4 2 6
	public static int shifterDown = 4;   //     3 1 5
	
	
}

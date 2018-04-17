package org.usfirst.frc.team2854.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static int leftTalonID1 = 3;
	public static int leftTalonID2 = 4;
	public static int rightTalonID1 = 1;
	public static int rightTalonID2 = 2;
 
	public static int elevator = 5; 

	public static int leftIntake = 9; //inverted
	public static int rightIntake = 7; 

	//leftIntake 7, slaveClaw 9	
	
	public static int masterClaw = 8; //inverted
	public static int slaveClaw = 6; //right intake
	
	public static int intakeUp = 0;
	public static int intakeDown = 1;
	
	public static int shifterUp = 2; // 4 2 6
	public static int shifterDown = 3;// 3 1 5

	public static int led = 0;
	
	public static int climb1 = 10;
	public static int climb2 = 11;

	
	
}

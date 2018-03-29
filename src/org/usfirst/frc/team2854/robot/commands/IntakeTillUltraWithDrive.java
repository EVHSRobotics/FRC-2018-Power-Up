package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SensorBoard;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeTillUltraWithDrive extends Command {

	private SensorBoard board;
	private double trigger, speed;
	private Claw claw;
	private DriveTrain drive;
	private long startTime;
	private boolean shouldTimeOut;
	private double driveSpeed;
	
    public IntakeTillUltraWithDrive(double trigger, double speed, boolean shouldTimeout, double driveSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.getSubsystem(SubsystemNames.CLAW));
    	requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    	this.trigger = trigger;
    	this.speed = speed;
    	this.shouldTimeOut = shouldTimeout;
    } 
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	board = Robot.getSensors();
    	claw = (Claw)Robot.getSubsystem(SubsystemNames.CLAW);
    	drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
    	startTime = System.nanoTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.drive(speed, speed, ControlMode.Velocity);
    	claw.runIntake(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if((board.getUltraDistance() < trigger)) {
    		System.out.println("Finishing due to ultra trigger");
    	}
    	if((shouldTimeOut && (System.nanoTime() - startTime)/1E9d > 5)) {
    		System.out.println("Finishing due to time out");
    	}
        return (board.getUltraDistance() < trigger) || (shouldTimeOut && (System.nanoTime() - startTime)/1E9d > 5);
    }

    // Called once after isFinished returns true
    protected void end() {
    	claw.runIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	claw.runIntake(0);
    }
}

package org.usfirst.frc.team2854.robot.commands;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SensorBoard;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeTillUltra extends Command {

	private SensorBoard board;
	private double trigger, speed;
	private Claw claw;
	private long startTime;
	private boolean shouldTimeOut;
	
    public IntakeTillUltra(double trigger, double speed, boolean shouldTimeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.getSubsystem(SubsystemNames.CLAW));
    	this.trigger = trigger;
    	this.speed = speed;
    	this.shouldTimeOut = shouldTimeout;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	board = Robot.getSensors();
    	claw = (Claw)Robot.getSubsystem(SubsystemNames.CLAW);
    	startTime = System.nanoTime();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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

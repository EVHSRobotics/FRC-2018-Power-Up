package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.PID.ProfileNotifier;
import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MotionProfileTurn extends Command {

	private DriveTrain drive;
	private double outV, cruzV, turnR, turnAngle;
	private ProfileNotifier notifier;
	private boolean right;
	
    public MotionProfileTurn(double outV, double cruzV, double turnR, double turnAngle, boolean right) {
    	requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    	
    	System.out.println("Creating command obj");
    	this.outV = outV;
    	this.cruzV = cruzV;
    	this.turnR = turnR;
    	this.turnAngle = turnAngle;
    	this.right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("initing to command");
    	drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
    	notifier = drive.generateTurnProfile(cruzV, outV, turnR, right, turnAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!notifier.isEnabled() && notifier.shouldEnable()) {
    		notifier.startNotifier();
    		drive.drive(1, 1, ControlMode.MotionProfile);
    		notifier.setEnabled(true);
    		System.out.println("Starting");
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return notifier.isFinished(500);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drive.stop();
    }
}

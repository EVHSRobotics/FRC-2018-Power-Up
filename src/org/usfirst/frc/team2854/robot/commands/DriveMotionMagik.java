package org.usfirst.frc.team2854.robot.commands;

import java.util.ResourceBundle.Control;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveMotionMagik extends Command {

	private DriveTrain drive;
	private double revs;
	private double targetPos;
	
    public DriveMotionMagik(double revs) {
        requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
        this.revs = revs;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive = ((DriveTrain)Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    	//double distance = 5 * Config.lowTarget;
    	//drive.drive(distance, distance, ControlMode.MotionMagic);
    	System.out.println("Driving");
    	drive.drive(revs * Config.lowTarget, revs * Config.lowTarget, ControlMode.MotionMagic);
    	targetPos = (revs * Config.lowTarget) + drive.getAvgEncoder();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println((revs * Config.lowTarget) + " " + drive.getAvgEncoder());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drive.getAvgEncoder() - targetPos) < 50;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

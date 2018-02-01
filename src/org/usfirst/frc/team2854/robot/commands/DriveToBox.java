package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToBox extends Command {

	private DriveTrain drive;
	private Command c;
	
	private double distance;
	
	public DriveToBox(double distance) {
        requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
        this.distance = distance;
	}
	
    public DriveToBox() {
        this(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	c = new DriveMotionMagik(-Robot.getVision().getDistanceToBox() + distance);
    	c.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return c.isCompleted();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

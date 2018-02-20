package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class StopDrive extends TimedCommand {

	private DriveTrain drive;
	
    public StopDrive(double time) {
    	super(time);
        requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drive.stop();
    }


    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

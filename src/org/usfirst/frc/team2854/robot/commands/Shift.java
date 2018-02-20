package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain.GearState;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shift extends Command {

	private GearState state;
	
    public Shift(DriveTrain.GearState state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	((DriveTrain)Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN)).shiftSlow();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

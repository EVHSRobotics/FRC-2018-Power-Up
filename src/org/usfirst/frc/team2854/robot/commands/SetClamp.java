package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetClamp extends Command {

	private Claw claw;
	
	private boolean open;

	public SetClamp(boolean open) {
		requires(Robot.getSubsystem(SubsystemNames.CLAW));
		this.open = open;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);
		if(open) {
			claw.open();
		} else {
			claw.close();
		}
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

package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class Intake extends TimedCommand {

	private Claw claw;

	public Intake(double time) {
		super(time);
		requires(Robot.getSubsystem(SubsystemNames.CLAW));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);

	}

	// Called repeatedly when this Comma nd is scheduled to run
	protected void execute() {
		claw.runIntake(-1d);

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

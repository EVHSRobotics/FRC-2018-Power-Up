package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeAquire extends Command {
	
	private Claw claw;

	public IntakeAquire() {
		requires(Robot.getSubsystem(SubsystemNames.CLAW));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("Running intake");
		claw.close();
		claw.runIntake(-0.45);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return claw.isIntakeStalling();
	}

	// Called once after isFinished returns true
	protected void end() {
		claw.setIntakeSpeed(-.1);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Intake interrupted");
	}
}

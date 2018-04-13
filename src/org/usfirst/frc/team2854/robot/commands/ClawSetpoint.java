package org.usfirst.frc.team2854.robot.commands;

import java.time.chrono.ThaiBuddhistEra;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClawSetpoint extends Command {

	double target = 0;
	double error = 50;

	private Claw claw;

	public ClawSetpoint(double target) {
		requires(Robot.getSubsystem(SubsystemNames.CLAW));
		this.target = target;//((Claw) Robot.getSubsystem(SubsystemNames.CLAW)).getClawPos();
		setTimeout(3);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Initing claw command");
		// System.out.println("Current Pos: " + Robot.claw.getPos() + " target " +
		// target + " error: " + Math.abs(Robot.claw.getPos() - target) + " min
		// error: " + error);
		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);

		//System.out.println("targetPos " + target + " currentPos: "
		//		+ ((Claw) Robot.getSubsystem(SubsystemNames.CLAW)).getClawPos());
		//Thread.dumpStack();
//		claw.driveClaw(target * 4096, ControlMode.Position);]\
		//claw.driveClaw(target * 4096, ControlMode.Position);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//System.out.println("running");
		if(target > claw.getClawPos()) {
			//System.out.println("Driving claw up " + claw.getClawPos());
			claw.driveClaw(1 * Math.abs(target - claw.getClawPos()) / 2000, ControlMode.PercentOutput);
		} else {
			//System.out.println("Driving claw down " + claw.getClawPos());
			claw.driveClaw(-.35, ControlMode.PercentOutput);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(target - claw.getClawPos()) < 350;
	}

	// Called once after isFinished returns true
	protected void end() {
		// System.out.println("Current Pos: " + Robot.claw.getPos() + " target " +
		// target + " error: " + Math.abs(Robot.claw.getPos() - target) + " min
		// error: " + error);
		System.out.println("Ending");
		claw.driveClaw(0, ControlMode.PercentOutput);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Intrupting");
		claw.driveClaw(0, ControlMode.PercentOutput);
	}
}

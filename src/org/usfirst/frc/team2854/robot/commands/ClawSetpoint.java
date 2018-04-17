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
		this.target = target;// ((Claw) Robot.getSubsystem(SubsystemNames.CLAW)).getClawPos();
		setTimeout(3);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Initing claw command");

		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);

	}

	protected void execute() {
		// if(target > claw.getClawPos()) {
		// claw.driveClaw(1 * Math.abs(target - claw.getClawPos()) / 2000,
		// ControlMode.PercentOutput);
		// } else {
		// claw.driveClaw(-.35, ControlMode.PercentOutput);
		// }
		
		
		//was a less than sign
		if(target > claw.getClawPos()) {
			claw.driveClaw(-1 * Math.abs(target - claw.getClawPos()) / 3000, ControlMode.PercentOutput);
		} else {												// was 2000 COMP
			claw.driveClaw(.15, ControlMode.PercentOutput); //was .35 COMP
		}

	}

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

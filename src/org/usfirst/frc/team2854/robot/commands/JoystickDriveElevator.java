package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Elevator;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class JoystickDriveElevator extends Command {

	private Elevator elevator;

	public JoystickDriveElevator() {
		requires(Robot.getSubsystem(SubsystemNames.ELEVATOR));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		elevator = (Elevator) Robot.getSubsystem(SubsystemNames.ELEVATOR);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//double value = (OI.secondaryJoystick.getRawAxis(2) - OI.secondaryJoystick.getRawAxis(3));
		double value = -OI.secondaryJoystick.getRawAxis(1);
		value = Math.abs(value) < .05 ? 0 : value;
		if(value == 0) {
			Robot.compressor.start();
		} else {
			Robot.compressor.stop();
		}
		double holdValue = .40;
		double multiplier = .75;
		if(elevator.getPos() > -1500) {
			holdValue = 0;
		} else {
			multiplier = .9;
		}
		elevator.drive(-(value * multiplier + holdValue), ControlMode.PercentOutput);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

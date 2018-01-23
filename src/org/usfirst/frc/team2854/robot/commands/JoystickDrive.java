package org.usfirst.frc.team2854.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

/**
 * Drives the robot using joystick drive.
 */

public class JoystickDrive extends Command {

	private DriveTrain drive;

	/**
	 * Default constructor.
	 */
	public JoystickDrive() {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
	}

	/**
	 * Initializes the drivetrain.
	 */
	protected void initialize() {
		drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
	}

	/**
	 * Executes the driving.
	 */
	protected void execute() {
		double speed = OI.joystick.getRawAxis(2) - OI.joystick.getRawAxis(3);
		// SmartDashboard.putNumber("target val", speed * Config.manuelSpeedMultiplier);
		// speed *= Config.targetVel;
		double left = (Math.abs(OI.joystick.getRawAxis(1)) < .1 ? 0 : OI.joystick.getRawAxis(1));
		// double right = (Math.abs(OI.joystick.getRawAxis(5)) < .1 ? 0 :
		// OI.joystick.getRawAxis(5)) *
		// 8400;

		if (Math.abs(OI.joystick.getRawAxis(1)) > .5) {
			drive.drive(left * Config.manuelSpeedMultiplier, left * Config.manuelSpeedMultiplier,
					ControlMode.PercentOutput);
		} else {
			drive.drive(speed * Config.manuelSpeedMultiplier, speed * Config.manuelSpeedMultiplier,
					ControlMode.Velocity);
		}

		// drive.drive(speed, speed, ControlMode.PercentOutput);
	}

	/**
	 * Stops drive train.
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * Stops driving.
	 */
	protected void end() {
		drive.stop();
	}

	/**
	 * Stops drive when interrupted.
	 */
	protected void interrupted() {
		drive.stop();
	}
}

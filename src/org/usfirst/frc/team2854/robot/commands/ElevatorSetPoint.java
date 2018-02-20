package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Elevator;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorSetPoint extends Command {

	double target = 0;
	double error = 50;

	private Elevator elevator;

	public ElevatorSetPoint(double target) {
		requires(Robot.getSubsystem(SubsystemNames.ELEVATOR));
		this.target = target;// + ((Elevator) Robot.getSubsystem(SubsystemNames.ELEVATOR)).getPos();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Initing elevator command");
		// System.out.println("Current Pos: " + Robot.elevator.getPos() + " target " +
		// target + " error: " + Math.abs(Robot.elevator.getPos() - target) + " min
		// error: " + error);
		elevator = (Elevator) Robot.getSubsystem(SubsystemNames.ELEVATOR);

		if (elevator.getPos() > target) {
			System.out.println("Using up target speed");
			elevator.updateTargetSpeed(Config.upTargetSpeed);
		} else {
			System.out.println("Using down target speed");
			elevator.updateTargetSpeed(Config.downTargetSpeed);
		}

		elevator.drive(target, ControlMode.MotionMagic);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// System.out.println("running");
		// System.out.println("Current Pos: " + Robot.elevator.getPos() + " error: " + +
		// " min error: " + error);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// System.out.println("Running is done, value will be " +
		 return elevator.isDone();
		//return Math.abs(target - elevator.getPos()) < 350;
	}

	// Called once after isFinished returns true
	protected void end() {
		// System.out.println("Current Pos: " + Robot.elevator.getPos() + " target " +
		// target + " error: " + Math.abs(Robot.elevator.getPos() - target) + " min
		// error: " + error);
		for (int i = 0; i < 10; i++) {
			System.out.println("Ending setpoint " + i);
		}
		// Robot.elevator.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Intrupting");
		// Robot.elevator.stop();
	}
}

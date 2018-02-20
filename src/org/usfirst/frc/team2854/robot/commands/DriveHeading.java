package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveHeading extends Command {

	private DriveTrain drive;
	private double speed, heading, distance, ogDistancce;
	private double startingDistance;

	public DriveHeading(double speed, double distance, double heading) {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.distance = distance;
		this.ogDistancce = distance;
		this.speed = speed;
		this.heading = heading;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
		this.distance = Config.cyclesToInches * ogDistancce;
		startingDistance = drive.getAvgEncoder();
		drive.setNeutralMode(NeutralMode.Brake);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double angle = Robot.getSensors().getNavX().getAngle();
		double diff = (heading - angle) / 90;
		drive.drive(speed - diff, speed + diff, ControlMode.Velocity);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println(drive.getAvgEncoder() + " " + startingDistance + " " + distance);
		return Math.abs(drive.getAvgEncoder() - startingDistance) > Math.abs(distance);
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Reached " + distance);
		drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}

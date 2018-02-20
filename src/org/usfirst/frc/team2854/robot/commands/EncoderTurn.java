package org.usfirst.frc.team2854.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

/** */
public class EncoderTurn extends Command {

	private DriveTrain drive;
	private double revs, inchs;
	private double leftTarget, rightTarget;

	public EncoderTurn(double angle) {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.inchs = 79.25 * angle / 360d;
	}

	// Called just before this Command runs

	protected void initialize() {
		drive = ((DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.revs = drive.inchesToCycles(inchs);
		drive.setNeutralMode(NeutralMode.Coast);
		// System.out.println("Driving");
		drive.drive(-revs, revs, ControlMode.MotionMagic);
		leftTarget = (-revs * drive.getDriveConstant()) + drive.getLeftEncoder();
		rightTarget = (revs * drive.getDriveConstant()) + drive.getRightEncoder();
		setTimeout(1);

	}

	// Called repeatedly when this Command is scheduled to run

	protected void execute() {
		// System.out.println((revs * drive.getDriveConstant()) + " " +
		// drive.getAvgEncoder());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		SmartDashboard.putNumber("turn error", Math.abs(drive.getLeftEncoder() - leftTarget));
		return (Math.abs(drive.getLeftEncoder() - leftTarget) < 300
				&& Math.abs(drive.getRightEncoder() - rightTarget) < 300) || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		drive.setNeutralMode(NeutralMode.Brake);
		drive.drive(drive.getAvgEncoder(), drive.getAvgEncoder(), ControlMode.Position);
		drive.drive(0, 0, ControlMode.Velocity);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		drive.setNeutralMode(NeutralMode.Brake);

	}
}

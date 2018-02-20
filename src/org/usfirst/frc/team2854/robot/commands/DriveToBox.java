package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToBox extends Command {

	private DriveTrain drive;
	private Command c;
	private double revs;

	private double distance;
	private double targetPos;

	public DriveToBox(double distance) {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.distance = distance;
	}

	public DriveToBox() {
		this(0);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println("Initing command");
		Robot.getVision().hasProcessed = false;
		Robot.getVision().setShouldRun(true);
		System.out.println("Finished initing command");
		drive = ((DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// System.out.println("Exewcuting " + Robot.getVision().hasProcessed);
		if (Robot.getVision().hasProcessed == true) {
			this.revs = drive.inchesToCycles(distance - Robot.getVision().getDistanceToBox());
			drive.setNeutralMode(NeutralMode.Brake);
			// System.out.println("Driving");
			drive.drive(revs, revs, ControlMode.MotionMagic);
			targetPos = (revs * drive.getDriveConstant()) + drive.getAvgEncoder();
			Robot.getVision().setShouldRun(false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(drive.getAvgEncoder() - targetPos) < 500;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ending");
		Robot.getVision().setShouldRun(false);
		c = null;
	}

	// Called when another command which requires one or more of the same

	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("getting interrupted");
		Robot.getVision().setShouldRun(false);
		c = null;

	}
}

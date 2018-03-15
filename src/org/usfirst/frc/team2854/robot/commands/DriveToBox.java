package org.usfirst.frc.team2854.robot.commands;

import org.opencv.core.Point;
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

	private double targetPos;

	private Point center;
	private double offset;

	private long startTime;
	private boolean running = false;

	public DriveToBox() {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
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
		System.out.println("Exewcuting " + Robot.getVision().hasProcessed);
		if (Robot.getVision().hasProcessed == true) {
			Robot.getVision().setHasProcessed(false);
			center = Robot.getVision().getCenter();
			running = true;
			this.offset = center.x;
			startTime = System.nanoTime();
		}

		if (running) {
			System.out.println(offset);
			double normOffset = offset / 160d;
			double sensitivty = 8; //bigger is less
			drive.drive((.6 + normOffset / sensitivty) * -.5d, (.6 - normOffset / sensitivty) * -.5d);
			if ((System.nanoTime() - startTime) / 1E9d > .5) {
				running = false;
				drive.drive(0, 0);
			}
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
		//return Robot.getSensors().getUltraDistance() < 3;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Ending");
		Robot.getVision().setShouldRun(false);
	}

	// Called when another command which requires one or more of the same

	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("getting interrupted");
		Robot.getVision().setShouldRun(false);

	}
}

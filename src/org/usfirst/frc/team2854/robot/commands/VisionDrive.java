package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDrive extends Command {

	private NetworkTable tbl;
	private DriveTrain drive;

	public VisionDrive() {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
		tbl = tableInstance.getTable("CVResultsTable");
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!tbl.getInstance().isConnected()) {
			System.out.println("No connection");
			drive.drive(0, 0);
			return;
		}
		double max = 200;
		double x = tbl.getEntry("x").getDouble(max / 2d);
		double width = tbl.getEntry("width").getDouble(0);
		if (width == 0) {
			x = max / 2d;
			drive.drive(-.2, .2);
			return;
		}
		//System.out.println(x);
		//x += width / 2d;
		x = x > max ? max : x;
		x /= max;
		x -= .5;
		//System.out.println("Driving at power: " + x);
		drive.drive(-x / 1.3d -.4 , x / 1.3d -.4);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("Ultra dist < 4? " + (Robot.getSensors().getUltraDistance() < 4));
		return Robot.getSensors().getUltraDistance() < 9;
	}

	// Called once after isFinished returns true
	protected void end() {
		drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		drive.stop();
	}
}

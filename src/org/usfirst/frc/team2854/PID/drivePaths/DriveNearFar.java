package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.EncoderTurn;
import org.usfirst.frc.team2854.robot.commands.Outtake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveNearFar extends CommandGroup {

	public DriveNearFar(boolean isRightSide) {
		// get to side of switch
		int multiplier = isRightSide ? 1 : -1;
		// addSequential(new ClawSetpoint(-1000));
		addSequential(new DriveHeading(-.25, 135, 8 * multiplier));
		addSequential(new EncoderTurn(-90 * multiplier));
		// addSequential(new ElevatorSetPoint(-5800));
		// addSequential(new ClawSetpoint(-2000));
		addSequential(new DriveHeading(-.6, 35, -90 * multiplier));
		// addSequential(new Outtake(1.5, .75d));
		addSequential(new DriveStraight(.25, 10));
		addSequential(new DriveHeading(.6, 70, (-180 - 10) * multiplier));
		addSequential(new DriveHeading(.30, 20, (-180 + 45) * multiplier));
		addSequential(new DriveHeading(-.30, 30, (-180 + 45) * multiplier));
		addSequential(new DriveHeading(.30, 10, (-180 + 45) * multiplier));
		addSequential(new DriveHeading(-.30, 20, (-180 + 45) * multiplier));

	}
}

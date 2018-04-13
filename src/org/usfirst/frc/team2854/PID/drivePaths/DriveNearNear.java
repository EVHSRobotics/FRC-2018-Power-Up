package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.AutoIntakeDrive;
import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.DriveToBox;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.EncoderTurn;
import org.usfirst.frc.team2854.robot.commands.GyroTurn;
import org.usfirst.frc.team2854.robot.commands.Intake;
import org.usfirst.frc.team2854.robot.commands.Outtake;
import org.usfirst.frc.team2854.robot.commands.Shift;
import org.usfirst.frc.team2854.robot.commands.ToggleClamp;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain.GearState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveNearNear extends CommandGroup {

	public DriveNearNear(boolean isRightSide) {

		double multiplier = isRightSide ? 1 : -1;

		addSequential(new ClawSetpoint(-2000));// sets claw to downward position
		addParallel(new Shift(GearState.SLOW));
		addSequential(new DriveHeading(-.75, 208, 0 * multiplier)); // -.8
		addSequential(new DriveHeading(-.25, 25, -40 * multiplier)); // -.25

		addSequential(new ElevatorSetPoint(-23000));
		addSequential(new ClawSetpoint(-2000));
		addSequential(new Outtake(.75, 1));
		addSequential(new ElevatorSetPoint(-1500));
		addSequential(new ToggleClamp());
		addSequential(new ClawSetpoint(-4250));
		addSequential(new EncoderTurn((-42 - 15) * multiplier));
		addSequential(new DriveStraight(-.5, 46));
		addSequential(new EncoderTurn(-50 * multiplier));

		addSequential(new AutoIntakeDrive(-.6));

		// addSequential(new DriveStraight(.5, 20));

		addSequential(new ElevatorSetPoint(-10000));
		addSequential(new ClawSetpoint(-2500));
		addSequential(new DriveStraight(-.5, 15));
		addSequential(new Outtake(1.5, .55d));
		addSequential(new DriveStraight(.5, 30));
		addSequential(new ElevatorSetPoint(-1500));
		//
		// addParallel(new Shift(GearState.FAST));

	}
}

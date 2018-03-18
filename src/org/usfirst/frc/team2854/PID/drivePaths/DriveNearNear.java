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

		
		// buttonA2.whenPressed(new ClawSetpoint(0));
		// buttonB2.whenPressed(new ClawSetpoint(750));
		// buttonY2.whenPressed(new ClawSetpoint(3000));
		double multiplier = isRightSide ? 1 : -1;

		addSequential(new ClawSetpoint(-1000));
		addParallel(new Shift(GearState.SLOW));
//		addSequential(new DriveHeading(-.25, 205.47, 0)); comp
		addSequential(new DriveHeading(-.8, 215.47, 0 * multiplier));
//		addSequential(new DriveHeading(-.2, 20, -47)); comp
		addSequential(new DriveHeading(-.25, 25, -40 * multiplier));
		addSequential(new ElevatorSetPoint(-20000));
		addSequential(new ClawSetpoint(-2000));
		addSequential(new Outtake(1.5, .75));
		addSequential(new ElevatorSetPoint(-1500));
//		addSequential(new EncoderTurn(-47 - 25)); comp
		addSequential(new EncoderTurn((-42 - 15)*multiplier));
		addSequential(new ToggleClamp());	
		addSequential(new DriveStraight(-.5, 40));
		addSequential(new EncoderTurn(-50 * multiplier));
		//addSequential(new DriveStraight(-.3, 30));
		//addParallel(new AutoIntake());
		//addSequential(new DriveStraight(-.3, 30));
		
		addSequential(new AutoIntakeDrive(-.3));
		
//		addSequential(new DriveStraight(-.3, 19));4
//		addParallel(new Intake(1.5));
//		addSequential(new ToggleClamp());
//		addParallel(new Intake(1.5));
		
//		addSequential(new DriveStraight(.2, 20)); comp
		addSequential(new DriveStraight(.2, 5));
//		addSequential(new DriveHeading(.2, 20, -180 + 25));
//		addSequential(new ClawSetpoint(2000)); comp
		addSequential(new ClawSetpoint(-2000));
//		addSequential(new ElevatorSetPoint(-19000)); comp
		addSequential(new ElevatorSetPoint(-10000));
		addSequential(new DriveStraight(-.2, 15));
		addSequential(new Outtake(1.5, .65d));
		addSequential(new ElevatorSetPoint(-1500));
		
//		addSequential(new DriveStraight(.2, 40));
//		addSequential(new EncoderTurn(90 * multiplier));
//		addSequential(new DriveStraight(.2, 13));
//		addSequential(new EncoderTurn(-90 * multiplier));
//		addSequential(new DriveStraight(-.2, 40));
//		addParallel(new AutoIntake());
//		addSequential(new DriveStraight(-.2, 20));
//		
//		addSequential(new DriveStraight(.2, 5));
//		addSequential(new ClawSetpoint(-2000));
//		addSequential(new EncoderTurn(5 * multiplier));
//		addSequential(new ElevatorSetPoint(-10000));
//		addSequential(new DriveStraight(-.2, 10));
		//addSequential(new Outtake(1.5, .75d));
		//addSequential(new ElevatorSetPoint(-1500));
		
		
		
//		addSequential(new ClawSetpoint(750));
//		addSequential(new EncoderTurn(-47 - 60));
		
		addParallel(new Shift(GearState.FAST));

		// addSequential(new GyroTurn(180, 5));

		// addSequential(new DriveHeading(-.5, 41, 0));

		// addSequential(new Delay(2));
		// addSequential(new Turn(180));
		// addSequential(new DriveMotionMagik(90));
		
		// addSequential(new Delay(.5));
		// addSequential(new DriveMotionMagik(13));
		// addSequential(new Outtake());

	}
}

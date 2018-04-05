package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.AutoIntakeDrive;
import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
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
public class DriveFarNear extends CommandGroup {

	public DriveFarNear(boolean isRight) {
		
		double multiplier = isRight ? 1 : -1;
		
		//addSequential(new ClawSetpoint(-1000));
		addParallel(new Shift(GearState.SLOW));
		addSequential(new DriveHeading(-.7, 215.47, 0 * multiplier));
		addSequential(new DriveHeading(-.4, 25, -40 * multiplier));
		//addSequential(new ElevatorSetPoint(-20000));
		//addSequential(new ClawSetpoint(-2000));
		//addSequential(new Outtake(1.5, .75));
		//addSequential(new ElevatorSetPoint(-1500));
		addSequential(new EncoderTurn((-42 - 15)*multiplier));
		//addSequential(new ToggleClamp());	
		addSequential(new DriveStraight(-.6, 40));
		addSequential(new EncoderTurn(-50 * multiplier));
		addSequential(new DriveHeading(-.6, 5, -180 * multiplier));
		//addSequential(new AutoIntakeDrive(-.3));
		//////addSequential(new EncoderTurn(50 * multiplier));
		addSequential(new DriveHeading(.6, 95, (45 - 180)* multiplier)); 
		addSequential(new EncoderTurn(50 * multiplier));
		addSequential(new DriveHeading(-.3, 5, (90 - 180)* multiplier));
		//addSequential(new DriveStraight(.3, 40));


		//stuff before VVVVVVVVVVVVVVVVVVVv

		// buttonA2.whenPressed(new ClawSetpoint(0));
		// buttonB2.whenPressed(new ClawSetpoint(750));
		// buttonY2.whenPressed(new ClawSetpoint(3000));


		//addSequential(new ClawSetpoint(1000));
//		addSequential(new ElevatorSetPoint(-21000));

//		addParallel(new Shift(GearState.SLOW));
//		
//		//Get to scale
//		addSequential(new DriveHeading(-.75, 205.47, 0));
//		//possibly move elevator up
//		addSequential(new DriveHeading(-.75, 20, -47));
//		//shoot into scale
//		addSequential(new ElevatorSetPoint(-21000));
//		addSequential(new ClawSetpoint(3000));
//		addSequential(new Outtake(1.5));
//		addSequential(new ElevatorSetPoint(100));		
//		addSequential(new ClawSetpoint(500));
//		//intake next block
//		addSequential(new EncoderTurn(-47 - 25));
//		addSequential(new ToggleClamp());	
//		addSequential(new DriveStraight(-.3, 40));
//		addSequential(new EncoderTurn(-50));
//		addSequential(new DriveStraight(-.3, 19));
//		addParallel(new Intake(1.5));
//		addSequential(new ToggleClamp());
//		addParallel(new Intake(1.5));
//		//get back to scale
//		addSequential(new DriveStraight(.2, 17));
//		addSequential(new EncoderTurn(255));
//		addSequential(new DriveStraight(-.75, 10));
//		//shoot into scale
//		addSequential(new ElevatorSetPoint(-21000));
//		addParallel(new ClawSetpoint(3000));
//		addSequential(new Outtake(1.5, 1));
//		addSequential(new ClawSetpoint(750));
//		addSequential(new EncoderTurn(-47 - 60));
//		
//		addParallel(new Shift(GearState.FAST));

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

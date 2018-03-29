package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
import org.usfirst.frc.team2854.robot.commands.DriveStraight;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.EncoderTurn;
import org.usfirst.frc.team2854.robot.commands.Outtake;
import org.usfirst.frc.team2854.robot.commands.Shift;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain.GearState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveFarFar extends CommandGroup {

	public DriveFarFar(boolean isRightSide) {

		// buttonA2.whenPressed(new ClawSetpoint(0));
		// buttonB2.whenPressed(new ClawSetpoint(750));
		// buttonY2.whenPressed(new ClawSetpoint(3000));

		
		//addSequential(new ClawSetpoint(1000));
//		addSequential(new ElevatorSetPoint(-21000));
		
		double multiplier = isRightSide ? 1 : -1;
//		addSequential(new DriveStraight(-.25, 30));
//		addSequential(new Delay(5));
		addSequential(new ClawSetpoint(-1000));

		addParallel(new Shift(GearState.SLOW));
		addSequential(new DriveStraight(-.75, 215));
		addSequential(new Delay(0.25d));
		addSequential(new EncoderTurn(-90 * multiplier));
		addSequential(new Delay(.25d));
		addSequential(new DriveHeading(-.75, 225, -90 * multiplier));
		addSequential(new EncoderTurn(90 * multiplier));
		addSequential(new ClawSetpoint(-2000));
		addSequential(new ElevatorSetPoint(-20000));
//		addSequential(new Outtake(.5, .75));
//
//		addSequential(new EncoderTurn(180));
//		
//		addSequential(new DriveStraight(-.25, 25));
//		addParallel(new AutoIntake());
//		addSequential(new DriveStraight(.25, 5));
//		
//		addSequential(new ElevatorSetPoint(-5800));
//		addSequential(new ClawSetpoint(-2000));
//		addSequential(new DriveStraight(-.25, 5));
//		addSequential(new Outtake(1.5, .75d));
//		addSequential(new DriveStraight(.25, 5));
//		addSequential(new ElevatorSetPoint(-1500));
//		addSequential(new DriveStraight(.25, 5));
//		addSequential(new EncoderTurn(180));
		

//----------------------------------------------------
		
////		addSequential(new DriveHeading(.2, 20, -180 + 25));
////		addSequential(new ClawSetpoint(2000)); comp
//		addSequential(new ClawSetpoint(-2000));
////		addSequential(new ElevatorSetPoint(-19000)); comp
//		addSequential(new ElevatorSetPoint(-10000));
//		addSequential(new DriveStraight(-.2, 15));
//		addSequential(new Outtake(1.5, .75d));
//		addSequential(new ElevatorSetPoint(-1500));
//		addSequential(new Delay(1));
//		addSequential(new EncoderTurn(-90 * multiplier));
//		addSequential(new DriveStraight(.25, 15));
//		addSequential(new ElevatorSetPoint(-18500));
//		addSequential(new ClawSetpoint(3000));
//		addSequential(new Outtake(1, .75d));
//		addSequential(new ElevatorSetPoint(-1500));


	}

}

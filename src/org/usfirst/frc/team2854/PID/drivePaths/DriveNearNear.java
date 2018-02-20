package org.usfirst.frc.team2854.PID.drivePaths;

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
public class DriveNearNear extends CommandGroup {

	public DriveNearNear() {

		// buttonA2.whenPressed(new ClawSetpoint(0));
		// buttonB2.whenPressed(new ClawSetpoint(750));
		// buttonY2.whenPressed(new ClawSetpoint(3000));


		//addSequential(new ClawSetpoint(1000));
		addParallel(new Shift(GearState.SLOW));
		addSequential(new DriveHeading(-.75, 205.47, 0));
		addSequential(new DriveHeading(-.75, 20, -47));
		addSequential(new ElevatorSetPoint(-19000));
		addSequential(new Outtake(1.5));		
		addSequential(new EncoderTurn(-47 - 25));
		addSequential(new ToggleClamp());	
		addSequential(new DriveStraight(-.3, 40));
		addSequential(new EncoderTurn(-50));
		addSequential(new DriveStraight(-.3, 19));
		addParallel(new Intake(1.5));
		addSequential(new ToggleClamp());
		addParallel(new Intake(1.5));
		
		addSequential(new DriveStraight(.2, 20));
		addSequential(new ClawSetpoint(2000));
		addSequential(new ElevatorSetPoint(-19000));
		addSequential(new Outtake(1.5, .75d));

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

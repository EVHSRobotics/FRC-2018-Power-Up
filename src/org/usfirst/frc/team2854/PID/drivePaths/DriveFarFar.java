package org.usfirst.frc.team2854.PID.drivePaths;

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

	public DriveFarFar() {

		// buttonA2.whenPressed(new ClawSetpoint(0));
		// buttonB2.whenPressed(new ClawSetpoint(750));
		// buttonY2.whenPressed(new ClawSetpoint(3000));


		//addSequential(new ClawSetpoint(1000));
//		addSequential(new ElevatorSetPoint(-21000));

		addParallel(new Shift(GearState.SLOW));
		addSequential(new DriveStraight(-.25, 215));
		addSequential(new Delay(0.5));
		addSequential(new EncoderTurn(-90));
		addSequential(new Delay(1));
		addSequential(new DriveHeading(-.25, 175, -90));
		addSequential(new Delay(1));
		addSequential(new EncoderTurn(-90));

		addSequential(new ElevatorSetPoint(-5800));
		addSequential(new ClawSetpoint(3000));
		addSequential(new Outtake(1));
	}

}

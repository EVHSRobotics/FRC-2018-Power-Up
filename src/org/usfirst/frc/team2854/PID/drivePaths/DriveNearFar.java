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
		if (isRightSide) {
			addSequential(new ClawSetpoint(-1000));
			addSequential(new DriveHeading(-.65, 135, 8));
			addSequential(new Delay(0.5));
			addSequential(new EncoderTurn(-90));
			// shoot
			addSequential(new ElevatorSetPoint(-5800));
			addSequential(new ClawSetpoint(-2000));
			//addSequential(new DriveHeading(-.35, 50, -90));
			addSequential(new DriveStraight(-.45, 50));
			addSequential(new DriveStraight(-.25, 20));
			//addSequential(new DriveHeading(.25, 5, 90)); 
			addSequential(new Outtake(1.5, .75d));
			//addSequential(new ClawSetpoint(-2000));
			//addSequential(new DriveStraight(.35, 50));
			//addSequential(new ElevatorSetPoint(-1500));
			// face forward
			//addSequential(new EncoderTurn(90));
		} else {
			addSequential(new ClawSetpoint(-1000));
			addSequential(new DriveHeading(-.5, 135, -8));
			addSequential(new Delay(0.5));
			addSequential(new EncoderTurn(90));
			// shoot
			addSequential(new DriveHeading(-.5, 50, 90));
			//addSequential(new DriveHeading(.25, 5, 90));
			addSequential(new ElevatorSetPoint(-5800));
			addSequential(new ClawSetpoint(-2000));
			addSequential(new Outtake(1.5, .75d));
			addSequential(new ElevatorSetPoint(-1500));
			// face forward
			addSequential(new EncoderTurn(-90));
		}

	}
}

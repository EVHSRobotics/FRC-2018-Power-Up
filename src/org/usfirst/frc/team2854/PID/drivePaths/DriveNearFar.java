package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.AutoIntakeDrive;
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
		addSequential(new ClawSetpoint(-2000));//claw to the ground 
		addSequential(new DriveHeading(-.75, 135, 8 * multiplier ));// forward at an angle
		addSequential(new EncoderTurn(-90 * multiplier));//turn 90 degrees toward switch
		addSequential(new ElevatorSetPoint(-5800));//elvator raised to the middle outtake
		addSequential(new ClawSetpoint(-2000));//claw to half height
		addSequential(new DriveHeading(-.6, 35, -90 * multiplier));//drive toward switch
		addSequential(new Outtake(.75, .75d));//shoots cube into the switch
		addSequential(new DriveStraight(.25, 10));//drive away from switch
		addSequential(new ClawSetpoint(-1000));//claw up
		addSequential(new ElevatorSetPoint(-1500));//elevator back to initial position
		addSequential(new DriveHeading(.6, 70, (-180 - 10) * multiplier));
		addSequential(new DriveHeading(.30, 20, (-180 + 45) * multiplier));//14//16
		addSequential(new ClawSetpoint(-4250));//claw back to initial position
		addSequential(new DriveHeading(-.30, 30, (-180 + 45 - 15) * multiplier));////-21//-24
		addSequential(new AutoIntakeDrive(-.5));//drive forward to the cube
		addSequential(new DriveHeading(.30, 10, (-180 + 45) * multiplier));//drive backward to get ready to raise elevator
		addSequential(new ElevatorSetPoint(-5800));//get elevator up to switch position
		addSequential(new ClawSetpoint(-2000));//raises claw to the middle position
		addSequential(new DriveHeading(-.30, 20, (-180 + 45) * multiplier));//drives forward to the switch(bumpers touch the switch)
		addSequential(new Outtake(.75, .75d));//outtakes cube into the switch
	}
}	
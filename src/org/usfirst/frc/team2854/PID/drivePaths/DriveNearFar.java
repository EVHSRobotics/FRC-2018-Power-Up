package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.Delay;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.EncoderTurn;
import org.usfirst.frc.team2854.robot.commands.Outtake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveNearFar extends CommandGroup {

    public DriveNearFar() {
    	//get to side of switch
		addSequential(new DriveHeading(-.5, 135, 8));
		addSequential(new Delay(0.5));
		addSequential(new EncoderTurn(-90));
		//shoot
		addSequential(new DriveHeading(.5, 20, -90));
		addSequential(new ElevatorSetPoint(-5800));
		addSequential(new ClawSetpoint(2000));
		addSequential(new Outtake(1.5, .75d));
		//face forward
		addSequential(new EncoderTurn(90));
		
    }
}

package org.usfirst.frc.team2854.PID.drivePaths;

import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.DriveHeading;
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
    	
    	
    	
    	
    	
    	double multiplier = isRightSide ? 1 : -1;
    	
    	addParallel(new Shift(GearState.SLOW));
    	
    	addSequential(new ClawSetpoint(-2000));
    	addSequential(new DriveHeading(-.75, 220, 5 * multiplier));
    	addSequential((new EncoderTurn(-90 * multiplier)));
    	addSequential(new DriveHeading(-.75, 215, -90 * multiplier));
    	addSequential(new DriveHeading(-.3, 10, 0 * multiplier));
    	addSequential(new DriveHeading(-.3, 20, 30 * multiplier));
    	
    	//addSequential(new ElevatorSetPoint(-23000));
		//addSequential(new ClawSetpoint(-2000));
		//addSequential(new Outtake(.75, .8));
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}

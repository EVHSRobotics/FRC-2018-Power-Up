package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveTest extends CommandGroup {

    public DriveTest() {
    	addSequential(new DriveMotionMagik(16 * 12));
    	addSequential(new PIDTurn(90, 1, true));
    	addSequential(new DriveMotionMagik(8 * 12 + 8));
    	addSequential(new PIDTurn(-90, 1, true));
    	addSequential(new DriveMotionMagik(13 * 12 + 4));
    	
    	addSequential(new PIDTurn(180, 1, true));
    	
    	addSequential(new DriveMotionMagik(16 * 12));
    	addSequential(new PIDTurn(-90, 1, true));
    	addSequential(new DriveMotionMagik(8 * 12 + 8));
    	addSequential(new PIDTurn(90, 1, true));
    	addSequential(new DriveMotionMagik(13 * 12 + 4));

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

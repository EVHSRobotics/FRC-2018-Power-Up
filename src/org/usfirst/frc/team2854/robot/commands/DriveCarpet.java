package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveCarpet extends CommandGroup {

	public DriveCarpet() {
		int counter = -90;
		for (int i = 0; i < 8; i++) {//19
			addSequential(new DriveMotionMagik(17 * 12));
			//addSequential(new Delay(4));
			addSequential(new DriveMotionMagik(-17 * 12));
			//addSequential(new Delay(4));
			//addSequential(new PIDTurn(counter, .5, false));
			//counter += -90;
			//addSequential(new DriveMotionMagik(3.5 * 12));
			//addSequential(new PIDTurn(counter, .5, false));
			//counter += -90;
		}

		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}

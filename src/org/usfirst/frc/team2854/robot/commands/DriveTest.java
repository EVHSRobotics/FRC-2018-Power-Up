package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveTest extends CommandGroup {

	public DriveTest() {

		for (int i = 0; i < 3; i++) {

			double ang = Robot.getSensors().getNavX().getAngle();
			addSequential(new DriveMotionMagik(16 * 12));
			addSequential(new EncoderTurn(79.25 / 4d));
			ang += 90;
			addSequential(new PIDTurn(ang, 1, false));
			addSequential(new DriveMotionMagik(8 * 12 + 8));
			addSequential(new EncoderTurn(-79.25 / 4d));
			ang -= 90;
			addSequential(new PIDTurn(ang, 1, false));

			addSequential(new DriveMotionMagik(6 * 12 + 4));

			// addSequential(new PIDTurn(0, 1, false));
			addSequential(new EncoderTurn(-79.25 / 2d));
			ang -= 180;
			addSequential(new PIDTurn(ang, 1, false));

			addSequential(new DriveMotionMagik(6 * 12 + 4));
			addSequential(new EncoderTurn(79.25 / 4d));
			ang += 90;
			addSequential(new PIDTurn(ang, 1, false));

			addSequential(new DriveMotionMagik(8 * 12 + 8));
			addSequential(new EncoderTurn(-79.25 / 4d));
			ang -= 90;
			addSequential(new PIDTurn(ang, 1, false));

			addSequential(new DriveMotionMagik(16 * 12));
			addSequential(new EncoderTurn(79.25 / 2d));
			ang += 180;
			addSequential(new PIDTurn(ang, 1, false));

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

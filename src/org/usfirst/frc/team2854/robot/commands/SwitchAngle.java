package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchAngle extends CommandGroup {

	public SwitchAngle() {

		addSequential(new SetClamp(false));
		addParallel(new ElevatorSetPoint(-7750));
		addSequential(new ClawSetpoint(750));
		addSequential(new DriveStraight(-.25, 10));
		addSequential(new Outtake(1, .75));
		addSequential(new ElevatorSetPoint(0));
		addSequential(new ClawSetpoint(0));
		addSequential(new SetClamp(true));

	}
}

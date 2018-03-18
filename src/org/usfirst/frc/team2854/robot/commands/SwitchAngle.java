package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchAngle extends CommandGroup {

	public SwitchAngle() {

		addSequential(new ElevatorSetPoint(-6400));
		addSequential(new ClawSetpoint(-2000));
	}
}

package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleAngle extends CommandGroup {

	public ScaleAngle() {
		
		addSequential(new ElevatorSetPoint(-23000));
		addSequential(new ClawSetpoint(1500));

	}
}

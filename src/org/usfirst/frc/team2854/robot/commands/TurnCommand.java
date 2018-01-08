package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class TurnCommand extends Command {

	protected double targetAngle;
	
	public TurnCommand(double targetAngle) {
		this.targetAngle = targetAngle;
	}
	
	public void setAngle(double angle) {
		this.targetAngle = angle;
	}

}

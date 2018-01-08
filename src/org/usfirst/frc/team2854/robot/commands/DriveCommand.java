package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public abstract class DriveCommand extends Command{

	protected double distance;
	
	public DriveCommand(double distance) {
		this.distance = distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}

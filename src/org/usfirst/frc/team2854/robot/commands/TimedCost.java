package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Elevator;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedCost extends TimedCommand {

	private DriveTrain drive;
	
    public TimedCost(double time) {
    	super(time);
        requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
    	drive.setNeutralMode(NeutralMode.Coast);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

   

    // Called once after isFinished returns true
    protected void end() {
    	drive.setNeutralMode(NeutralMode.Brake);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drive.setNeutralMode(NeutralMode.Brake);
    }
}

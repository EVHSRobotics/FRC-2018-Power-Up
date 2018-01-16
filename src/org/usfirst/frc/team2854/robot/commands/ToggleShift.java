package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Shifter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleShift extends Command {

	Shifter shifter;
	
    public ToggleShift() {
        requires(Robot.getSubsystem(SubsystemNames.SHIFTER));
        }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shifter = (Shifter) Robot.getSubsystem(SubsystemNames.SHIFTER);
    	shifter.toggleShift();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Shift is somehow being interupted");
    }
}
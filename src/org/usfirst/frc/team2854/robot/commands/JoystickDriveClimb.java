package org.usfirst.frc.team2854.robot.commands;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Climb;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDriveClimb extends Command {

	private Climb climb;
	
    public JoystickDriveClimb() {
        requires(Robot.getSubsystem(SubsystemNames.CLIMB));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	climb = (Climb) Robot.getSubsystem(SubsystemNames.CLIMB);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("Running climb");
    	double value = -OI.secondaryJoystick.getRawAxis(1);
    	value = Math.abs(value) < .1d ? 0 : value;
    	climb.drive(value);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class CenterBlock extends TimedCommand {

	private Claw claw;
	private double power;
	private boolean side;
	
    public CenterBlock(double time) {
    	this(time, 1);
    }
    
    public CenterBlock(double time, double power) {
    	super(time);
    	this.power = power;
        requires(Robot.getSubsystem(SubsystemNames.CLAW));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	claw.runIntakeSide(power, side);
    }



    // Called once after isFinished returns true
    protected void end() {
    	side = !side;
    	//claw.runIntake(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	side = !side;
    	//claw.runIntake(0);
    }
}

package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives the robot using joystick drive.
 */
public class JoystickDrive extends Command {
	
	private DriveTrain drive;


    /**
     * Default constructor.
     */
    public JoystickDrive() {
    	requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    }

    // Called just before this Command runs the first time
    /** 
     * Initializes the drivetrain.
     */
    protected void initialize() {
    	drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
    	
   
    }

    
    // Called repeatedly when this Command is scheduled to run
    /** 
     * Executes the driving.
     */
    protected void execute() {	
    	double speed = OI.joystick.getRawAxis(2) - OI.joystick.getRawAxis(3);
    	//SmartDashboard.putNumber("target val", speed * Config.manuelSpeedMultiplier);
    	//speed *= Config.targetVel;
    	//double left = (Math.abs(OI.joystick.getRawAxis(1)) < .1 ? 0 : OI.joystick.getRawAxis(1)) * 8400;
    	//double right = (Math.abs(OI.joystick.getRawAxis(5)) < .1 ? 0 : OI.joystick.getRawAxis(5)) * 8400;
    	
    	
    	drive.drive(speed * Config.manuelSpeedMultiplier, speed * Config.manuelSpeedMultiplier, ControlMode.Velocity);
    	
    	//drive.drive(speed, speed, ControlMode.PercentOutput);
    }

    // Make this return true when this Command no longer needs to run execute()
    /**
     * Stops drive train.
     */
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    
    /** 
     * Stops driving.
     */
    protected void end() {
    	drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    /** 
     * Stops drive when interrupted.
     */
    protected void interrupted() {
    	drive.stop();
    }
}

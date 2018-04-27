package org.usfirst.frc.team2854.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

/** */
public class JoystickDrive extends Command {

	private DriveTrain drive;

	public JoystickDrive() {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//double throttle = OI.mainJoystick.getRawAxis(3) - OI.mainJoystick.getRawAxis(2);
		double throttle = OI.throttle.get();
		throttle = Math.abs(throttle) < .1 ? 0 : throttle;

		//double turn = OI.mainJoystick.getRawAxis(0);
		
		double turn = OI.turn.get() ;
		turn = Math.abs(turn) < .05 ? 0 : turn;
		if(OI.mainJoystick.getRawButton(3)) {
			turn /= 2d;
		}
		//turn *= -(1+Robot.getSensors().getNavX().getVelocityX());
		drive.drive(sig(throttle - cubeRoot(turn)), sig(throttle + cubeRoot(turn)), ControlMode.PercentOutput);

	}
	

	
	public double cubeRoot(double val) {
		if(val >= 0) {
			return Math.pow(val,  3/2d);
		} else {
			return -Math.pow(-val, 3/2d);
		}
	}

	public double sig(double val) {
		return 2/(1 + Math.pow(Math.E, -7 * Math.pow(val, 3))) - 1;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		drive.stop();
	}

}

package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.PID.PIDConstant;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class PIDTurn extends PIDCommand {

	
	private static PIDConstant pid = PIDConstant.autoTurn;
	private AHRS navX = Robot.getSensors().getNavX();
	
	private double target;
	private double thresh;
	
	private DriveTrain driveTrain;
	
	public PIDTurn(double target, double thresh, boolean relative) {
		super("Auto Turn", pid.getP(), pid.getI(), pid.getD(), pid.getF());
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		driveTrain = (DriveTrain) (Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.setInputRange(-180, 180);
		this.thresh = thresh;
		if(relative) {
			this.target = target + navX.getAngle();
		} else {
			this.target = target;
		}
	}

	@Override
	protected double returnPIDInput() {
		return (navX.getAngle() - target);
	}

	@Override
	protected void usePIDOutput(double output) {
		driveTrain.drive(-output, output, ControlMode.Velocity);
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return Math.abs(navX.getAngle() - target) < thresh;
	}


}

package org.usfirst.frc.team2854.robot.subsystems;

import java.util.Random;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.team2854.mapauto.Driveable;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem{

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

	private TalonSRX leftT1, leftT2, rightT1, rightT2;
	
	private ControlMode mode;
	
	private boolean side = true;

	private double lastValLeft, currentValLeft;
	private double lastValRight, currentValRight;
	
	private long lastTime, currentTime;


	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}
	

	public DriveTrain() {
		leftT1 = new TalonSRX(RobotMap.leftTalonID1);
		leftT1.setInverted(side);
		
		leftT2 = new TalonSRX(RobotMap.leftTalonID2);
		leftT2.setInverted(side);

		rightT1 = new TalonSRX(RobotMap.rightTalonID1);
		rightT1.setInverted(!side);

		rightT2 = new TalonSRX(RobotMap.rightTalonID2);
		rightT2.setInverted(!side);
		
	
		
		

		
		configureTalon(leftT2, side);
		configureTalon(rightT2, !side);
		
		
		
	}
	
	private void configureTalon(TalonSRX talon, boolean side) {
		
		final int timeOutConstant = 20;
		final int PIDIndex = 0;

		final double P = 1;
		final double I = 0;
		final double D = 0;
		final double F = 0;
		
		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIndex, timeOutConstant);
		talon.setSensorPhase(side);
		
        talon.configNominalOutputForward(0, timeOutConstant);
        talon.configNominalOutputReverse(0, timeOutConstant);
        talon.configPeakOutputForward(1, timeOutConstant);
        talon.configPeakOutputReverse(-1, timeOutConstant);
        

        talon.config_kP(PIDIndex, P, timeOutConstant);
        talon.config_kI(PIDIndex, I, timeOutConstant);
        talon.config_kD(PIDIndex, D, timeOutConstant);
        talon.config_kF(PIDIndex, F, timeOutConstant);
        
		//int absolutePosition = talon.getSelectedSensorPosition(timeOutConstant) & 0xFFF; 
        talon.setSelectedSensorPosition(0, PIDIndex, timeOutConstant);
        
        lastValLeft = leftT2.getSelectedSensorVelocity(0);
        currentValLeft = leftT2.getSelectedSensorVelocity(0);
        lastValRight = rightT2.getSelectedSensorVelocity(0);
        currentValRight = rightT2.getSelectedSensorVelocity(0);        
	}
	
	

	
	
	public void writeToDashBoard() {
			
		currentTime = System.nanoTime();
        currentValLeft = leftT2.getSelectedSensorVelocity(0);
        currentValRight = rightT2.getSelectedSensorVelocity(0);   
        
        double deltaLeft = currentValLeft - lastValLeft;
        double deltaRight = currentValRight - lastValRight;
        double deltaTime = (currentTime - lastTime) / 1E9d;
		
		SmartDashboard.putNumber("LeftT1%", leftT1.getOutputCurrent());
		SmartDashboard.putNumber("LeftT2%", leftT2.getOutputCurrent());
		SmartDashboard.putNumber("RightT1%", rightT1.getOutputCurrent());
		SmartDashboard.putNumber("RightT2%", rightT2.getOutputCurrent());
		
		SmartDashboard.putNumber("Left Encoder", deltaLeft / deltaTime);
		SmartDashboard.putNumber("Right Encoder", deltaRight  / deltaTime);
		
		lastTime = currentTime;
		lastValLeft = currentValLeft;
		lastValRight = currentValRight;

	}

	public void drive(double left, double right, ControlMode mode) {
		
		if(mode == ControlMode.Velocity || mode == ControlMode.Position) {
			left = 256/(4 * Math.PI * 10) * left;
			right = 256/(4 * Math.PI * 10) * right;
		}
		
		leftT2.set(mode, left * Config.totalDriveSpeedMultiplier);	
		leftT1.set(ControlMode.Follower, leftT2.getDeviceID());
		rightT2.set(mode, right * Config.totalDriveSpeedMultiplier);	
		rightT1.set(ControlMode.Follower, rightT2.getDeviceID());
	}
	
	public void drive(double left, double right) {
		drive(left, right, ControlMode.PercentOutput);
	}


	public void stop() {
		drive(0, 0, ControlMode.PercentOutput);
	}



}

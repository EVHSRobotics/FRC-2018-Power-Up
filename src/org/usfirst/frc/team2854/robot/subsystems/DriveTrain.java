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
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

	private TalonSRX leftT1, leftT2, rightT1, rightT2;


	private boolean side = false;

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

		final double P_Left = .28;
		final double I_Left = .0006;
		final double D_Left = .8;
		final double F_Left = 0.1217;
		
		final double P_Right = .28;
		final double I_Right = .0006;
		final double D_Right = .8;
		final double F_Right = 0.1217;
		
		
		configureTalon(leftT2, P_Left, I_Left, D_Left, F_Left, !side);
		configureTalon(rightT2,  P_Right, I_Right, D_Right, F_Right, !side);

		leftT1.set(ControlMode.Follower, leftT2.getDeviceID());
		rightT1.set(ControlMode.Follower, rightT2.getDeviceID());	
		
	}
	
	private void configureTalon(TalonSRX talon, double P, double I, double D, double F, boolean side) {

		final int timeOutConstant = 10;
		final int PIDIndex = 0;



		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIndex, timeOutConstant);
		talon.setSensorPhase(side);

		talon.configNominalOutputForward(0, timeOutConstant);
		talon.configNominalOutputReverse(0, timeOutConstant);
		talon.configPeakOutputForward(1 * Config.totalDriveSpeedMultiplier, timeOutConstant);
		talon.configPeakOutputReverse(-1 * Config.totalDriveSpeedMultiplier, timeOutConstant);

		talon.config_kP(PIDIndex, P, timeOutConstant);
		talon.config_kI(PIDIndex, I, timeOutConstant);
		talon.config_kD(PIDIndex, D, timeOutConstant);
		talon.config_kF(PIDIndex, F, timeOutConstant);

		//talon.configMotionCruiseVelocity(8400, 10);
		//talon.configMotionAcceleration(8400, 10);
		
		// int absolutePosition = talon.getSelectedSensorPosition(timeOutConstant) &
		// 0xFFF;
		talon.setSelectedSensorPosition(0, PIDIndex, timeOutConstant);
		
		
		talon.configVoltageCompSaturation(12, 10);

	}

	public void writeToDashBoard() {

		SmartDashboard.putNumber("Left Velocity", leftT2.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Velocity", rightT2.getSelectedSensorVelocity(0));
		
		SmartDashboard.putNumber("Left Error", leftT2.getClosedLoopError(0));
		SmartDashboard.putNumber("Right Error", leftT2.getClosedLoopError(0));
		
		SmartDashboard.putNumber("Velocity", rightT2.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Throttle", rightT2.getMotorOutputPercent());
		SmartDashboard.putNumber("Error", rightT2.getClosedLoopError(0));

	}

	public void drive(double left, double right, ControlMode mode) {

		leftT2.set(mode, left * Config.totalDriveSpeedMultiplier);
		rightT2.set(mode, right * Config.totalDriveSpeedMultiplier);
		

		
	}

	public void drive(double left, double right) {
		drive(left, right, ControlMode.PercentOutput);
	}

	public void stop() {
		drive(0, 0);
	}

}

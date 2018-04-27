package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;
import org.usfirst.frc.team2854.robot.commands.JoystickDriveElevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends Subsystem implements Restartable {

	private TalonSRX talon;

	private double startPos;

	private double targetSpeed = 0;

	// Put methods for cont rolling this subsystem
	// here. Call these from Commands.

	public Elevator() {
		talon = new TalonSRX(RobotMap.elevator);

		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		talon.setInverted(false);
		talon.setSensorPhase(true);
		talon.setNeutralMode(NeutralMode.Brake);
		talon.enableVoltageCompensation(true);
		double P = .24d;
		double I = .0001d;
		double D = 15d;
		double F = 1023d / 24000;
		double targetSpeed = Config.upTargetSpeed;

		updatePID(P, I, D, F, targetSpeed);

		startPos = talon.getSelectedSensorPosition(0);

		

	}

	public void updateTargetSpeed(double targetSpeed) {
		talon.configMotionCruiseVelocity((int) targetSpeed / 1, 10);
		talon.configMotionAcceleration((int) (targetSpeed / .25d), 10);
		
	}

	public void updatePID(double P, double I, double D, double F, double targetSpeed) {
		final int timeOutConstant = 10;
		final int PIDIndex = 0;
		talon.config_kP(PIDIndex, P, timeOutConstant);
		talon.config_kI(PIDIndex, I, timeOutConstant);
		talon.config_kD(PIDIndex, D, timeOutConstant);
		talon.config_kF(PIDIndex, F, timeOutConstant);
		// System.out.println(targetSpeed);
		talon.configMotionCruiseVelocity((int) targetSpeed / 1, 10);
		talon.configMotionAcceleration((int) (targetSpeed / .20d), 10);
		talon.configAllowableClosedloopError(0, 5, 10);

	}

	public boolean isDone() {
		System.out.println("Running is done: error: " + talon.getClosedLoopError(0) + " current pos "
				+ talon.getSelectedSensorPosition(0) + " current target " + talon.getClosedLoopTarget(0));
		return Math.abs(talon.getSelectedSensorPosition(0) - talon.getClosedLoopTarget(0)) < 700;
	}

	public void resetEncoder() {
		startPos = talon.getSelectedSensorPosition(0);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDriveElevator());
	}

	public void drive(double value, ControlMode mode) {
		talon.set(mode, value);
	}

	public void stop() {
		drive(0, ControlMode.PercentOutput);
	}

	public double getPos() {
		return talon.getSelectedSensorPosition(0) - startPos;
	}

	public double getVel() {
		return talon.getSelectedSensorVelocity(0);
	}

	public void writeToDashboard() {
		//SmartDashboard.putNumber("velocity", getVel());
		//SmartDashboard.putNumber("pos", getPos());
		//SmartDashboard.putNumber("error", talon.getClosedLoopError(0));
		// System.out.println("actual error: " + talon.getClosedLoopError(0));
		//SmartDashboard.putNumber("Throttle", talon.getMotorOutputPercent());
		
		//SmartDashboard.putBoolean("Elavator at low", getPos() >= -1500);
		//SmartDashboard.putBoolean("Elavator at medium", getPos() <= -1500 && getPos() >= -5400);
		//SmartDashboard.putBoolean("Elavator at high", getPos() <= -5400 && getPos() >= -23000);
	}

	
	@Override
	public void enable() {
		System.out.println("Zeroing elevator pos");
		resetEncoder();
		System.out.println("Current pos: " + getPos());
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

}

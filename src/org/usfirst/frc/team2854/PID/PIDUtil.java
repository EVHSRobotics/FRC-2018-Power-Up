package org.usfirst.frc.team2854.PID;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.usfirst.frc.team2854.robot.Config;

public class PIDUtil {

	public static void updatePID(TalonSRX talon, PIDConstant PID) {
		updatePID(talon, PID.getP(), PID.getI(), PID.getD(), PID.getF(), PID.getTargetSpeed());
	}

	public static void updatePID(TalonSRX talon, double P, double I, double D, double F, double targetSpeed) {
		final int timeOutConstant = 10;
		final int PIDIndex = 0;
		talon.config_kP(PIDIndex, P, timeOutConstant);
		talon.config_kI(PIDIndex, I, timeOutConstant);
		talon.config_kD(PIDIndex, D, timeOutConstant);
		talon.config_kF(PIDIndex, F, timeOutConstant);
		// System.out.println(targetSpeed);
		talon.configMotionCruiseVelocity((int) targetSpeed/3, 10);
		talon.configMotionAcceleration((int) (targetSpeed / 1d), 10);
		talon.configAllowableClosedloopError(0, 5, 10);
	}

	public static void configureTalon(TalonSRX talon, boolean side) {

		final int timeOutConstant = 10;
		final int PIDIndex = 0;

		talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PIDIndex, timeOutConstant);
		talon.setSensorPhase(side);

		talon.configNominalOutputForward(0, timeOutConstant);
		talon.configNominalOutputReverse(0, timeOutConstant);
		talon.configPeakOutputForward(1 * Config.totalDriveSpeedMultiplier, timeOutConstant);
		talon.configPeakOutputReverse(-1 * Config.totalDriveSpeedMultiplier, timeOutConstant);

		// talon.configMotionCruiseVelocity(8400, 10);
		// talon.configMotionAcceleration(8400, 10);
		talon.setSelectedSensorPosition(0, PIDIndex, timeOutConstant);

		talon.configVoltageCompSaturation(12, 10);
		
		talon.setNeutralMode(NeutralMode.Brake);
	}
}

package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDriveClaw;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Claw extends Subsystem implements Restartable {

	private TalonSRX masterClaw, slaveClaw;
	private TalonSRX leftIn, rightIn;
	private DoubleSolenoid piston;

	private double startingPos;

	private int stallCounter = 0;

	private double intakeSpeed = 0;

	public Claw() {
		masterClaw = new TalonSRX(RobotMap.masterClaw);
		slaveClaw = new TalonSRX(RobotMap.slaveClaw);

		leftIn = new TalonSRX(RobotMap.leftIntake);
		rightIn = new TalonSRX(RobotMap.rightIntake);

		leftIn.setNeutralMode(NeutralMode.Coast);
		rightIn.setNeutralMode(NeutralMode.Coast);

		rightIn.setInverted(true);	

		boolean isPracticeBot = false;
		
		masterClaw.setInverted(!isPracticeBot);
		slaveClaw.setInverted(isPracticeBot);

		// slaveClaw.set(ControlMode.Follower, RobotMap.masterClaw);

		piston = new DoubleSolenoid(RobotMap.intakeUp, RobotMap.intakeDown);

		masterClaw.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		masterClaw.setSensorPhase(false);

		startingPos = masterClaw.getSelectedSensorPosition(0);

		masterClaw.setNeutralMode(NeutralMode.Brake);
		slaveClaw.setNeutralMode(NeutralMode.Brake);

		// leftIn.configOpenloopRamp(.1, 10);
		// rightIn.configOpenloopRamp(.1, 10);

		double P = 1023 / 3750;
		double I = 0;
		double D = 0;
		double F = 1023 / 3750; // 3

		updatePID(P, I, D, F, 0);

	}

	public void updateTargetSpeed(double targetSpeed) {
		masterClaw.configMotionCruiseVelocity((int) targetSpeed / 1, 10);
		masterClaw.configMotionAcceleration((int) (targetSpeed / .25d), 10);
	}

	public void updatePID(double P, double I, double D, double F, double targetSpeed) {
		final int timeOutConstant = 10;
		final int PIDIndex = 0;
		masterClaw.config_kP(PIDIndex, P, timeOutConstant);
		masterClaw.config_kI(PIDIndex, I, timeOutConstant);
		masterClaw.config_kD(PIDIndex, D, timeOutConstant);
		masterClaw.config_kF(PIDIndex, F, timeOutConstant);
		// System.out.println(targetSpeed);
		masterClaw.configMotionCruiseVelocity((int) targetSpeed / 1, 10);
		masterClaw.configMotionAcceleration((int) (targetSpeed / .25d), 10);
		masterClaw.configAllowableClosedloopError(0, 5, 10);

	}

	public double getClawPos() {
		return masterClaw.getSelectedSensorPosition(0) - startingPos;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDriveClaw());
	}

	public void driveClaw(double speed, ControlMode mode) {
		System.out.println(getClawPos());
		//getClawPos() > -400 COMP
		if (getClawPos() > -400) {
			speed = -.75;
		}
		// System.out.println(speed);
		masterClaw.set(mode, speed);
		slaveClaw.set(mode, speed);

	}

	public void zeroEncoder() {
		startingPos = masterClaw.getSelectedSensorPosition(0);
	}

	public boolean isIntakeStalling() {
		double stallMax = 19;
		System.out.println(
				"checking motor stall, current: " + Math.abs(leftIn.getOutputCurrent()) + ", trigger at " + stallMax);
		return Math.abs(leftIn.getOutputCurrent()) > stallMax;
	}

	public void writeToDashboard() {
		SmartDashboard.putNumber("Claw encoder", getClawPos());
		// SmartDashboard.putNumber("Claw output power",
		// masterClaw.getMotorOutputPercent());
		// SmartDashboard.putNumber("Claw target", masterClaw.getClosedLoopTarget(0));
		// SmartDashboard.putNumber("claw error", masterClaw.getClosedLoopError(0));

		// SmartDashboard.putNumber("intake output percent",
		// leftIn.getMotorOutputPercent());
		// SmartDashboard.putNumber("claw output current left",
		// leftIn.getOutputCurrent());
		// SmartDashboard.putNumber("claw output current right",
		// rightIn.getOutputCurrent());

		SmartDashboard.putBoolean("Claw closed ", piston.get().equals(Value.kReverse));
		// SmartDashboard.putBoolean("Claw Open ",
		// !piston.get().equals(Value.kReverse));

		SmartDashboard.putBoolean("Box in", Robot.getSensors().getUltraDistance() < 4);
		// SmartDashboard.putBoolean("Claw in", Robot.getSensors().getUltraDistance() >
		// 4);

	}

	public void runIntake(double speed) {
		
		speed = -speed; //COMP
		double multiplier = .85;
		if (speed < 0) {
			multiplier = 1;
		}
		if ((Math.abs((leftIn.getOutputCurrent() + rightIn.getOutputCurrent()) / 2d) > 15) || stallCounter > 0) {

			if (stallCounter > 0) {
				stallCounter--;
			} else {
				stallCounter = 2;
			}

			System.out.println("STALLING");
			leftIn.set(ControlMode.Current, -Math.signum(speed) * .25);
			rightIn.set(ControlMode.Current, -Math.signum(speed) * .25);
		} else {
			leftIn.set(ControlMode.PercentOutput, speed * multiplier);
			rightIn.set(ControlMode.PercentOutput, speed * multiplier);
		}
	}

	public void runIntakeSide(double speed, boolean side) {
		if (side) {
			rightIn.set(ControlMode.PercentOutput, speed);
		} else {
			leftIn.set(ControlMode.PercentOutput, speed);

		}
	}

	public void close() {
		piston.set(Value.kForward);
	}

	public void open() {
		setIntakeSpeed(0);
		piston.set(Value.kReverse);
	}

	public void toggleClamp() {
		if (piston.get().equals(Value.kForward)) {
			piston.set(Value.kReverse);
		} else if (piston.get().equals(Value.kReverse)) {
			piston.set(Value.kForward);

		} else {
			piston.set(Value.kReverse);
		}
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		close();
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

	public void setIntakeSpeed(double intakeSpeed) {
		this.intakeSpeed = intakeSpeed;
	}

	public double getIntakeSpeed() {
		return intakeSpeed;
	}

	public boolean isDone() {
		System.out.println("Current pos: " + masterClaw.getSelectedSensorPosition(0) + " target: "
				+ masterClaw.getClosedLoopTarget(0));
		return Math.abs(masterClaw.getSelectedSensorPosition(0) - masterClaw.getClosedLoopTarget(0)) < 50;
	}
}

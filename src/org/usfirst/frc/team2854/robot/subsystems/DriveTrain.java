package org.usfirst.frc.team2854.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team2854.PID.DummyPIDOutput;
import org.usfirst.frc.team2854.PID.PIDConstant;
import org.usfirst.frc.team2854.PID.PIDUtil;
import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem implements Restartabale {

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

	private TalonSRX leftT1, leftT2, rightT1, rightT2;

	private DummyPIDOutput turnOut;
	private PIDController turnController;

	private boolean side = false;

	private DoubleSolenoid shifter;

	private GearState gear;

	enum GearState {
		LOW, HIGH, UNKNOWN;

		public static Value gearToValue(GearState state) {
			switch (state) {
			case HIGH:
				return Value.kForward;
			case LOW:
				return Value.kReverse;
			case UNKNOWN:
			default:
				return Value.kOff;
			}
		}

		public static GearState valueToGear(Value value) {
			switch (value) {
			case kReverse:
				return GearState.LOW;
			case kOff:
				return GearState.UNKNOWN;
			case kForward:
				return GearState.HIGH;
			default:
				return GearState.UNKNOWN;

			}
		}
	}

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

		shifter = new DoubleSolenoid(RobotMap.shifterUp, RobotMap.shifterDown);

		PIDUtil.configureTalon(leftT2, !side);
		PIDUtil.configureTalon(rightT2, !side);

		gear = GearState.UNKNOWN;

		// initTurnPID(P_Turn, I_Turn, D_Turn, F_Turn);

		leftT1.set(ControlMode.Follower, leftT2.getDeviceID());
		rightT1.set(ControlMode.Follower, rightT2.getDeviceID());

		// PIDConstant.startSmartDashboardInput(PIDConstant.highDrive, leftT2, rightT2);

	}

	public void enable() {
		System.out.println("Enabling drive train");
		// turnController.enable();
		applyShift(GearState.LOW, -10); // TODO 10 more attempts because of start up time?
	}

	public void disable() {
		System.out.println("Disableing drive train");
		// turnController.disable();
	}

	public void initTurnPID(double P, double I, double D, double F) {
		PIDSource turnSource = new PIDSource() {

			private PIDSourceType type;

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				this.type = pidSource;
			}

			@Override
			public double pidGet() {
				// System.out.println("Calling PID get " + Math.random());
				return rightT2.getSelectedSensorVelocity(0) - leftT2.getSelectedSensorVelocity(0);
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return type;
			}
		};

		turnSource.setPIDSourceType(PIDSourceType.kRate);

		turnOut = new DummyPIDOutput();

		turnController = new PIDController(P, I, D, F, turnSource, turnOut);
		turnController.setInputRange(-5000, 5000);
		// turnController.setOutputRange(-Config.targetVel, Config.targetVel);
		turnController.setSetpoint(0);
		turnController.setAbsoluteTolerance(5);
		turnController.enable();

	}

	public void writeToDashBoard() {

		SmartDashboard.putNumber("Left Velocity", leftT2.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Velocity", rightT2.getSelectedSensorVelocity(0));

		// SmartDashboard.putNumber("Velocity Diff",
		// rightT2.getSelectedSensorVelocity(0) - leftT2.getSelectedSensorVelocity(0));
		// SmartDashboard.putNumber("turn output", turnController.get());
		// SmartDashboard.putBoolean("is enabled", turnController.isEnabled());
		// SmartDashboard.putNumber("turn error", turnController.getError());

		SmartDashboard.putNumber("Error Right", rightT2.getClosedLoopError(0));
		SmartDashboard.putNumber("Error Left", leftT2.getClosedLoopError(0));
		SmartDashboard.putNumber("Left Throttle", rightT2.getMotorOutputPercent());
		// SmartDashboard.putNumber("Target", leftT2.getClosedLoopTarget(0));

	}

	private void applyShift(GearState desiredState, int attempt) {
		if (attempt > 10) {
			System.err.println("Shifter is not shifting to " + desiredState + " at attempt " + attempt);
			return;
		}
		shifter.set(GearState.gearToValue(desiredState));

		// Timer.delay(.005);
		if (!shifter.get().equals(GearState.gearToValue(desiredState))) { // did not shift for some reason //TODO check
																			// compressor pressure
			java.util.Timer taskTimer = new java.util.Timer();
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					applyShift(desiredState, attempt + 1);
				}
			};
			taskTimer.schedule(task, 5); // try to shift again in half a second

		} else {
			System.out.println("shifted to " + desiredState);
			gear = desiredState;
			PIDConstant constant = (gear == GearState.HIGH ? PIDConstant.highDrive : PIDConstant.lowDrive);
			PIDUtil.updatePID(rightT2, constant);
			PIDUtil.updatePID(leftT2, constant);
		}

		// P_Drive = .2;
		// I_Drive = .003;
		// D_Drive = 0;
		// low ^ high v
		// P_Drive = .2;
		// I_Drive = .001;
		// D_Drive = .02;
	}

	public void toggleShift() {
		if (gear == GearState.HIGH) {
			applyShift(GearState.LOW, 0);
		} else if (gear == GearState.LOW) {
			applyShift(GearState.HIGH, 0);
		} else {
			System.out.println("In unknown state, defaulting to LOW");
			applyShift(GearState.LOW, 0);
		}

	}

	public void drive(double left, double right, ControlMode mode) {
		// System.out.println(mode.toString() + " " + ControlMode.Velocity + " " +
		// mode.equals(ControlMode.Velocity));
		
		if (mode.equals(ControlMode.Velocity)) {
			if (gear == GearState.LOW) {
				// System.out.println("Using low target");
				left *= Config.lowTarget;
				right *= Config.lowTarget;
			} else {
				// System.out.println("Using high target");
				left *= Config.highTarget;
				right *= Config.highTarget;
			}
		} else if (mode.equals(ControlMode.Position) || mode.equals(ControlMode.MotionMagic)) {
			left += leftT2.getSelectedSensorPosition(0);
			right += rightT2.getSelectedSensorPosition(0);
		}
		// System.out.println(left + " " + right);
		leftT2.set(mode, left * Config.totalDriveSpeedMultiplier);
		rightT2.set(mode, right * Config.totalDriveSpeedMultiplier);

	}

	public void driveStraight(double left, double right, ControlMode mode) {
		double output = turnController.get();
		System.out.println(output);
		turnController.setSetpoint(0);
	}

	public void drive(double left, double right) {
		drive(left, right, ControlMode.PercentOutput);
	}

	public void stop() {
		drive(0, 0);
	}
	
	public double getAvgEncoder() {
		return (rightT2.getSelectedSensorPosition(0)  + leftT2.getSelectedSensorPosition(0))/2d;
	}

}

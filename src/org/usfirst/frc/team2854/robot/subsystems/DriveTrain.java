package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.TimerTask;

import org.usfirst.frc.team2854.PID.DummyPIDOutput;
import org.usfirst.frc.team2854.PID.PIDConstant;
import org.usfirst.frc.team2854.PID.PIDUtil;

/** */
public class DriveTrain extends Subsystem implements Restartable, PowerSaver {

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

	private TalonSRX leftT1, leftT2, rightT1, rightT2;

	private DummyPIDOutput turnOut;
	private PIDController turnController;

	private boolean side = false;

	private DoubleSolenoid shifter;

	private GearState gear;

	private boolean autoShift = true;

	private double driveMultiplier = 1;

	public enum GearState {
		SLOW, FAST, UNKNOWN;

		public static Value gearToValue(GearState state) {
			switch (state) {
			case SLOW:
				return Value.kForward;
			case FAST:
				return Value.kReverse;
			case UNKNOWN:
			default:
				return Value.kOff;
			}
		}

		public static GearState valueToGear(Value value) {
			switch (value) {
			case kReverse:
				return GearState.FAST;
			case kOff:
				return GearState.UNKNOWN;
			case kForward:
				return GearState.SLOW;
			default:
				return GearState.UNKNOWN;
			}
		}
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	
	public DriveTrain() {
		leftT2 = new TalonSRX(RobotMap.leftTalonID1);
		leftT2.setInverted(side);

		leftT1 = new TalonSRX(RobotMap.leftTalonID2);
		leftT1.setInverted(side);

		rightT2 = new TalonSRX(RobotMap.rightTalonID1);
		rightT2.setInverted(!side);

		rightT1 = new TalonSRX(RobotMap.rightTalonID2);
		rightT1.setInverted(!side);

		shifter = new DoubleSolenoid(RobotMap.shifterUp, RobotMap.shifterDown);

		PIDUtil.configureTalon(leftT2, !side);
		PIDUtil.configureTalon(rightT2, !side);

		gear = GearState.UNKNOWN;

		// initTurnPID(P_Turn, I_Turn, D_Turn, F_Turn);

		leftT1.set(ControlMode.Follower, leftT2.getDeviceID());
		rightT1.set(ControlMode.Follower, rightT2.getDeviceID());

		// PIDConstant.startSmartDashboardInput(PIDConstant.highDrive, leftT2, rightT2);

		NeutralMode mode = NeutralMode.Coast;

		leftT1.setNeutralMode(mode);
		leftT2.setNeutralMode(mode);
		rightT1.setNeutralMode(mode);
		rightT2.setNeutralMode(mode);

		leftT1.configOpenloopRamp(.05d, 10);
		leftT2.configOpenloopRamp(.05d, 10);
		rightT1.configOpenloopRamp(.05d, 10);
		rightT2.configOpenloopRamp(.05d, 10);

		
		// PIDConstant.startSmartDashboardInput(PIDConstant.fastDrive, leftT2, rightT2);

	}

	public void enable() {
		// System.out.println("Enabling drive train");
		// turnController.enable();
		applyShift(GearState.SLOW, -10); // TODO 10 more attempts because of start up time?
	}

	public void disable() {
		// System.out.println("Disableing drive train");
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
				return rightT2.getSelectedSensorPosition(0) - leftT2.getSelectedSensorPosition(0);
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return type;
			}
		};

		turnSource.setPIDSourceType(PIDSourceType.kDisplacement);

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

		// SmartDashboard.putBoolean("reset during en?", new
		// StickyFaults().ResetDuringEn);
		// SmartDashboard.putNumber("encoder diff",
		// Math.abs(rightT2.getSelectedSensorPosition(0) -
		// leftT2.getSelectedSensorPosition(0)));

		SmartDashboard.putString("Gear", gear.toString());

		SmartDashboard.putNumber("average Pos", getAvgEncoder());

		SmartDashboard.putNumber("acumelatpr", leftT2.getIntegralAccumulator(0));

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
			PIDConstant constant = (gear == GearState.SLOW ? PIDConstant.slowDrive : PIDConstant.fastDrive);
			PIDUtil.updatePID(rightT2, constant);
			PIDUtil.updatePID(leftT2, constant);
		}

		// P_Drive = .2;
		// I_Drive = .003;
		// D_Drive = 0;
		// low ^ high vs
		// P_Drive = .2;
		// I_Drive = .001;
		// D_Drive = .02;
	}

	public void toggleShift() {
		if (gear == GearState.SLOW) {
			applyShift(GearState.FAST, 0);
		} else if (gear == GearState.FAST) {
			applyShift(GearState.SLOW, 0);
		} else {
			System.out.println("In unknown state, defaulting to LOW");
			applyShift(GearState.FAST, 0);
		}

	}

	public void shiftFast() {

		applyShift(GearState.FAST, 0);

	}

	public void shiftSlow() {

		applyShift(GearState.SLOW, 0);

	}

	public void drive(double left, double right, ControlMode mode) {
		// System.out.println(mode.toString() + " " + ControlMode.Velocity + " " +
		// mode.equals(ControlMode.Velocity));
		SmartDashboard.putNumber("left output Init", left);
		SmartDashboard.putNumber("right output Init", right);

		if (mode.equals(ControlMode.PercentOutput) || mode.equals(ControlMode.Velocity)) {
			left *= driveMultiplier;
			right *= driveMultiplier;
		}

		if (mode.equals(ControlMode.Velocity) || mode.equals(ControlMode.MotionMagic)
				|| mode.equals(ControlMode.Position)) {

			left *= getDriveConstant();
			right *= getDriveConstant();
		}
		if (mode.equals(ControlMode.Position) || mode.equals(ControlMode.MotionMagic)) {
			left += leftT2.getSelectedSensorPosition(0);
			right += rightT2.getSelectedSensorPosition(0);
		}
		// System.out.println("target left + " + left);
		// System.out.println(left + " " + right);

		SmartDashboard.putNumber("left output", left);
		SmartDashboard.putNumber("right output", right);

		SmartDashboard.putNumber("Ltrigger", OI.mainJoystick.getRawAxis(2));
		SmartDashboard.putNumber("Rtrigger", OI.mainJoystick.getRawAxis(3));
		SmartDashboard.putNumber("turn ", OI.mainJoystick.getRawAxis(0));

		leftT2.set(mode, left * Config.totalDriveSpeedMultiplier);
		rightT2.set(mode, right * Config.totalDriveSpeedMultiplier);

	}

	/**
	 * UNIMPLEMENTED
	 * 
	 * @param left
	 * @param right
	 * @param mode
	 */
	public void driveStraight(double left, double right, ControlMode mode) {
		// double error = rightT2.getSelectedSensorPosition(0) -
		// leftT2.getSelectedSensorPosition(0);
		drive(left, right, mode);
	}

	public void setNeutralMode(NeutralMode mode) {
		
		rightT1.setNeutralMode(mode);
		rightT2.setNeutralMode(mode);
		
		leftT1.setNeutralMode(mode);
		leftT2.setNeutralMode(mode);

	}

	public void drive(double left, double right) {
		drive(left, right, ControlMode.PercentOutput);

	}

	public void stop() {
		drive(0, 0);
	}

	public double getAvgEncoder() {
		return (rightT2.getSelectedSensorPosition(0) + leftT2.getSelectedSensorPosition(0)) / 2d;
	}

	public double getLeftEncoder() {
		return leftT2.getSelectedSensorPosition(0);
	}

	public double getRightEncoder() {
		return rightT2.getSelectedSensorPosition(0);
	}

	public double getAvgVelocity() {
		return (rightT2.getSelectedSensorVelocity(0) + leftT2.getSelectedSensorVelocity(0)) / 2d;
	}

	public double inchesToCycles(double d) { // TODO finish this
		if (gear == GearState.SLOW) {
			return (d + .997) / 6.96d;
		} else {
			throw new RuntimeException("Need to calibrate distance for " + gear.toString());
		}
	}

	public boolean isAutoShift() {
		return autoShift;
	}

	public void setAutoShift(boolean autoShift) {
		this.autoShift = autoShift;
	}

	public double getDriveConstant() {
		return (gear == GearState.SLOW ? Config.slowTarget : Config.fastTarget);
	}

	@Override
	public void savePower() {
		// TODO Auto-generated method stub

	}

	@Override
	public void normalPower() {
		// TODO Auto-generated method stub

	}

	public double getDriveMultiplier() {
		return driveMultiplier;
	}

	public void setDriveMultiplier(double driveMultiplier) {
		this.driveMultiplier = driveMultiplier;
	}

}

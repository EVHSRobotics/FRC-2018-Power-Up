package org.usfirst.frc.team2854.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.DummyPIDOutput;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;

/** */
public class DriveTrain extends Subsystem implements Restartabale {

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

  private TalonSRX leftT1, leftT2, rightT1, rightT2;

  private DummyPIDOutput turnOut;
  private PIDController turnController;

  private boolean side = false;

  private DoubleSolenoid shifter;

  public void initDefaultCommand() {
    setDefaultCommand(new JoystickDrive());
  }

  public void enable() {
    System.out.println("Enabling drive train");
    turnController.enable();

    Value startingGear = Value.kForward;

    shifter.set(startingGear);
    shift(startingGear);
  }

  public void disable() {
    System.out.println("Disableing drive train");
    turnController.disable();
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

    final double P_Drive = .28;
    final double I_Drive = .0006;
    final double D_Drive = .8;
    final double F_Drive = 0.1217;

    final double P_Turn = .0002;
    final double I_Turn = .000002;
    final double D_Turn = .2;
    final double F_Turn = 0;

    configureTalon(leftT2, P_Drive, I_Drive, D_Drive, F_Drive, !side);
    configureTalon(rightT2, P_Drive, I_Drive, D_Drive, F_Drive, !side);

    initTurnPID(P_Turn, I_Turn, D_Turn, F_Turn);

    leftT1.set(ControlMode.Follower, leftT2.getDeviceID());
    rightT1.set(ControlMode.Follower, rightT2.getDeviceID());
  }

  public void shift(DoubleSolenoid.Value value) {

    double P_Drive = .28;
    double I_Drive = .0006;
    double D_Drive = .8;
    double F_Drive = 0.1217;

    if (value.equals(DoubleSolenoid.Value.kForward)) {
      System.out.println("Using low values");
      P_Drive = .6;
      I_Drive = .00001;
      D_Drive = 0;
      F_Drive = 1023 / Config.lowTarget;
    } else {
      System.out.println("Using high values");
      P_Drive = .2;
      I_Drive = .001;
      D_Drive = .02;
      F_Drive = 1023 / Config.highTarget;
    }
    updatePID(rightT2, P_Drive, I_Drive, D_Drive, F_Drive);
    updatePID(leftT2, P_Drive, I_Drive, D_Drive, F_Drive);
  }

  private void updatePID(TalonSRX talon, double P, double I, double D, double F) {
    final int timeOutConstant = 10;
    final int PIDIndex = 0;
    talon.config_kP(PIDIndex, P, timeOutConstant);
    talon.config_kI(PIDIndex, I, timeOutConstant);
    talon.config_kD(PIDIndex, D, timeOutConstant);
    talon.config_kF(PIDIndex, F, timeOutConstant);
  }

  private void configureTalon(
      TalonSRX talon, double P, double I, double D, double F, boolean side) {

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

    // talon.configMotionCruiseVelocity(8400, 10);
    // talon.configMotionAcceleration(8400, 10);

    // int absolutePosition = talon.getSelectedSensorPosition(timeOutConstant) &
    // 0xFFF;
    talon.setSelectedSensorPosition(0, PIDIndex, timeOutConstant);

    talon.configVoltageCompSaturation(12, 10);
  }

  public void initTurnPID(double P, double I, double D, double F) {
    PIDSource turnSource =
        new PIDSource() {

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
    //		SmartDashboard.putNumber("turn output", turnController.get());
    //		SmartDashboard.putBoolean("is enabled", turnController.isEnabled());
    //		SmartDashboard.putNumber("turn error", turnController.getError());

    SmartDashboard.putNumber("Error", rightT2.getClosedLoopError(0));
  }

  public void toggleShift() {
    if (shifter.get() == Value.kForward) {
      shifter.set(Value.kReverse);
    } else if (shifter.get() == Value.kReverse) {
      shifter.set(Value.kForward);
    } else {
      System.out.println("Weird state " + shifter.get().toString() + " defualting to forward");
      shifter.set(Value.kForward);
    }
    System.out.println("shifting to " + shifter.get().toString());
    shift(shifter.get());
  }

  public void drive(double left, double right, ControlMode mode) {

    if (mode.equals(ControlMode.Velocity)) {
      if (shifter.get().equals(Value.kForward)) {
        left *= Config.highTarget;
        right *= Config.highTarget;
      } else {
        left *= Config.lowTarget;
        right *= Config.lowTarget;
      }
    }

    leftT2.set(mode, left * Config.totalDriveSpeedMultiplier);
    rightT2.set(mode, right * Config.totalDriveSpeedMultiplier);
  }

  public void driveStraight(double left, double right, ControlMode mode) {
    double output = turnController.get();
    System.out.println(output);
    // drive(left - (output * Config.targetVel / 2d), right, mode);
    turnController.setSetpoint(0);
  }

  public void drive(double left, double right) {
    drive(left, right, ControlMode.PercentOutput);
  }

  public void stop() {
    drive(0, 0);
  }
>>>>>>> 033c7217ed4fb22308945d4d081b45eb7cca8acd
}

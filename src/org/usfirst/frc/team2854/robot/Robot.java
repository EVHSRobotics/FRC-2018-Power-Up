package org.usfirst.frc.team2854.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import java.util.HashMap;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Restartable;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

  public static OI oi;
  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();
  private static HashMap<SubsystemNames, Subsystem> subsystems;
  private SensorBoard sensors;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    System.out.println("STARTING");
    subsystems = new HashMap<SubsystemNames, Subsystem>();
    subsystems.put(SubsystemNames.DRIVE_TRAIN, new DriveTrain());

    // sensors = new SensorBoard();

    for (Subsystem s : subsystems.values()) {
      if (s instanceof Restartable) {
        ((Restartable) s).enable();
      }
    }
  }
  /**
   * This function is called once each time the robot enters Disabled mode. You can use it to reset
   * any subsystem information you want to clear when the robot is disabled.
   */
  @Override
  public void disabledInit() {
    for (Subsystem s : subsystems.values()) {
      if (s instanceof Restartable) {
        ((Restartable) s).disable();
      }
    }
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the chooser code above
   * (like the commented example) or additional comparisons to the switch structure below with
   * additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    for (Subsystem s : subsystems.values()) {
      if (s instanceof Restartable) {
        ((Restartable) s).enable();
      }
    }
  }

  /** This function is called periodically during autonomous */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    for (Subsystem s : subsystems.values()) {
      if (s instanceof Restartable) {
        ((Restartable) s).enable();
      }
    }

    // OI.buttonA.whenPressed(new DriveMotionMagik());

  }

  /** s This function is called periodically during operator control */
  @Override
  public void teleopPeriodic() {

    ((DriveTrain) getSubsystem(SubsystemNames.DRIVE_TRAIN)).writeToDashBoard();
    //		double angle = sensors.getNavX().getAngle();
    //		while(angle < 0) {
    //			angle += 360;
    //		}
    //		SmartDashboard.putNumber("Gyro", angle % 360);

    Scheduler.getInstance().run();
  }

  /** This function is called periodically during test mode */
  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }

  public static Subsystem getSubsystem(SubsystemNames name) {
    return subsystems.get(name);
  }

  public SensorBoard getSensors() {
    return sensors;
  }
}

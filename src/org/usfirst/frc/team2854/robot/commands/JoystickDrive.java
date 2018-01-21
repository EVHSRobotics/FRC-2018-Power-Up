package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

/** */
public class JoystickDrive extends Command {

  private DriveTrain drive;

  public JoystickDrive() {
    requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    drive.drive(
        OI.joystick.getRawAxis(1) * Config.manuelSpeedMultiplier,
        OI.joystick.getRawAxis(5) * Config.manuelSpeedMultiplier);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    drive.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    drive.stop();
  }
}

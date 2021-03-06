package org.usfirst.frc.team2854.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

/** */
public class DriveThottle extends Command {

  private double percent;
  private DriveTrain drive;

  public DriveThottle(double percent) {
    requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
    this.percent = percent;
  }

  // Called just before this Command runs the first time
  protected void initialize() {
    drive = ((DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
  }

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    System.out.println("Driving");
    drive.drive(percent, percent, ControlMode.Velocity);
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  protected void end() {
    System.out.println("Stopping");
    drive.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {
    System.out.println("Interupted");
    drive.stop();
  }
}

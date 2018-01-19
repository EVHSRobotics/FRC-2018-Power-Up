package org.usfirst.frc.team2854.robot.commands;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/** */
public class RunAllTalons extends Command {

  public RunAllTalons() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  protected void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  protected void execute() {
    int i;
    for (i = 0; i < 6; i++) {
      try {
        CANTalon talon = new CANTalon(i);
        talon.set(1);
        Timer.delay(2);
        talon.set(0);
        System.out.println("Talon #" + i + " alive");
      } catch (Exception e) {
        System.out.println("Talon #" + i + " died");
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  protected void end() {}

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  protected void interrupted() {}
}

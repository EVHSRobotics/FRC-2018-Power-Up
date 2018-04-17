package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.OI;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDriveClaw extends Command {

	private Claw claw;

	public JoystickDriveClaw() {
		requires(Robot.getSubsystem(SubsystemNames.CLAW));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		claw = (Claw) Robot.getSubsystem(SubsystemNames.CLAW);
	}

	// 0,500,3750,4500
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double value = -(OI.thirdJoystick.getRawAxis(5));
		value = Math.abs(value) < .05 ? 0 : value;
		//if (claw.getClawPos() > Config.upperClawLimit && value < 0) {
		//	value = 0;
		//}
		if(Math.abs(claw.getClawPos()) > 4500 && value < 0) {
			value = 0;
		}
		double holdValue = (Robot.getSensors().getUltraDistance() > 3 ? -.085 : -.085);
		claw.driveClaw((value * .65 - holdValue), ControlMode.PercentOutput);
		
		double clawValue = OI.thirdJoystick.getRawAxis(2) - OI.thirdJoystick.getRawAxis(3);
		clawValue = Math.abs(clawValue) < .05 ? claw.getIntakeSpeed() : clawValue * 1d;
		clawValue = clawValue < 0 ? clawValue : clawValue * .75;
		//System.out.println(clawValue);
		claw.runIntake(clawValue);
		
		

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		claw.driveClaw(0, ControlMode.PercentOutput);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		claw.driveClaw(0, ControlMode.PercentOutput);

	}
}

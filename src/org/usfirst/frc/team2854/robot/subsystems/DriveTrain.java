package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Drivable{

	// Put methods for controlling this subsyWstem
	// here. Call these from Commands.

	private CANTalon leftT1, leftT2, rightT1, rightT2;
	private boolean side = false;


	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	public DriveTrain() {
		leftT1 = new CANTalon(RobotMap.leftTalonID1);
		leftT1.setInverted(side);
		
		leftT2 = new CANTalon(RobotMap.leftTalonID2);
		leftT2.setInverted(side);

		rightT1 = new CANTalon(RobotMap.rightTalonID1);
		rightT1.setInverted(!side);

		rightT2 = new CANTalon(RobotMap.rightTalonID2);
		rightT2.setInverted(!side);

	}

	public void drive(double left, double right) {

		leftT1.set(left * Config.totalDriveSpeedMultiplier);
		leftT2.set(left * Config.totalDriveSpeedMultiplier);
		rightT1.set(right * Config.totalDriveSpeedMultiplier);
		rightT2.set(right * Config.totalDriveSpeedMultiplier);
	}


	public void stop() {
		leftT1.set(0);
		leftT2.set(0);
		rightT1.set(0);
		rightT2.set(0);
	}

	public double getEncoder() {
		return (leftT1.getEncPosition() + rightT1.getEncPosition())/2d;
	}

}

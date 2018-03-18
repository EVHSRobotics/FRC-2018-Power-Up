package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDriveClimb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climb extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	TalonSRX climb1, climb2;
	private double speed;
	public Climb() {
		climb1 = new TalonSRX(RobotMap.climb1);
		climb2 = new TalonSRX(RobotMap.climb2);
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new JoystickDriveClimb());
    }
    
    public void drive(double speed) {
//    	this.speed = speed;
    	//System.out.println("Speed "  + speed);
//    	climb1.set(this.speed);
    	
//    	climb2.set(this.speed);
    	climb1.set(ControlMode.PercentOutput, speed);
    	climb2.set(ControlMode.PercentOutput, speed);
    	//System.out.println("Climb1: " + climb1.get());
    	//climb2.set(speed);
    }
    
    public double getSpeed() {
    	return speed;
    	
    }
    
    public void writeToDashboard() {
    	//SmartDashboard.putBoolean("Climb Deployed", getSpeed() > 0);
    }
}


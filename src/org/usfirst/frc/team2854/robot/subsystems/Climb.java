package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDriveClimb;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climb extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Spark climb1, climb2;
	double speed;
	public Climb() {
		climb1 = new Spark(RobotMap.climb1);
		climb2 = new Spark(RobotMap.climb2);
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new JoystickDriveClimb());
    }
    
    public void drive(double speed) {
    	this.speed = speed;
    	System.out.println("Speed");
    	climb1.set(this.speed);
    	climb2.set(this.speed);
    }
    
    public double getSpeed() {
    	return speed;
    	
    }
    
    public void writeToDashboard() {
    	SmartDashboard.putBoolean("Climb Deployed", getSpeed() > 0);
    }
}


package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;
import org.usfirst.frc.team2854.robot.commands.JoystickDriveClimb;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climb extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Spark climb1, climb2;
	
	public Climb() {
		climb1 = new Spark(RobotMap.climb1);
		climb2 = new Spark(RobotMap.climb2);
	}
	
    public void initDefaultCommand() {
       setDefaultCommand(new JoystickDriveClimb());
    }
    
    public void drive(double speed) {
    	System.out.println("Speed");
    	climb1.set(speed);
    	climb2.set(speed);
    }
}


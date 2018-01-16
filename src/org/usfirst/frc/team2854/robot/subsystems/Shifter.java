package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifter extends Subsystem {

	private DoubleSolenoid shifter;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Shifter() {
    	shifter = new DoubleSolenoid(RobotMap.shifterUp, RobotMap.shifterDown);
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
	}
}


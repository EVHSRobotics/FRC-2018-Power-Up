package org.usfirst.frc.team2854.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoIntake extends CommandGroup {

    public AutoIntake() {
    	
    	
    	addSequential(new SetClamp(true));
    	addSequential(new ClawSetpoint(-4250));
    	addSequential(new IntakeTillUltra(8.5, -.4, false));
    	addSequential(new SetClamp(false));
    	addSequential(new IntakeTillUltra(2, -.9, true));
    	addSequential(new ClawSetpoint(-1250));

    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}

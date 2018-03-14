package org.usfirst.frc.team2854.robot;

import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.DriveThottle;
import org.usfirst.frc.team2854.robot.commands.DriveToBox;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.FloorAngle;
import org.usfirst.frc.team2854.robot.commands.IntakeAquire;
import org.usfirst.frc.team2854.robot.commands.Outtake;
import org.usfirst.frc.team2854.robot.commands.ScaleAngle;
import org.usfirst.frc.team2854.robot.commands.Spit;
import org.usfirst.frc.team2854.robot.commands.SwitchAngle;
import org.usfirst.frc.team2854.robot.commands.ToggleClamp;
import org.usfirst.frc.team2854.robot.commands.ToggleShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick mainJoystick = new Joystick(0);
	public static Joystick secondaryJoystick = new Joystick(1);
	//0,500,3750,4500
	public static JoystickButton buttonA1 = new JoystickButton(mainJoystick, 1);
	public static JoystickButton buttonB1 = new JoystickButton(mainJoystick, 2);
	public static JoystickButton buttonX1 = new JoystickButton(mainJoystick, 3);
	public static JoystickButton buttonY1 = new JoystickButton(mainJoystick, 4);
	public static JoystickButton lTrigger1 = new JoystickButton(mainJoystick, 5);
	public static JoystickButton rTrigger1 = new JoystickButton(mainJoystick, 6);

	public static JoystickButton buttonA2 = new JoystickButton(secondaryJoystick, 1);
	public static JoystickButton buttonB2 = new JoystickButton(secondaryJoystick, 2);
	public static JoystickButton buttonX2 = new JoystickButton(secondaryJoystick, 3);
	public static JoystickButton buttonY2 = new JoystickButton(secondaryJoystick, 4);
	public static JoystickButton lTrigger2 = new JoystickButton(secondaryJoystick, 5);
	public static JoystickButton rTrigger2 = new JoystickButton(secondaryJoystick, 6);
	public static JoystickButton rightStickButton2 = new JoystickButton(secondaryJoystick, 10);

	// rishi drive
	// B toggle intake
	// hold A intake
	// hold X out

	// second controler
	// triggers climb
	// 1 elevator Y B A elevator set point
	// 5 claw up down

	static {

		// Actual Robo code VVV

//		buttonX2.whenPressed(new ToggleClamp());
//
//		buttonA1.whenPressed(new IntakeAquire());
		rTrigger1.whenPressed(new ToggleShift());
//
//		 buttonA2.whenPressed(new FloorAngle());
//		 buttonB2.whenPressed(new SwitchAngle());
//		 buttonY2.whenPressed(new ScaleAngle());
		
		buttonA1.whenPressed(new DriveToBox());
		//buttonX1.whenPressed(new DriveThottle(.5));
		//Practice bot robo code VVV
		
		buttonX2.whenPressed(new AutoIntake());
		
		//-4k,-2k,-750
		buttonA2.whenPressed(new FloorAngle());
		buttonB2.whenPressed(new SwitchAngle());
		buttonY2.whenPressed(new ScaleAngle());
		
		lTrigger2.whenPressed(new ToggleClamp());
		rTrigger2.whenPressed(new Spit());
		
		rightStickButton2.whenPressed(new ClawSetpoint(-2000));
		//buttonY1.whenPressed(new ElevatorSetPoint(-22000));
		//buttonA1.whenPressed(new ElevatorSetPoint(-5400));
		//buttonB1.whenPressed(new ElevatorSetPoint(-600));

		// Auto Code tsting stuff VVVVVVV

//		 buttonA.whenPressed(new DriveToPos(0));
//		 buttonB.whenPressed(new DriveToPos(-10000));
//		 buttonY.whenPressed(new DriveToPos(-20000));
		// OI.buttonX.whenPressed(new DriveThottle(.5));
		// OI.rTrigger.whenPressed(new ToggleShift());
		// OI.buttonB.whenPressed(new ChangeLed());
		//
		
		
		//OI.buttonA2.whenPressed(new DriveNearNear());
		
		
		// OI.buttonY.whenPressed(new DriveFarNear());

	}

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}

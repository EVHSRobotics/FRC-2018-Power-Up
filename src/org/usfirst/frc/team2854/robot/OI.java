package org.usfirst.frc.team2854.robot;

import org.usfirst.frc.team2854.robot.commands.DriveMotionMagik;
import org.usfirst.frc.team2854.robot.commands.DriveToBox;
import org.usfirst.frc.team2854.robot.commands.GyroTurn;
import org.usfirst.frc.team2854.robot.commands.ToggleShift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick joystick = new Joystick(0);	
	public static JoystickButton buttonA = new JoystickButton(joystick, 1);
	public static JoystickButton buttonB = new JoystickButton(joystick, 2);
	public static JoystickButton buttonX = new JoystickButton(joystick, 3);
	public static JoystickButton rTrigger = new JoystickButton(joystick, 6);

	static {

		OI.buttonA.whenPressed(new DriveToBox(0));
		OI.buttonB.whenPressed(new DriveMotionMagik(175));
		OI.buttonX.whenPressed(new DriveToBox(0));
		OI.rTrigger.whenPressed(new ToggleShift());
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

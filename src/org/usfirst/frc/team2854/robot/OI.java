package org.usfirst.frc.team2854.robot;

import java.util.function.Supplier;

import org.usfirst.frc.team2854.robot.commands.AutoCenterBlock;
import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.CenterBlock;
import org.usfirst.frc.team2854.robot.commands.ClawSetpoint;
import org.usfirst.frc.team2854.robot.commands.DriveThottle;
import org.usfirst.frc.team2854.robot.commands.DriveToBox;
import org.usfirst.frc.team2854.robot.commands.ElevatorSetPoint;
import org.usfirst.frc.team2854.robot.commands.EncoderTurn;
import org.usfirst.frc.team2854.robot.commands.FloorAngle;
import org.usfirst.frc.team2854.robot.commands.IntakeAquire;
import org.usfirst.frc.team2854.robot.commands.Outtake;
import org.usfirst.frc.team2854.robot.commands.ScaleAngle;
import org.usfirst.frc.team2854.robot.commands.Shift;
import org.usfirst.frc.team2854.robot.commands.Spit;
import org.usfirst.frc.team2854.robot.commands.SwitchAngle;
import org.usfirst.frc.team2854.robot.commands.ToggleClamp;
import org.usfirst.frc.team2854.robot.commands.ToggleShift;
import org.usfirst.frc.team2854.robot.commands.VisionPickup;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain.GearState;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static Joystick mainJoystick = new Joystick(0);
	public static Joystick secondaryJoystick = new Joystick(1);
	public static Joystick thirdJoystick = new Joystick(2);
	// 0,500,3750,4500
	public static JoystickButton buttonA1 = new JoystickButton(mainJoystick, 1);
	public static JoystickButton buttonB1 = new JoystickButton(mainJoystick, 2);
	public static JoystickButton buttonX1 = new JoystickButton(mainJoystick, 3);
	public static JoystickButton buttonY1 = new JoystickButton(mainJoystick, 4);
	public static JoystickButton lTrigger1 = new JoystickButton(mainJoystick, 5);
	public static JoystickButton rTrigger1 = new JoystickButton(mainJoystick, 6);
	public static JoystickButton button7_1 = new JoystickButton(mainJoystick, 7);

	public static JoystickButton buttonA2 = new JoystickButton(secondaryJoystick, 1);
	public static JoystickButton buttonB2 = new JoystickButton(secondaryJoystick, 2);
	public static JoystickButton buttonX2 = new JoystickButton(secondaryJoystick, 3);
	public static JoystickButton buttonY2 = new JoystickButton(secondaryJoystick, 4);
	public static JoystickButton lTrigger2 = new JoystickButton(secondaryJoystick, 5);
	public static JoystickButton rTrigger2 = new JoystickButton(secondaryJoystick, 6);
	public static JoystickButton rightStickButton2 = new JoystickButton(secondaryJoystick, 10);

	public static Supplier<Double> throttle;
	public static Supplier<Double> turn;

	static {
		
		buttonA1.whenPressed(new VisionPickup());
		
		button7_1.whenPressed(new ToggleClamp());
		(new JoystickButton(thirdJoystick, 6)).whenPressed(new ToggleClamp());
		buttonA2.whenPressed(new AutoIntake());
		// buttonY1.whenPressed(new ToggleShift());
		// rTrigger1.whenPressed(new ToggleShift());

		// buttonX1.whenPressed(new Shift(GearState.FAST));
		// buttonY1.whenPressed(new Shift(GearState.SLOW));

		// buttonB1.whenPressed(new AutoCenterBlock());

		// buttonX2.whenPressed(new EncoderTurn(150));
		// buttonY2.whenPressed(new EncoderTurn(-150));
		// buttonX2.whenPressed(new AutoIntake());

		// buttonA2.whenPressed(new FloorAngle());
		// buttonB2.whenPressed(new SwitchAngle());
		// buttonY2.whenPressed(new ScaleAngle());

		// lTrigger2.whenPressed(new ToggleClamp());
		// rTrigger2.whenPressed(new Spit());

		// rightStickButton2.whenPressed(new ClawSetpoint(-2000));

		throttle = () -> secondaryJoystick.getRawAxis(1);
		turn = () -> mainJoystick.getRawAxis(0);

	}
}

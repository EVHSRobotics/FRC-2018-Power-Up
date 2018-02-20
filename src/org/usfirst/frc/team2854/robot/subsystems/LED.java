package org.usfirst.frc.team2854.robot.subsystems;

import org.usfirst.frc.team2854.robot.RobotMap;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LED extends Subsystem implements Restartable {

	private Relay led;	

	private Notifier notifier;

	private Mode mode;

	public LED() {
		led = new Relay(RobotMap.led);
		led.setDirection(Direction.kBoth);

		notifier = new Notifier(() -> {
			if (led.get().equals(Relay.Value.kReverse)) {
				led.set(Relay.Value.kOff);
			} else if (led.get().equals(Relay.Value.kOff)) {
				led.set(Relay.Value.kReverse);
			}
		});

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public enum Mode {
		ON, OFF, BLINK
	}

	public void setMode(Mode mode) {
		System.out.println(mode.toString());
		switch (mode) {
		case BLINK:
			notifier.startPeriodic(.5);
			this.mode = mode;
			break;
		case OFF:
			notifier.stop();
			led.set(Relay.Value.kOff);
			this.mode = mode;
			break;
		case ON:
			notifier.stop();
			led.set(Relay.Value.kReverse);
			this.mode = mode;
			break;
		default:
			break;

		}
	}

	public void toggleMode() {
		if (mode.equals(Mode.BLINK)) {
			setMode(Mode.OFF);
		} else if (mode.equals(Mode.OFF)) {
			setMode(Mode.ON);
		} else if (mode.equals(Mode.ON)) {
			setMode(Mode.BLINK);
		}
	}

	@Override
	public void enable() {

		setMode(Mode.OFF);
	}
 
	@Override
	public void disable() {
		setMode(Mode.OFF);

	}

}

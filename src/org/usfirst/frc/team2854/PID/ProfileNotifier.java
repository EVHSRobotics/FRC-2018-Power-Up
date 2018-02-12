package org.usfirst.frc.team2854.PID;

import org.usfirst.frc.team2854.robot.Config;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ProfileNotifier {

	boolean shouldEnable;
	Notifier notifier;
	int points = 0;
	final int minPoints = 20;
	private TalonSRX leftT, rightT;
	double updateTime;

	double leftStart, leftTarget, rightStart, rightTarget;

	private boolean enabled = false;

	public ProfileNotifier(double updateTime, double leftTarget, double rightTarget, TalonSRX leftT, TalonSRX rightT) {
		this.leftT = leftT;
		this.rightT = rightT;
		this.updateTime = updateTime;
		leftT.changeMotionControlFramePeriod((int) (updateTime / 2));
		rightT.changeMotionControlFramePeriod((int) (updateTime / 2));
		leftStart = leftT.getSelectedSensorPosition(0);
		rightStart = rightT.getSelectedSensorPosition(0);
		this.leftTarget = leftTarget;
		this.rightTarget = rightTarget;
	}

	public void addPoint() {
		points++;
		if (points >= minPoints) {
			shouldEnable = true;
		}
	}

	public boolean shouldEnable() {
		return shouldEnable;
	}

	public void startNotifier() {
		notifier = new Notifier(() -> {
			rightT.processMotionProfileBuffer();
			leftT.processMotionProfileBuffer();
		});
		notifier.startPeriodic(updateTime / 2d / 1000d);
	}

	public void stopNotifier() {
		notifier.stop();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isFinished() {
		MotionProfileStatus s1 = new MotionProfileStatus();
		MotionProfileStatus s2 = new MotionProfileStatus();

		rightT.getMotionProfileStatus(s1);
		leftT.getMotionProfileStatus(s2);
		SmartDashboard.putNumber("error", Math.abs(rightT.getSelectedSensorPosition(0) - rightStart - rightTarget));
		System.out.println(Math.abs(rightT.getSelectedSensorPosition(0) - rightStart - rightTarget));
		return Math.abs(rightT.getSelectedSensorPosition(0) - rightStart
				- rightTarget) < Config.driveEncoderCyclesPerRevolution
				&& Math.abs(leftT.getSelectedSensorPosition(0) - leftStart
						- leftTarget) < Config.driveEncoderCyclesPerRevolution;
				

	}

}
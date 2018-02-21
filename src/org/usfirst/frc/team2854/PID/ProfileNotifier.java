package org.usfirst.frc.team2854.PID;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

public class ProfileNotifier {

  boolean shouldEnable;
  Notifier notifier;
  int points = 0;
  final int minPoints = 5;
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
  }

  public void addPoint() {
    points++;
    if (minPoints >= points) {
      shouldEnable = true;
    }
  }

  public boolean shouldEnable() {
    return shouldEnable;
  }

  public void startNotifier() {
    enabled = true;
    notifier.setHandler(() -> {
      rightT.processMotionProfileBuffer();
      leftT.processMotionProfileBuffer();
    });
    notifier.startPeriodic(updateTime / 2d / 1000d);
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isFinished(double thresh) {
    return Math.abs(leftT.getSelectedSensorPosition(0) - leftStart - leftStart) < thresh
            && Math.abs(rightT.getSelectedSensorPosition(0) - rightStart - rightStart) < thresh;
  }

}
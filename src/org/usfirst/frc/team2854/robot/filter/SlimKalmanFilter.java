package org.usfirst.frc.team2854.robot.filter;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class SlimKalmanFilter {

  Gyro a, b, c, d;
  Matrix states, covariance;


  public SlimKalmanFilter(Gyro a, Gyro b, Gyro c, Gyro d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    states = new Matrix(1,4);

    covariance = new Matrix(4,4);
  }
}

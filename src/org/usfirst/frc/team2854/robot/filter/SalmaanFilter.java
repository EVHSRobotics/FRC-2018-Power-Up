package org.usfirst.frc.team2854.robot.filter;

public class SalmaanFilter extends KalmanFilterSimple {
  double x; // Holds next estimation of measurements in real time
  double xlast = 0d; // Previous estimation
  double z = 0.0;
  double A = 1; // don't know what this is, maybe linear factor for calculations
  double B = 1; // don't know what this is, maybe linear factor for calculations
  double K = 0.5; // Kalman gain, need to implement still (shouldn't be zero)
  double P = 0.5; // error covariance estimate
  double PLast = 0.5; //previous covariance
  double Q; //covariance of process noise
  double R; //covariance of measurement noise
  double H = 1; //same as A and B


  SlimMatrix transitionMatrix = new SlimMatrix(1, 4);

  public SalmaanFilter(double a) {
    z = a;

  }
  public void predict(){
    xlast = A * xlast;
    P = (A * A * PLast) + Q;
  }
  public void correction(){
    K = (PLast*H)/((H*H*PLast) + R);
  }
  // Kalman filter for 1 value at first, use weighted average to use multiple
}

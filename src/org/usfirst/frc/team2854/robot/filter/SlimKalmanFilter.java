package org.usfirst.frc.team2854.robot.filter;

import java.util.Random;

public class SlimKalmanFilter {
  double x,
      a,
      b,
      z,
      w,
      gain,
      u,
      c; // g around 0.7, x = current prediction, xlast is previous, p around 1
  double r; // published accuracy of the sensor
  double p; // prediction error
  // u = control signal, b = rate of change of degrees
  public SlimKalmanFilter(
      double g,
      double noise,
      double prediction_error,
      double input,
      double a,
      double u,
      double b,
      double c) {
    gain = g;
    r = noise;
    p = prediction_error;
    w = 0; // Assume 0 process noise
    z = input;
    x = input;
    this.a = a;
  }

  public double prediction() {
    x = a * x + b * u + w;
    p = a * p * a;
    return x;
  }

  public void update(double input) {
    z = input;
    gain = (p * c) / (c * p * c + r);
    x = x + gain * (z - x * c);
    p = (1 - gain * c) * p;
  }
}

class Main {

  public static void main(String[] args) {
    int i = 0;
    double[] values = {1000, 500, 250, 125, 62.5, 31.25, 15.625, 7.8125, 3.90625, 1.953125};
    double[] withNoise = new double[values.length];
    double[] predictions = new double[withNoise.length];
    double maxNoise = 0.05;
    Random r = new Random();
    while (i < values.length) { // generate noise
      withNoise[i] = values[i] + (r.nextDouble() * 15);
      System.out.print(withNoise[i] + ", ");
      i++;
    }
    i = 1;
    SlimKalmanFilter kf = new SlimKalmanFilter(0.7, 200, 1, withNoise[0], 0.5, 1, 1, 1);
    System.out.println();
    System.out.println();
    System.out.println("Filter:");
    while (i < withNoise.length) {
      predictions[i] = kf.prediction();
      System.out.println(predictions[i]);
      kf.update(withNoise[i]);
      // System.out.println(values[i]);
      i++;
    }
    i = 0;
    // Average percent error calculation
    System.out.println();
    System.out.println("Error calculation:" + predictions.length);
    double error = 0;
    double[] errors = new double[predictions.length];
    while (i < predictions.length) {
      error = Math.abs((values[i] - predictions[i]) / (values[i])) * 100;
      System.out.println("error = " + error);
      errors[i] = error;
      i++;
    }

    double average, sum = 0;
    for (double d : errors) sum += d;
    average = (sum - 100) / (errors.length - 1);
    System.out.println();
    System.out.println(average);
  }
}

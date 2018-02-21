package org.usfirst.frc.team2854.robot.filter;

import java.util.Random;

public class Main {

  public static void main(String[] args) {
    int i = 0;
    double[] values = {1000,500,250,125,62.5, 31.25, 15.625, 7.8125, 3.90625, 1.953125};
    double[] withNoise = new double[values.length];
    double[] predictions = new double[withNoise.length];
    double maxNoise = 0.05;
    Random r = new Random();
    System.out.println("Values received with noise: ");
    while(i < values.length){ //generate noise
      withNoise[i] = values[i] + (r.nextDouble()*15);
      System.out.print(withNoise[i] + ", ");
      i++;
    }
    i = 1;
    KalmanFilter kf = new KalmanFilter(0.7, 200, 1, withNoise[0], 0.5, 1, 1, 1);
    System.out.println();
    System.out.println();
    System.out.println("Filter:");
    while(i < withNoise.length){
      predictions[i] = kf.prediction();
      System.out.println("c: " + kf.c);
      System.out.println("Prediction val: " + predictions[i]);
      kf.update(withNoise[i]);
      i++;
    }
    i = 0;
    //Average percent error calculation
    System.out.println();
    System.out.println("Error calculation:" + predictions.length);
    double error = 0;
    double[] errors = new double[predictions.length];
    while(i < predictions.length){
      error = Math.abs((values[i]-predictions[i])/(values[i]))*100;
      System.out.println("error = " + error);
      errors[i] = error;
      i++;
    }

    double average, sum = 0;
    for(double d : errors) sum+= d;
    average = (sum-100)/(errors.length-1);
    System.out.println();
    System.out.println(average);
  }
}

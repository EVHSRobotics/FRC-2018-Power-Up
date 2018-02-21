package org.usfirst.frc.team2854.robot.filter.FatKalman;

import org.ejml.data.DMatrixRMaj;

import java.util.Random;

public class KalmanFilterSimpleDriver {
  public static void main(String[] args) {

    KalmanFilterSimple filter = new KalmanFilterSimple();

    double[] trueAngles = {1000,500,250,125,62.5, 31.25, 15.625, 7.8125, 3.90625, 1.953125};
    double[][] sensorValues = new double[trueAngles.length][2];
    double[][] sensorValuesInColFormat = new double[2][trueAngles.length];
    Random r = new Random();
    int sensor_stddev = 20;
    for (int i = 0; i < sensorValues.length; i++) {
      sensorValuesInColFormat[0][i] = trueAngles[i] + returnNoise(sensor_stddev);
      sensorValuesInColFormat[0][i] = trueAngles[i] + returnNoise(sensor_stddev);
    }
    for (int i = 0; i < sensorValues.length; i++) {
      sensorValuesInColFormat[0][i] = trueAngles[i] + returnNoise(sensor_stddev);
      sensorValuesInColFormat[1][i] = trueAngles[i] + returnNoise(sensor_stddev);
    }

    double[][] tempF = {{0.5}}; // assume 1 state variable for now
    double[][] tempQ = {{0.01}}; // assume process noise covariance is 1?
    double[][] tempH = {{1}, {1}};
    double[][] tempX = {{sensorValuesInColFormat[0][0]}};
    double[][] tempP = {{1}};
    double[][] tempR = {
      {sensor_stddev * sensor_stddev, 0}, {0, sensor_stddev * sensor_stddev}
    }; // stddev * stddev

    filter.configure(new DMatrixRMaj(tempF), new DMatrixRMaj(tempQ), new DMatrixRMaj(tempH));
    filter.setState(new DMatrixRMaj(tempX), new DMatrixRMaj(tempP));
    for (int i = 0; i < trueAngles.length; i++) {
      System.out.println("Reading 1: " + sensorValuesInColFormat[0][i+1]);
      System.out.println("Reading 2: " + sensorValuesInColFormat[1][i+1]);
      filter.predict();
      //      System.out.println("Z: " + new DMatrixRMaj(returnRow(i, sensorValues)));
      ////      System.out.println("H: " + new DMatrixRMaj(tempH));
      ////      System.out.println("X: " + new DMatrixRMaj(tempX));
      ////      System.out.println("R: " + new DMatrixRMaj(tempR));
      filter.update(
          new DMatrixRMaj(returnColumn(i+1, sensorValuesInColFormat)), new DMatrixRMaj(tempR));
      System.out.print("Best estimate for next state: ");
      System.out.println(filter.getStateVar());
      System.out.println("Actual next state: " + trueAngles[i+1]);
      System.out.println("Percent error: " + (filter.getStateVar() - trueAngles[i+1]) / trueAngles[i+1] * 100 + "%");
      System.out.println();
    }
  }

  public static double[][] returnRow(int rowIndex, double[][] array2d) {
    double[][] myRow = new double[1][array2d[rowIndex].length];
    for (int i = 0; i < array2d[rowIndex].length; i++) {
      myRow[0][i] = array2d[rowIndex][i];
    }
    return myRow;
  }

  /**
   * Returns a column
   *
   * @param colIndex the index of the column
   * @param array2d a rectangular 2d array
   * @return a 2D array representation of that column
   */
  public static double[][] returnColumn(int colIndex, double[][] array2d) {
    double[][] myCol = new double[array2d.length][1];
    for (int i = 0; i < array2d.length; i++) {
      myCol[i][0] = array2d[i][colIndex];
    }
    return myCol;
  }

  public static double returnNoise(double stddev) {
    Random r = new Random();
    double noise = (Math.round(Math.random()) * 2 - 1) * r.nextDouble() * stddev;
    return noise;
  }
}

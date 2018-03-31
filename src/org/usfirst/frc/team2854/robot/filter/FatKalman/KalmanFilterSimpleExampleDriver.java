package org.usfirst.frc.team2854.robot.filter.FatKalman;

import org.ejml.data.DMatrixRMaj;

import java.util.Random;

public class KalmanFilterSimpleExampleDriver {
  public static void main(String[] args) throws Exception {
    Random r = new Random(); // same seed to test accuracy of filter;
    KalmanFilterSimple filter = new KalmanFilterSimple();

    int numberOfTests = 1000;
    double pNoise =
        5; // process noise -- how much the state can fluctuate by due to uncontrollable factors
    // (e.g., turbulence)
    int sensor_stddev = 3;

    // Angles as expected by math model
    double[] perfectAngles = new double[numberOfTests];

    for (int i = 0; i < perfectAngles.length; i++) {
      perfectAngles[i] = 1000 * Math.pow(3.0 / 6.0, i);
    }

    // True angle values are different because of external factors--turbulence, motor
    // inefficiency--this is technically dubbed
    // PROCESS NOISE
    double[] trueAngles = new double[perfectAngles.length];
    for (int i = 0; i < perfectAngles.length; i++) {
      if (i == 0) {
        trueAngles[i] = perfectAngles[i] + generateProcessNoise(pNoise);
      } else {
        trueAngles[i] = trueAngles[i - 1] * (3.0 / 6.0) + generateProcessNoise(pNoise);
      }
    }

    double[][] sensorValues = new double[trueAngles.length][2];
    double[][] sensorValuesInColFormat = new double[2][trueAngles.length];

    for (int i = 0; i < sensorValues.length; i++) {
      sensorValuesInColFormat[0][i] = trueAngles[i] + generateNoise(sensor_stddev);
      sensorValuesInColFormat[1][i] = trueAngles[i] + generateNoise(sensor_stddev);
    }

    double[][] tempF = {{3.0 / 6.0}}; // assume 1 state variable for now, hence 1x1 matrix
    double[][] tempQ = {{81}}; // assume process noise covariance is 5? idk random rn
    double[][] tempH = {{1}, {1}}; // JUST ASSUME H IS 1
    double[][] tempX = {{trueAngles[0]}};
    double[][] tempP = {
      {100}
    }; // P is the average of the squared error of our predictions OR random large number to begin
    // with?
    double[][] tempR = {
      {sensor_stddev, 0}, {0, sensor_stddev}
    }; // stddev * stddev

    filter.configure(new DMatrixRMaj(tempF), new DMatrixRMaj(tempQ), new DMatrixRMaj(tempH));
    filter.setState(new DMatrixRMaj(tempX), new DMatrixRMaj(tempP));

    int kalmanCount = 0;
    int sensorsCount = 0;
    int sensorsandModelCount = 0;

    for (int i = 0; i + 1 < trueAngles.length; i++) {
      System.out.println("Trial: " + (i + 1));
      System.out.println("Reading 1: " + sensorValuesInColFormat[0][i + 1]);
      System.out.println("Reading 2: " + sensorValuesInColFormat[1][i + 1]);
      filter.predict();
      double pureMathValue = perfectAngles[i+1];
      //      System.out.println("Z: " + new DMatrixRMaj(returnRow(i, sensorValues)));
      ////      System.out.println("H: " + new DMatrixRMaj(tempH));
      ////      System.out.println("X: " + new DMatrixRMaj(tempX));
      ////      System.out.println("R: " + new DMatrixRMaj(tempR));
      filter.update(
          new DMatrixRMaj(returnColumn(i + 1, sensorValuesInColFormat)), new DMatrixRMaj(tempR));
      System.out.print("Best estimate for next state: ");
      System.out.println(filter.getStateVar());
      System.out.println("Actual next state: " + trueAngles[i + 1]);
      System.out.println("Off by " + (filter.getStateVar() - trueAngles[i + 1]) + " units");
      double kalmanError = (filter.getStateVar() - trueAngles[i + 1]) / trueAngles[i + 1] * 100;
      double sensorsError =
          (((sensorValuesInColFormat[0][i + 1] + sensorValuesInColFormat[1][i + 1]) / 2.0
                  - trueAngles[i + 1])
              / trueAngles[i + 1]
              * 100);
      double sensorsAndModelError =
          (((sensorValuesInColFormat[0][i + 1] + sensorValuesInColFormat[1][i + 1] + pureMathValue)
                      / 3.0
                  - trueAngles[i + 1])
              / trueAngles[i + 1]
              * 100);
      String bestMethod = "Rounding error";

      if (Math.min(Math.abs(sensorsError), Math.abs(sensorsAndModelError))
          == Math.abs(sensorsAndModelError)) {
        if (Math.min(Math.abs(sensorsAndModelError), Math.abs(kalmanError))
            == Math.abs((sensorsAndModelError))) {
          bestMethod = "Avg of sensors and model";
          sensorsandModelCount++;
        } else if (Math.min(Math.abs(sensorsAndModelError), Math.abs(kalmanError))
            == Math.abs(kalmanError)) {
          bestMethod = "Kalman filter";
          kalmanCount++;
        }
      } else {
        if (Math.min(Math.abs(sensorsError), Math.abs(kalmanError)) == Math.abs(sensorsError)) {
          bestMethod = "Avg of sensors";
          sensorsCount++;
        } else if (Math.min(Math.abs(sensorsError), Math.abs(kalmanError))
            == Math.abs(kalmanError)) {
          bestMethod = "Kalman filter";
          kalmanCount++;
        } else {
          throw new Exception("Rounding error");
        }
      }

      System.out.println("Percent error of Kalman: " + kalmanError + "%");

      System.out.println("Percent error of avg of sensor values: " + sensorsError + " %");

      System.out.println(
          "Percent error of avg of projected model and sensor values: "
              + sensorsAndModelError
              + " %");
      System.out.println("Least error found in: " + bestMethod);

      String bestMethodOverall = "";
      int mostSuccesses = Math.max(kalmanCount, Math.max(sensorsCount, sensorsandModelCount));
      if (mostSuccesses == kalmanCount) {
        bestMethodOverall = "Kalman";
      } else if (mostSuccesses == sensorsCount) {
        bestMethodOverall = "Avg of sensors";
      } else if (mostSuccesses == sensorsandModelCount) {
        bestMethodOverall = "Avg of sensors and model";
      } else {
        throw new Exception();
      }
      System.out.println("Kalman successes: " + kalmanCount);
      System.out.println("Sensors successes: " + sensorsCount);
      System.out.println("Sensors and model successes " + sensorsandModelCount);
      System.out.println("Best method overall = " + bestMethodOverall);
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

  /**
   * Diagnostic method that generates noise (e.g., sensor defects) that affects the reading of the
   * state
   *
   * @param stddev
   * @return
   */
  public static double generateNoise(double stddev) {
    Random r = new Random();
    double noise = (Math.round(r.nextDouble()) * 2 - 1) * r.nextDouble() * stddev;
    return noise;
  }

  /**
   * Diagnostic method that generates process noise (e.g., turbulence) that affects the actual state
   *
   * @param netChange
   * @return
   */
  public static double generateProcessNoise(double netChange) {
    Random r = new Random();
    double processNoise = (Math.round(r.nextDouble()) * 2 - 1) * r.nextDouble() * netChange;
    return processNoise;
  }
}

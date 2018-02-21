/*
 * Copyright (c) 2009-2017, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.usfirst.frc.team2854.robot.filter.FatKalman;

import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.SimpleMatrix;

/**
 * A Kalman filter implemented using SimpleMatrix. The code tends to be easier to read and write,
 * but the performance is degraded due to excessive creation/destruction of memory and the use of
 * more generic algorithms. This also demonstrates how code can be seamlessly implemented using both
 * SimpleMatrix and DMatrixRMaj. This allows code to be quickly prototyped or to be written either
 * by novices or experts.
 *
 * @author Peter Abeles
 */
public class KalmanFilterSimple implements KalmanFilter {

  // kinematics description
  private SimpleMatrix F; //model for change in the state
  private SimpleMatrix Q; //process noise covariance e.g., turbulence
  private SimpleMatrix H; //measurement model (i.e., matrix of sensor values) OR C idk

  // system state estimate
  private SimpleMatrix x; // state vector
  private SimpleMatrix P; // prediction error covariance that is computed recursively

  /**
   * Specify the kinematics model of the Kalman filter.  This must be called
   * first before any other functions.
   *
   * @param F State transition matrix.
   * @param Q process noise covariance matrix
   * @param H measurement matrix
   */
  @Override
  public void configure(DMatrixRMaj F, DMatrixRMaj Q, DMatrixRMaj H) {
    this.F = new SimpleMatrix(F);
    this.Q = new SimpleMatrix(Q);
    this.H = new SimpleMatrix(H);
  }

  /**
   * The prior state estimate and covariance.
   *
   * @param x The estimated system state.
   * @param P The covariance of the estimated system state.
   */
  @Override
  public void setState(DMatrixRMaj x, DMatrixRMaj P) {
    this.x = new SimpleMatrix(x);
    this.P = new SimpleMatrix(P);
  }

  /**
   * First step of Kalman filter
   * Predicts the state of the system forward one time step. Entirely based on the state transition matrix (i.e., mathematical model).
   */
  @Override
  public void predict() {
    // x = F x
    x = F.mult(x);

    // P = F P F' + Q
    P = F.mult(P).mult(F.transpose()).plus(Q);
  }

  /**
   * Second step of Kalman filter
   * Updates the state provided the observation from a sensor(s).
   * @param _z Measurement matrix
   * @param _R Measurement covariance
   */
  @Override
  public void update(DMatrixRMaj _z, DMatrixRMaj _R) {
    // a fast way to make the matrices usable by SimpleMatrix
//    SimpleMatrix z = SimpleMatrix.wrap(_z);
//    SimpleMatrix R = SimpleMatrix.wrap(_R);
//
//    // y = z - H x
//    SimpleMatrix y = z.minus(H.mult(x));
//
//    // S = H P H' + R
//    SimpleMatrix S = H.mult(P).mult(H.transpose()).plus(R);
//
//    // K = PH'S^(-1)
//    SimpleMatrix K = P.mult(H.transpose().mult(S.invert()));
//
//    // x = x + Ky
//    x = x.plus(K.mult(y));
//
//    // P = (I-kH)P = P - KHP
//    P = P.minus(K.mult(H).mult(P));
    SimpleMatrix z = SimpleMatrix.wrap(_z);
    SimpleMatrix R = SimpleMatrix.wrap(_R);

    // y = z - H x
    SimpleMatrix y = z.minus(H.mult(x));
//    System.out.println("y: " + y);
//
//    // S = H P H' + R
//    System.out.println("H.H.mult(P).mult(H.transpose()): " + H.mult(P).mult(H.transpose()));
    SimpleMatrix S = H.mult(P).mult(H.transpose()).plus(R);

    // K = PH'S^(-1)
    System.out.print("K: " + P.mult(H.transpose().mult(S.invert())));
    SimpleMatrix K = P.mult(H.transpose().mult(S.invert()));

    // x = x + Ky
    x = x.plus(K.mult(y));

    // P = (I-kH)P = P - KHP
    P = P.minus(K.mult(H).mult(P));
  }

  @Override
  public DMatrixRMaj getState() {
    return x.getMatrix();
  }

  public double getStateVar() {
    return x.get(0,0);
  }

  @Override
  public DMatrixRMaj getCovariance() {
    return P.getMatrix();
  }

}

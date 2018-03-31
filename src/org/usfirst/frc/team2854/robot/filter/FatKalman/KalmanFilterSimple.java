package org.usfirst.frc.team2854.robot.filter.FatKalman;

import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.SimpleMatrix;

/**
 * A Kalman filter implemented using SimpleMatrix.  The code tends to be easier to
 * read and write, but the performance is degraded due to excessive creation/destruction of
 * memory and the use of more generic algorithms.  This also demonstrates how code can be
 * seamlessly implemented using both SimpleMatrix and DMatrixRMaj.  This allows code
 * to be quickly prototyped or to be written either by novices or experts.
 *
 * @author Peter Abeles
 */
public class KalmanFilterSimple implements KalmanFilter{

  // kinematics description
  private SimpleMatrix F,Q,H;

  // sytem state estimate
  private SimpleMatrix x,P;

  /**
   *
   * @param F State transition matrix.
   * @param Q plant noise.
   * @param H measurement projection matrix.
   */
  @Override
  public void configure(DMatrixRMaj F, DMatrixRMaj Q, DMatrixRMaj H) {
    this.F = new SimpleMatrix(F);
    this.Q = new SimpleMatrix(Q);
    this.H = new SimpleMatrix(H);
  }

  /**
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
   *
   */
  @Override
  public void predict() {
    // x = F x
    x = F.mult(x);

    // P = F P F' + Q
    P = F.mult(P).mult(F.transpose()).plus(Q);
  }

  /**
   *
   * @param _z
   * @param _R
   */
  @Override
  public void update(DMatrixRMaj _z, DMatrixRMaj _R) {
    // a fast way to make the matrices usable by SimpleMatrix
    SimpleMatrix z = SimpleMatrix.wrap(_z);
    SimpleMatrix R = SimpleMatrix.wrap(_R);

    // y = z - H x
    SimpleMatrix y = z.minus(H.mult(x));

    // S = H P H' + R
    SimpleMatrix S = H.mult(P).mult(H.transpose()).plus(R);

    // K = PH'S^(-1)
    SimpleMatrix K = P.mult(H.transpose().mult(S.invert()));
    System.out.print(K);

    // x = x + Ky
    x = x.plus(K.mult(y));
    //quickfix remove later if need be


    // P = (I-kH)P = P - KHP
    P = P.minus(K.mult(H).mult(P));
  }

  @Override
  public DMatrixRMaj getState() {
    return x.getMatrix();
  }

  @Override
  public DMatrixRMaj getCovariance() {
    return P.getMatrix();
  }

  public double getStateVar() {
    return x.get(0,0 );
  }
}
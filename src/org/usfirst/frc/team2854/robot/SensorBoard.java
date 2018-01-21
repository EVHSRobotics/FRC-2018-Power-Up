package org.usfirst.frc.team2854.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

public class SensorBoard {

  private AHRS navX;
  private ADXRS450_Gyro spiGyro;
  private BuiltInAccelerometer builtInacc;

  public SensorBoard() {
    navX = new AHRS(I2C.Port.kMXP);

    System.out.println("Navx connection: " + navX.isConnected());
    spiGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS1);
    builtInacc = new BuiltInAccelerometer();
    // gyro = new DualSensor("Gyro");
    // forwardAccel = new DualSensor("Forward Acceleration");

  }

  //	public void calibrate(long time) {
  //		long startTime = System.nanoTime();
  //		while(System.nanoTime() - startTime < time) {
  //			gyro.addValue(spiGyro.getRate(), navX.getRate());
  //			forwardAccel.addValue(builtInacc.getX(), navX.getRawAccelY());
  //		}
  //		gyro.calibrate();
  //		forwardAccel.calibrate();
  //	}

  //	public double getGyroValue() {
  //		return gyro.calculateValue(spiGyro.getRate(), navX.getRate());
  //	}
  //
  //	public double getForwardAccelValue() {
  //		return forwardAccel.calculateValue(builtInacc.getX(), navX.getRawAccelY());
  //	}
  //
  //	public DualSensor getGyro() {
  //		return gyro;
  //	}
  //
  //	public DualSensor getForwardAccel() {
  //		return forwardAccel;
  //	}

  public BuiltInAccelerometer getAccelerometer() {
    return builtInacc;
  }

  public AHRS getNavX() {
    return navX;
  }
}

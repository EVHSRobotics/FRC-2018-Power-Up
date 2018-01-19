package org.usfirst.frc.team2854.robot;

import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

public class SensorBoard {

	private AHRS navX;
	private ADXRS450_Gyro spiGyro;
	private BuiltInAccelerometer builtInacc;
	
	
	/**
	 * Default constructor.
	 */
	public SensorBoard() {
		navX = new AHRS(I2C.Port.kMXP);
		
		System.out.println("Navx connection: " + navX.isConnected());
		spiGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS1);
		builtInacc = new BuiltInAccelerometer();
		//gyro = new DualSensor("Gyro");
		//forwardAccel = new DualSensor("Forward Acceleration");
		
	}
	
	/**
	 * Calibrates gyro and forward accelerometer.
	 */
//	public void calibrate(long time) {
//		long startTime = System.nanoTime();
//		while(System.nanoTime() - startTime < time) {
//			gyro.addValue(spiGyro.getRate(), navX.getRate());
//			forwardAccel.addValue(builtInacc.getX(), navX.getRawAccelY());
//		}
//		gyro.calibrate();
//		forwardAccel.calibrate();
//	}
	/**
	 * Gets the value of the gyro.
	 * @return - value of the gyro
	 */
//	public double getGyroValue() {
//		return gyro.calculateValue(spiGyro.getRate(), navX.getRate());
//	}
	/**
	 * Gets the value of the forward acceleration.
	 * @return - value of the forward acceleration
	 */
//	public double getForwardAccelValue() {
//		return forwardAccel.calculateValue(builtInacc.getX(), navX.getRawAccelY());
//	}
	/**
	 * Gets gyro.
	 * @return - gyro
	 */
//	public DualSensor getGyro() {
//		return gyro;
//	}
	/**
	 * Gets the forward acceleration.
	 * @return - forward acceleration.
	 */
//	public DualSensor getForwardAccel() {
//		return forwardAccel;
//	}

	/**
	 * Getter method for the Accelerometer.
	 * @return - built in accelerometer.
	 */
	public BuiltInAccelerometer getAccelerometer() {
		return builtInacc;
	}

	/**
	 * Getter method for NavX object.
	 * @return - NavX object
	 */
	public AHRS getNavX() {
		return navX;
	}
	
	
}

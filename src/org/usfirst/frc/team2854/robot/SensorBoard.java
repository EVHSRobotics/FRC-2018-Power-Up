package org.usfirst.frc.team2854.robot;

import java.io.SerializablePermission;

import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class SensorBoard {

	private AHRS navX;
	private ADXRS450_Gyro spiGyro;
	private BuiltInAccelerometer builtInacc;
	private DriveTrain drive;

	public SensorBoard() {

		// navX = new AHRS(I2C.Port.kMXP);
		
//		String type = null;
//		navXLoop: {
//			for (I2C.Port p : I2C.Port.values()) {
//				try {
//					navX = new AHRS(p);
//					Timer.delay(1);
//					if (navX.isConnected()) {
//						type = p.name() + p.getClass().toString();
//						break navXLoop;
//					}
//				} catch (Exception e) {
//					continue;
//				}
//			}
//			for (SPI.Port p : SPI.Port.values()) {
//				try {
//					navX = new AHRS(p);
//					Timer.delay(1);
//					if (navX.isConnected()) {
//						type = p.name() + p.getClass().toString();
//						break navXLoop;
//					}
//				} catch (Exception e) {
//					continue;
//				}
//			}
//			for (Port p : SerialPort.Port.values()) {
//				try {
//					navX = new AHRS(p);
//					Timer.delay(1);
//					if (navX.isConnected()) {
//						type = p.name() + p.getClass().toString();
//						break navXLoop;
//					}
//
//				} catch (Exception e) {
//					continue;
//				}
//			}
//		}

//		System.out.println(type);
		navX = new AHRS(I2C.Port.kMXP);
		// spiGyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS1);
		builtInacc = new BuiltInAccelerometer();
		// gyro = new DualSensor("Gyro");
		// forwardAccel = new DualSensor("Forward Acceleration");
		drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
	}

	// public void calibrate(long time) {
	// long startTime = System.nanoTime();
	// while(System.nanoTime() - startTime < time) {
	// gyro.addValue(spiGyro.getRate(), navX.getRate());
	// forwardAccel.addValue(builtInacc.getX(), navX.getRawAccelY());
	// }
	// gyro.calibrate();
	// forwardAccel.calibrate();
	// }

	// public double getGyroValue() {
	// return gyro.calculateValue(spiGyro.getRate(), navX.getRate());
	// }
	//
	// public double getForwardAccelValue() {
	// return forwardAccel.calculateValue(builtInacc.getX(), navX.getRawAccelY());
	// }
	//
	// public DualSensor getGyro() {
	// return gyro;
	// }
	//
	// public DualSensor getForwardAccel() {
	// return forwardAccel;
	// }

	public BuiltInAccelerometer getAccelerometer() {
		return builtInacc;
	}

	public AHRS getNavX() {
		return navX;
	}

	public double getVelocity() {
		return drive.getAvgVelocity();
	}

}

package org.usfirst.frc.team2854.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Ultrasonic;

public class SensorBoard {

	private AHRS navX;
	private ADXRS450_Gyro spiGyro;
	private BuiltInAccelerometer builtInacc;
	private DriveTrain drive;
	private Ultrasonic ultra;

	private double ultraRange = -1;
	private boolean ultraShouldRun = true;
	
	private CvSource gyroOutput;
	private boolean putGyroOutput = true;
	private Mat arrow;
	
	public SensorBoard() {

		// navX = new AHRS(I2C.Port.kMXP);

		// String type = null;
		// navXLoop: {
		// for (I2C.Port p : I2C.Port.values()) {
		// try {
		// navX = new AHRS(p);
		// Timer.delay(1);
		// if (navX.isConnected()) {
		// type = p.name() + p.getClass().toString();
		// break navXLoop;
		// }
		// } catch (Exception e) {
		// continue;
		// }
		// }
		// for (SPI.Port p : SPI.Port.values()) {
		// try {
		// navX = new AHRS(p);
		// Timer.delay(1);
		// if (navX.isConnected()) {
		// type = p.name() + p.getClass().toString();
		// break navXLoop;
		// }
		// } catch (Exception e) {
		// continue;
		// }
		// }
		// for (Port p : SerialPort.Port.values()) {
		// try {
		// navX = new AHRS(p);
		// Timer.delay(1);
		// if (navX.isConnected()) {
		// type = p.name() + p.getClass().toString();
		// break navXLoop;
		// }
		//
		// } catch (Exception e) {
		// continue;
		// }
		// }
		// }

		// System.out.println(type);
		ultra = new Ultrasonic(1, 0); //1, 0;
		ultra.setEnabled(true);
		// ultra.setAutomaticMode(true);
		ultra.ping();
		gyroOutput = new CvSource("Gyro", new VideoMode(PixelFormat.kGray, 320, 480, 30));
//		arrow = Imgcodecs.imread("/res/arrow.png", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//		new Thread(()  ->  {
//			while(true) {
//				if(putGyroOutput) {
//					Point center = new Point(arrow.width()/2d, arrow.height()/2d);
//					Size size = new Size(arrow.width(), arrow.height());
//					Mat m = Imgproc.getRotationMatrix2D(center, navX.getAngle(), 1);
//					Mat output = new Mat();
//					Imgproc.warpAffine(arrow, output, m, size);
//					gyroOutput.putFrame(output);
//					m.release();
//					output.release();
//				} else {
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		});
		new Thread(() -> {
			while (ultraShouldRun) {
				try {
				if(ultra.isRangeValid()) {
					ultraRange = ultra.getRangeInches();
					//System.out.println(ultraRange);
					ultra.ping();
				}
//				if (ultra.isRangeValid()) {
//					
//					ultraRange = ultra.getRangeInches();
//					System.out.println("Found range " + ultraRange);
//					ultra.ping();
//				}
//				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		}).start();
		
		
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
	
	public void reInitUltra() {
		ultraShouldRun = false;
		try {
			ultra.free();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ultra = new Ultrasonic(1, 0);
			ultra.setEnabled(true);
			ultra.ping();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ultraShouldRun = true;
	}

	public BuiltInAccelerometer getAccelerometer() {
		return builtInacc;
	}

	public AHRS getNavX() {
		return navX;
	}

	public double getVelocity() {
		return drive.getAvgVelocity();
	}

	public double getUltraDistance() {
		return ultraRange;
	}

	public Ultrasonic getUltra() {
		return ultra;
	}

	public void setUltraShouldRun(boolean ultraShouldRun) {
		this.ultraShouldRun = ultraShouldRun;
	}

}

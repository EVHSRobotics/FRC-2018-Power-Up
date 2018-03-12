
package org.usfirst.frc.team2854.robot;



import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;


import org.usfirst.frc.team2854.robot.commands.ToggleShift;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.communication.NIRioStatus;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

import org.usfirst.frc.team2854.lidar.LidarReader;
import org.usfirst.frc.team2854.map.EncoderBased;
import org.usfirst.frc.team2854.map.FieldMapDriver;
import org.usfirst.frc.team2854.map.MapInput;
import org.usfirst.frc.team2854.map.elements.FieldMap;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Restartabale;
import org.usfirst.frc.team2854.vision.Vision;

//import com.kauailabs.sf2.frc.navXSensor;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	private static HashMap<SubsystemNames, Subsystem> subsystems;	
	private static SensorBoard sensors;

	private static Vision vision;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		subsystems = new HashMap<SubsystemNames, Subsystem>();	
		subsystems.put(SubsystemNames.DRIVE_TRAIN, new DriveTrain());
		
		System.out.println("Robot init");

//		vision = new Vision(new Scalar(85, 100, 100), new Scalar(125, 255, 255));
//		Thread visT = new Thread(vision);
//		visT.start();
//	 
//		vision.setShouldRun(true);
		
//		LidarReader lidar = new LidarReader(Port.kOnboard);
//		
//		CvSource lidarOut = CameraServer.getInstance().putVideo("lidar", lidar.getWidth(), lidar.getHeight());  
//		new Thread(() ->  {while(true) {Mat m = lidar.getMat();lidarOut.putFrame(m);m.release();}}).start();
//
//		new Thread(lidar).start();
		
		sensors = new SensorBoard();
		//navXSensor navX = new navXSensor(sensors.getNavX(), "test navx");
		
		
		for(Subsystem s : subsystems.values()) {
			if(s instanceof Restartabale) {
				((Restartabale) s).enable();
			}
		}
//		
		//SmartDashboard.putNumber("lowerHue", vision.getLowerBoundValue().val[0]);
		//SmartDashboard.putNumber("upperHue", vision.getUpperBoundValue().val[0]);

		
//		double fieldWidth = 5;
//		double fieldHeight = 5;
//		FieldMap map = new FieldMap(fieldWidth, fieldHeight);
//		MapInput input = new EncoderBased();
//		FieldMapDriver mapDrive = new FieldMapDriver(map, 720, 720, input);
//		
		//UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
	}
	
	@Override
	public void robotPeriodic() {
		
	}
	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		for(Subsystem s : subsystems.values()) {
			if(s instanceof Restartabale) {
				((Restartabale) s).disable();
			}
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		for(Subsystem s : subsystems.values()) {
			if(s instanceof Restartabale) {
				((Restartabale) s).enable();
			}
		}
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		for(Subsystem s : subsystems.values()) {
			if(s instanceof Restartabale) {
				((Restartabale) s).enable();
			}
		}
		
		//OI.buttonA.whenPressed(new DriveMotionMagik());

	}

	/**
	 * s This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		
//		
//		SmartDashboard.putNumber("kRIOStatusBufferInvalidSize", NIRioStatus.kRIOStatusBufferInvalidSize);
//		SmartDashboard.putNumber("kRIOStatusFeatureNotSupported", NIRioStatus.kRIOStatusFeatureNotSupported);
//		SmartDashboard.putNumber("kRioStatusOffset", NIRioStatus.kRioStatusOffset);
//		SmartDashboard.putNumber("kRIOStatusOperationTimedOut", NIRioStatus.kRIOStatusOperationTimedOut);
//		SmartDashboard.putNumber("kRIOStatusResourceNotInitialized", NIRioStatus.kRIOStatusResourceNotInitialized);
//		SmartDashboard.putNumber("kRioStatusSuccess", NIRioStatus.kRioStatusSuccess);
		
		
//		vision.setLowerBoundHue((int) SmartDashboard.getNumber("lowerHue", 0));
//		vision.setUpperBoundHue((int) SmartDashboard.getNumber("upperHue", 255));

		
		SmartDashboard.putBoolean("NavX is Connected", sensors.getNavX().isConnected());
		SmartDashboard.putBoolean("NavX is Calibrating", sensors.getNavX().isCalibrating());

		((DriveTrain)getSubsystem(SubsystemNames.DRIVE_TRAIN)).writeToDashBoard();
		
		double angle = sensors.getNavX().getAngle();
		SmartDashboard.putNumber("Gyro", angle);

		
		Scheduler.getInstance().run();		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static Subsystem getSubsystem(SubsystemNames name) {
		return subsystems.get(name);
	}
	public static SensorBoard getSensors() {
		return sensors;
	}

	public static Vision getVision() {
		return vision;
	}

}

package org.usfirst.frc.team2854.robot;

import java.util.HashMap;

import org.usfirst.frc.team2854.PID.PIDConstant;
import org.usfirst.frc.team2854.PID.drivePaths.DriveFarFar;
import org.usfirst.frc.team2854.PID.drivePaths.DriveFarNear;
import org.usfirst.frc.team2854.PID.drivePaths.DriveNearFar;
import org.usfirst.frc.team2854.PID.drivePaths.DriveNearNear;
import org.usfirst.frc.team2854.robot.commands.AutoIntake;
import org.usfirst.frc.team2854.robot.commands.RecreateUltra;
import org.usfirst.frc.team2854.robot.subsystems.Claw;
import org.usfirst.frc.team2854.robot.subsystems.Climb;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2854.robot.subsystems.Elevator;
import org.usfirst.frc.team2854.robot.subsystems.LED;
import org.usfirst.frc.team2854.robot.subsystems.Restartable;
import org.usfirst.frc.team2854.robot.subsystems.SubsystemNames;
import org.usfirst.frc.team2854.vision.Vision;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.sf2.frc.navXSensor;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	Command autonomousCommand;
	private static HashMap<SubsystemNames, Subsystem> subsystems;
	private static SensorBoard sensors;

	public static Compressor compressor;

	private static Vision vision;

	SendableChooser<String> sideChooser, advancedChooser;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		subsystems = new HashMap<SubsystemNames, Subsystem>();

		subsystems.put(SubsystemNames.DRIVE_TRAIN, new DriveTrain());
		subsystems.put(SubsystemNames.ELEVATOR, new Elevator());      
		subsystems.put(SubsystemNames.LED, new LED());
		subsystems.put(SubsystemNames.CLAW, new Claw());
		subsystems.put(SubsystemNames.CLIMB, new Climb());

		compressor = new Compressor(0);

		System.out.println("Robot init");

		sideChooser = new SendableChooser<String>();
		advancedChooser = new SendableChooser<String>();
		SmartDashboard.putData("auto", sideChooser);

		sideChooser.addDefault("right", "right");
		sideChooser.addObject("left", "left");

		advancedChooser.addDefault("advanced", "advanced");
		advancedChooser.addObject("basic", "basic");
		
		SmartDashboard.putData("re-create ultra", new RecreateUltra());
		SmartDashboard.putData("auto intake", new AutoIntake());

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("intakeCam", 0);
		camera.setExposureAuto();
		camera.setWhiteBalanceAuto();

//		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture("driveCam", 1);
//		camera1.setExposureAuto();
//		camera1.setWhiteBalanceAuto();

		vision = new Vision(camera);
		Thread visT = new Thread(vision);
		visT.start();

		vision.setShouldRun(false);

		//
		// vision.setShouldRun(false);
		//
		// LidarReader lidar = new LidarReader(Port.kOnboard);
		//
		// CvSource lidarOut = CameraServer.getInstance().putVideo("lidar",
		// lidar.getWidth(), lidar.getHeight());
		// new Thread(() -> {while(true) {Mat m =
		// lidar.getMat();lidarOut.putFrame(m);m.release();}}).start();
		//
		// new Thread(lidar).start();

		sensors = new SensorBoard();
		navXSensor navX = new navXSensor(sensors.getNavX(), "test navx");

		for (Subsystem s : subsystems.values()) {
			if (s instanceof Restartable) {
				((Restartable) s).enable();
			}
		}

		//
		// SmartDashboard.putNumber("lowerHue", vision.getLowerBoundValue().val[0]);
		// SmartDashboard.putNumber("upperHue", vision.getUpperBoundValue().val[0]);

		// double fieldWidth = 5;
		// double fieldHeight = 5;
		// FieldMap map = new FieldMap(fieldWidth, fieldHeight);
		// MapInput input = new EncoderBased();
		// FieldMapDriver mapDrive = new FieldMapDriver(map, 720, 720, input);
		//
		// UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
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
		for (Subsystem s : subsystems.values()) {
			if (s instanceof Restartable) {
				((Restartable) s).disable();
			}
		}

		((DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN)).shiftFast();

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
		for (Subsystem s : subsystems.values()) {
			if (s instanceof Restartable) {
				((Restartable) s).enable();
			}
		}
		getSensors().getNavX().zeroYaw();

		String side = sideChooser.getSelected();
		String advanced = advancedChooser.getSelected();
		String game = DriverStation.getInstance().getGameSpecificMessage();
		char switchChar = game.charAt(0);
		char scaleChar = game.charAt(1);

		System.out.println(advanced + " " + side + " " + switchChar + " " + scaleChar);

		if (advanced.equals("advanced")) {
			if (game.length() > 0) {
				if (side.equals("left") && switchChar == 'L' && scaleChar == 'L') {
					// left near near
				} else if (side.equals("left") && switchChar == 'L' && scaleChar == 'R') {
					// left near far
				} else if (side.equals("left") && switchChar == 'R' && scaleChar == 'L') {
					// left far near
				} else if (side.equals("left") && switchChar == 'R' && scaleChar == 'R') {
					// left far far
				} else if (side.equals("right") && switchChar == 'L' && scaleChar == 'L') {
					new DriveFarFar().start();
				} else if (side.equals("right") && switchChar == 'L' && scaleChar == 'R') {
					new DriveFarNear().start();
				} else if (side.equals("right") && switchChar == 'R' && scaleChar == 'L') {
					new DriveNearFar().start();
				} else if (side.equals("right") && switchChar == 'R' && scaleChar == 'R') {
					(new DriveNearNear()).start();
				} else {
					// smt is wrong, run some deafult command
				}
			}
		} else {
			if (game.length() > 0) {
				if (side.equals("left") && switchChar == 'L' && scaleChar == 'L') {
					// left near near
				} else if (side.equals("left") && switchChar == 'L' && scaleChar == 'R') {
					// left near far
				} else if (side.equals("left") && switchChar == 'R' && scaleChar == 'L') {
					// left far near
				} else if (side.equals("left") && switchChar == 'R' && scaleChar == 'R') {
					// left far far
				} else if (side.equals("right") && switchChar == 'L' && scaleChar == 'L') {
					new DriveFarFar().start();
				} else if (side.equals("right") && switchChar == 'L' && scaleChar == 'R') {
					new DriveFarNear().start();
				} else if (side.equals("right") && switchChar == 'R' && scaleChar == 'L') {
					new DriveNearFar().start();
				} else if (side.equals("right") && switchChar == 'R' && scaleChar == 'R') {
					(new DriveNearNear()).start();
				} else {
					// smt is wrong, run some deafult command
				}
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
		for (Subsystem s : subsystems.values()) {
			if (s instanceof Restartable) {
				((Restartable) s).enable();
			}
		}
		

		// 0,500,3750,4500

		// getSensors().getNavX().zeroYaw();
		((Claw) Robot.getSubsystem(SubsystemNames.CLAW)).zeroEncoder();
		((DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN)).setNeutralMode(NeutralMode.Brake);
		// OI.buttonA.whenPressed(new DriveMotionMagik());
		//Robot.getSensors().reInitUltra();
	}

	/**
	 * s This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		if(OI.mainJoystick.getRawButton(7) ) {
			Scheduler.getInstance().removeAll();
		}

		SmartDashboard.putBoolean("NavX is Connected", sensors.getNavX().isConnected());
		SmartDashboard.putBoolean("NavX is Calibrating", sensors.getNavX().isCalibrating());
		// if(sensors.getUltra().isRangeValid()) {
		SmartDashboard.putNumber("Ultra Distance", sensors.getUltraDistance());
		// }
		SmartDashboard.putBoolean("is range valid", sensors.getUltra().isRangeValid());
		SmartDashboard.putBoolean("Is ultra enabled", sensors.getUltra().isEnabled());

		((DriveTrain) getSubsystem(SubsystemNames.DRIVE_TRAIN)).writeToDashBoard();
		((Claw) getSubsystem(SubsystemNames.CLAW)).writeToDashboard();
		((Elevator) getSubsystem(SubsystemNames.ELEVATOR)).writeToDashboard();

		double angle = sensors.getNavX().getAngle();
		SmartDashboard.putNumber("Gyro", angle);

		Scheduler.getInstance().run();

		DriveTrain drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
		Elevator elevator = (Elevator) Robot.getSubsystem(SubsystemNames.ELEVATOR);
		if (Math.abs(elevator.getPos()) > Math.abs(Config.highElevator)) {
			drive.setDriveMultiplier(.5);
		} else if (Math.abs(elevator.getPos()) > Math.abs(Config.midElevator)) {
			drive.setDriveMultiplier(.75d);
		} else if (Math.abs(elevator.getPos()) > Math.abs(Config.lowElevator)) {
			drive.setDriveMultiplier(1);
		} else {
			drive.setDriveMultiplier(1);
		}
		SmartDashboard.putNumber("drive multiplier", drive.getDriveMultiplier());
		

		if (RobotController.getBatteryVoltage() < 9) {
			compressor.stop();
		} else {
			compressor.start();
		}

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

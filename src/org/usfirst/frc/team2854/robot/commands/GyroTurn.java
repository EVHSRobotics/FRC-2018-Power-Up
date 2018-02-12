package org.usfirst.frc.team2854.robot.commands;

import org.usfirst.frc.team2854.PID.DummyPIDOutput;
import org.usfirst.frc.team2854.PID.PIDConstant;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurn extends Command {

	private static PIDConstant pid = PIDConstant.autoTurn;
	private AHRS navX = Robot.getSensors().getNavX();

	private double target;
	private double thresh;
	private double ogTarget;

	private boolean relative;

	private DriveTrain driveTrain;

	private PIDController pidController;
	private DummyPIDOutput out;

	public GyroTurn(double target, double thresh, boolean relative) {
		requires(Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		driveTrain = (DriveTrain) (Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN));
		this.relative = relative;
		this.thresh = thresh;
		this.target = target;
		this.ogTarget = target;
		//System.out.println(navX.getAngle());
		//16 8 8 13 4
	}

	//20 ft forward
	//9 side
	public void initialize() {
		System.out.println("relative target " + target);
		if (relative) {
			this.target = ogTarget + navX.getAngle();
		} else {
			this.target = target;
		}
		//System.out.println("Abosolute target " + target);
		//System.out.println(target);
		PIDSource in = new PIDSource() {

			private PIDSourceType type;

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				type = pidSource;
			}

			@Override
			public double pidGet() {
				return (navX.getAngle() - target);
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return type;
			}
		};

		in.setPIDSourceType(PIDSourceType.kDisplacement);

		out = new DummyPIDOutput();

		pidController = new PIDController(pid.getP(), pid.getI(), pid.getD(), pid.getF(), in, out);
		pidController.setOutputRange(-1, 1);

		pidController.setInputRange(-180, 180);
		// pidController.setContinuous(true);
		pidController.setSetpoint(0);
		pidController.setAbsoluteTolerance(thresh);
		pidController.enable();
		driveTrain.setNeutralMode(NeutralMode.Brake);
	
	}

	public void execute() {
//		System.out.println("Connected " + navX.isConnected());
//		System.out.println("angle" + navX.getFusedHeading());

		double value = pidController.get();

	//	System.out.println("Error: " + pidController.getError()
	//			+ " output: " + value );

		driveTrain.drive(-value/2, value/2, ControlMode.PercentOutput);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return pidController.onTarget();
	}

	@Override
	public void end() {
		driveTrain.setNeutralMode(NeutralMode.Brake);
		pidController.disable();
	}

}

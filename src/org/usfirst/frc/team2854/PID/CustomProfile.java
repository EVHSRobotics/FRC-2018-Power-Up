package org.usfirst.frc.team2854.PID;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

import com.ctre.CANTalon;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class CustomProfile {

	public static ProfileNotifier generateTurnMotionControl(double cruzV, double outV, double turnR, double turnAngle,
			boolean right, TalonSRX leftT2, TalonSRX rightT2) {

		System.out.println("Generating profile");

		if (Config.robotWidth == 0) {
			throw new RuntimeException("measure the robot width");
		}
		TalonSRX outer = (right ? leftT2 : rightT2);
		TalonSRX inner = (right ? rightT2 : leftT2);
		double angle = Math.toRadians(turnAngle);
		DriveTrain drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);
		double outerDist = drive.inchesToCycles(turnR + Config.robotWidth) * angle;
		double innerDist = drive.inchesToCycles(turnR) * angle;

		double inVelOuter = outer.getSelectedSensorVelocity(0);
		double inVelInner = inner.getSelectedSensorPosition(0);

		double v1 = (inVelOuter + cruzV) / 8d;
		double v2 = cruzV / 2d;
		double v3 = (cruzV + outV) / 8d;

		double time = outerDist / (v1 + v2 + v3) * 100; // cycles / (cycles / 100ms) -> 100ms

		double firstAccelerationTime = time / 4d;
		double cruzTime = time / 2d;
		double secondAccelerationTime = time / 4d;

		double innerCruzVel = 4 * ((innerDist / time) - (inVelInner / 8d) - (outV / 8d)) / 3d;

		double firstAccelerationOuter = (cruzV - inVelOuter) / firstAccelerationTime;
		double secondAccelerationOuter = (outV - cruzV) / secondAccelerationTime;

		double firstAccelerationInner = (innerCruzVel - inVelInner) / firstAccelerationTime;
		double secondAccelerationInner = (outV - innerCruzVel) / secondAccelerationTime;

		TrajectoryPoint outerPoint = new TrajectoryPoint();
		TrajectoryPoint innerPoint = new TrajectoryPoint();

		double timer = 0;
		double stepSize = 10;

		double outerCurrentVel = inVelOuter;
		double innerCurrentVel = inVelInner;
		double outerCurrentPos = outer.getSelectedSensorPosition(0);
		double innerCurrentPos = inner.getSelectedSensorPosition(0);

		boolean isFirst = true;

		inner.clearMotionProfileTrajectories();
		outer.clearMotionProfileTrajectories();

		inner.configMotionProfileTrajectoryPeriod(10, 0);
		outer.configMotionProfileTrajectoryPeriod(10, 0);

		outerPoint.profileSlotSelect0 = 0;
		innerPoint.profileSlotSelect0 = 0;
		outerPoint.headingDeg = 0;
		innerPoint.profileSlotSelect0 = 0;
		outerPoint.profileSlotSelect1 = 1;
		innerPoint.profileSlotSelect1 = 1;

		ProfileNotifier pNotifier = new ProfileNotifier(10, innerDist, outerDist, inner, outer);

		System.out.println("first acc time: " + firstAccelerationTime);
		System.out.println("first acc time: " + cruzTime);
		System.out.println("first acc time: " + secondAccelerationTime);

		while (timer < time) {
			// System.out.println("happy face one");
			timer += .01;

			if (timer < firstAccelerationTime) {
				// System.out.println("happy face two");

				outerCurrentVel += firstAccelerationOuter * stepSize;
				outerCurrentPos += outerCurrentVel * stepSize;
				innerCurrentVel += firstAccelerationInner * stepSize;
				innerCurrentPos += outerCurrentVel * stepSize;

				outerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				outerPoint.velocity = outerCurrentVel;
				outerPoint.position = outerCurrentPos;
				outerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				innerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				innerPoint.velocity = innerCurrentVel;
				innerPoint.position = innerCurrentPos;
				innerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				if (isFirst) {
					outerPoint.zeroPos = true;
					innerPoint.zeroPos = true;
					isFirst = false;
				}

				pNotifier.addPoint();

			} else if (timer < firstAccelerationTime + cruzTime) {
				outerCurrentVel += cruzV;
				outerCurrentPos += outerCurrentVel * stepSize;
				innerCurrentVel += innerCruzVel;
				innerCurrentPos += outerCurrentVel * stepSize;

				outerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				outerPoint.velocity = outerCurrentVel;
				outerPoint.position = outerCurrentPos;
				outerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				innerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				innerPoint.velocity = innerCurrentVel;
				innerPoint.position = innerCurrentPos;
				innerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				pNotifier.addPoint();

			} else if (timer < firstAccelerationTime + cruzTime + secondAccelerationTime) {
				outerCurrentVel += secondAccelerationOuter * stepSize;
				outerCurrentPos += outerCurrentVel * stepSize;
				innerCurrentVel += secondAccelerationInner * stepSize;
				innerCurrentPos += outerCurrentVel * stepSize;

				outerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				outerPoint.velocity = outerCurrentVel;
				outerPoint.position = outerCurrentPos;
				outerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				innerPoint.isLastPoint = false;
				outerPoint.zeroPos = false;
				innerPoint.velocity = innerCurrentVel;
				innerPoint.position = innerCurrentPos;
				innerPoint.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				pNotifier.addPoint();

			} else if (timer >= time) {
				innerPoint.isLastPoint = true;
				innerPoint.isLastPoint = true;

				pNotifier.addPoint();

			}

			// inner.getMotionProfileStatus(status);
			// System.out.println(status.topBufferCnt);
<<<<<<< HEAD
			// System.out.println("generating " + (inner == null) + " " + (innerPoint ==
			// null));

			// printPoint(innerPoint);
=======
			System.out.println("generating " + (inner == null) + " " + (innerPoint == null));

			printPoint(innerPoint);
>>>>>>> 47c5598916ef906fc96759958b4e6c426fca28f1

			inner.pushMotionProfileTrajectory(innerPoint);
			outer.pushMotionProfileTrajectory(outerPoint);
		}

		System.out.println("done generating profile");

		return pNotifier;
	}

<<<<<<< HEAD
	public static void generateStraightMotionControl(double cruzV, double outV, double distance, TalonSRX leftT2,
			TalonSRX rightT2, ProfileNotifier pNotifier) {

		System.out.println("Generating profile");

		if (Config.robotWidth == 0) {
			throw new RuntimeException("measure the robot width");
		}

		DriveTrain drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);

		double leftVel = leftT2.getSelectedSensorVelocity(0);
		double rightVel = rightT2.getSelectedSensorPosition(0);
		double inVel = (leftVel + rightVel) / 2;

		double b1 = .25;
		double b2 = .5;
		double b3 = .25;

		double v1 = b1 / 2 * inVel;
		double v2 = b1 / 2 * cruzV + cruzV * b2 + b3 / 2 * cruzV;
		double v3 = b3 / 2 * outV;

		double totalT = distance / (v1 + v2 + v3);

		double t1 = totalT * b1;
		double t2 = totalT * b2;
		double t3 = totalT * b3;

		double a1 = (cruzV - inVel) / t1;
		double a2 = (outV - cruzV) / t3;

		boolean isFirst = true;

		System.out.println("A1: " + a1 + " A2: " + a2);
		System.out.println("TotalTime: " + totalT);
		leftT2.clearMotionProfileTrajectories();
		rightT2.clearMotionProfileTrajectories();

		leftT2.configMotionProfileTrajectoryPeriod(0, 0);
		rightT2.configMotionProfileTrajectoryPeriod(0, 0);

		leftT2.changeMotionControlFramePeriod(5);
		rightT2.changeMotionControlFramePeriod(5);

		TrajectoryPoint point = new TrajectoryPoint();

		point.profileSlotSelect0 = 0;
		point.profileSlotSelect0 = 0;
		point.headingDeg = 0;

		// ProfileNotifier pNotifier = new ProfileNotifier(10, distance, distance,
		// leftT2, rightT2);

		System.out.println("first acc time: " + t1);
		System.out.println("cruz time: " + t2);
		System.out.println("second acc time: " + t3);

		double timer = 0;

		double currentVel = inVel;
		double startingPos = (leftT2.getSelectedSensorPosition(0) + rightT2.getSelectedSensorPosition(0)) / 2;
		double currentPos = (leftT2.getSelectedSensorPosition(0) + rightT2.getSelectedSensorPosition(0)) / 2;

		double stepSize = .01 / 100d;

		while (timer < totalT) {
			// System.out.println("happy face one");
			timer += stepSize;
			// System.out.println("Total T: " + totalT + " currentT: " + timer);
			if (timer < t1) {
				// System.out.println("happy face two");

				currentVel += a1 * stepSize;
				currentPos += currentVel * stepSize;

				point.isLastPoint = false;
				point.zeroPos = false;
				point.velocity = currentVel;
				point.position = currentPos;
				point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				if (isFirst) {
					 point.zeroPos = true;
					 point.zeroPos = true;
					isFirst = false;
				}

				pNotifier.addPoint();

			} else if (timer < t1 + t2) {

				currentVel = cruzV;
				currentPos += cruzV * stepSize;

				point.isLastPoint = false;
				point.zeroPos = false;
				point.velocity = currentVel;
				point.position = currentPos;
				point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				pNotifier.addPoint();

			} else if (timer < t1 + t2 + t3) {

				currentVel += a2 * stepSize;
				currentPos += currentVel * stepSize;

				point.isLastPoint = false;
				point.zeroPos = false;
				point.velocity = currentVel;
				point.position = currentPos;
				point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;

				pNotifier.addPoint();

			} else if (timer >= totalT) {
				point.isLastPoint = true;
				point.velocity = outV;
				point.position = startingPos + distance;
				System.out.println("Adding last point");
				pNotifier.addPoint();

			}

			// inner.getMotionProfileStatus(status);
			// System.out.println(status.topBufferCnt);
			// System.out.println("generating " + (inner == null) + " " + (innerPoint ==
			// null));s

			point.position = point.position / Config.driveEncoderCyclesPerRevolution * drive.getDriveConstant();
			point.velocity = point.velocity /100d / Config.driveEncoderCyclesPerRevolution * 1000 * 60 * drive.getDriveConstant();
			
			System.out.println("Targetting velocity: " + point.velocity + " Target position: " + point.position);
			

			
			rightT2.pushMotionProfileTrajectory(point);
			leftT2.pushMotionProfileTrajectory(point);
		}
		System.out.println("total points " + pNotifier.points);
		System.out.println("done generating profile");

	}

	public static void generateTestMotionControl(double cruzV, double outV, double distance, TalonSRX leftT2,
			TalonSRX rightT2, ProfileNotifier pNotifier) {

		System.out.println("Generating profile");

		if (Config.robotWidth == 0) {
			throw new RuntimeException("measure the robot width");
		}

		DriveTrain drive = (DriveTrain) Robot.getSubsystem(SubsystemNames.DRIVE_TRAIN);

		TrajectoryPoint point = new TrajectoryPoint();
		
		point.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
		
		point.velocity = 0;
		
		rightT2.pushMotionProfileTrajectory(point);
		leftT2.pushMotionProfileTrajectory(point);
		
		point.velocity = Config.lowTarget;
		
		rightT2.pushMotionProfileTrajectory(point);
		leftT2.pushMotionProfileTrajectory(point);
		
		point.velocity = 0;

		
		System.out.println("Targeting velocity: " + point.velocity + " Target position: " + point.position);

		rightT2.pushMotionProfileTrajectory(point);
		leftT2.pushMotionProfileTrajectory(point);
		System.out.println("total points " + pNotifier.points);
		System.out.println("done generating profile");
	}

=======
>>>>>>> 47c5598916ef906fc96759958b4e6c426fca28f1
	public static void printPoint(TrajectoryPoint trajPt) {
		System.out.println(trajPt.position);
		System.out.println(trajPt.velocity);
		System.out.println(trajPt.headingDeg);
		System.out.println(trajPt.profileSlotSelect0);
		System.out.println(trajPt.profileSlotSelect1);
		System.out.println(trajPt.isLastPoint);
		System.out.println(trajPt.zeroPos);
		System.out.println(trajPt.timeDur == null);
		System.out.println(trajPt.timeDur.value);
	}

}

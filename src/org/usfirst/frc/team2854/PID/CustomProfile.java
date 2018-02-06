package org.usfirst.frc.team2854.PID;

import org.usfirst.frc.team2854.robot.Config;
import org.usfirst.frc.team2854.robot.Robot;
import org.usfirst.frc.team2854.robot.SubsystemNames;
import org.usfirst.frc.team2854.robot.subsystems.DriveTrain;

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

		outerPoint.profileSlotSelect0 = 0;
		innerPoint.profileSlotSelect0 = 0;
		outerPoint.headingDeg = 0;
		innerPoint.profileSlotSelect0 = 0;
		outerPoint.profileSlotSelect1 = 1;
		innerPoint.profileSlotSelect1 = 1;
		
		
		ProfileNotifier pNotifier = new ProfileNotifier(10, innerDist, outerDist, inner, outer);

		//leftT2.configMotionProfileTrajectoryPeriod(0, 10);
		//rightT2.configMotionProfileTrajectoryPeriod(0, 10);
		
		
		System.out.println("first acc time: " + firstAccelerationTime);
		System.out.println("first acc time: " + cruzTime);
		System.out.println("first acc time: " + secondAccelerationTime);

		
		while (timer < time) {
			System.out.println("happy face one");
			timer += .01;

			if (timer < firstAccelerationTime) {
				System.out.println("happy face two");

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
			
			//inner.getMotionProfileStatus(status);
			//System.out.println(status.topBufferCnt);
			System.out.println("generating " + (inner == null) + " " + (innerPoint==null));
			
			printPoint(innerPoint);
			
			
			
			inner.pushMotionProfileTrajectory(innerPoint);
			outer.pushMotionProfileTrajectory(outerPoint);
		}
		
		System.out.println("done generating profile");

		return pNotifier;
	}
	
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

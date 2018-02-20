package org.usfirst.frc.team2854.PID;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2854.robot.Config;

public class PIDConstant {

	public static PIDConstant slowDrive, fastDrive, autoTurn;

	static {
		
		//These were for programming robot  (Autobot)
		
		// ty richard
//		double P_Drive_LOW = 0.6;
//		double I_Drive_LOW = 1.7E-4;
//		double D_Drive_LOW = 5.8;
//		double F_Drive_LOW = 1023d / Config.lowTarget;
//
//		lowDrive = new PIDConstant(P_Drive_LOW, I_Drive_LOW, D_Drive_LOW, F_Drive_LOW, Config.lowTarget);
//
//		double P_Drive_HIGH = .22;
//		double I_Drive_HIGH = .0014;
//		double D_Drive_HIGH = 1.5;
//		double F_Drive_HIGH = 1023 / Config.highTarget;
//		
//		highDrive = new PIDConstant(P_Drive_HIGH, I_Drive_HIGH, D_Drive_HIGH, F_Drive_HIGH, Config.highTarget);
		
		double P_Drive_LOW = 0.45;
		double I_Drive_LOW = 1.8E-4;
		double D_Drive_LOW =  2;
		double F_Drive_LOW = 1023d / Config.slowTarget;

		slowDrive = new PIDConstant(P_Drive_LOW, I_Drive_LOW, D_Drive_LOW, F_Drive_LOW, Config.slowTarget);

		double P_Drive_HIGH = 0;
		double I_Drive_HIGH = 0;
		double D_Drive_HIGH = 0;
		double F_Drive_HIGH = 1023 / Config.fastTarget;
		
		fastDrive = new PIDConstant(P_Drive_HIGH, I_Drive_HIGH, D_Drive_HIGH, F_Drive_HIGH, Config.fastTarget);
		
		//TODO TUNE
		//ty matt
		double P_Drive_AUTO_TURN = .004;
		double I_Drive_AUTO_TURN = .002;
		double D_Drive_AUTO_TURN = .07;
		double F_Drive_AUTO_TURN = 0;//1023 / 180d;

		autoTurn = new PIDConstant(P_Drive_AUTO_TURN, I_Drive_AUTO_TURN, D_Drive_AUTO_TURN, F_Drive_AUTO_TURN, -1);
	}

	// ---------------------------------------------------------------------------

	private double P, I, D, F, targetSpeed;

	public PIDConstant(double p, double i, double d, double f, double targetSpeed) {
		P = p;
		I = i;
		D = d;
		F = f;
		this.targetSpeed = targetSpeed;
	}

	public static void startSmartDashboardInput(PIDConstant pid, TalonSRX... talons) {

		SmartDashboard.putNumber("P", pid.P);
		SmartDashboard.putNumber("I", pid.I);
		SmartDashboard.putNumber("D", pid.D);
		SmartDashboard.putNumber("F", pid.F);
		Runnable r = () -> {
			while (true) {
				double newP = SmartDashboard.getNumber("P", 0);
				double newI = SmartDashboard.getNumber("I", 0);
				double newD = SmartDashboard.getNumber("D", 0);
				double newF = SmartDashboard.getNumber("F", 0);
				
				PIDConstant newConst = new PIDConstant(newP, newI, newD, newF, pid.getTargetSpeed());
				if (!newConst.equals(pid)) {
					System.out.println("updating PID to " + newConst);
					pid.setPID(newConst);
					for (TalonSRX t : talons) {
						PIDUtil.updatePID(t, newConst);
					}
				}
			}
		};
		new Thread(r).start();
	}

	public void setPID(PIDConstant pid) {
		setPID(pid.P, pid.I, pid.D, pid.F);
	}

	public void setPID(double P, double I, double D, double F) {
		setP(P);
		setI(I);
		setD(D);
		setF(F);
	}

	public double getP() {
		return P;
	}

	public void setP(double p) {
		P = p;
	}

	public double getI() {
		return I;
	}

	public void setI(double i) {
		I = i;
	}

	public double getD() {
		return D;
	}

	public void setD(double d) {
		D = d;
	}

	public double getF() {
		return F;
	}

	public void setF(double f) {
		F = f;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PIDConstant other = (PIDConstant) obj;
		if (Double.doubleToLongBits(D) != Double.doubleToLongBits(other.D))
			return false;
		if (Double.doubleToLongBits(F) != Double.doubleToLongBits(other.F))
			return false;
		if (Double.doubleToLongBits(I) != Double.doubleToLongBits(other.I))
			return false;
		if (Double.doubleToLongBits(P) != Double.doubleToLongBits(other.P))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PIDConstant [P=" + P + ", I=" + I + ", D=" + D + ", F=" + F + "]";
	}

	public double getTargetSpeed() {
		return targetSpeed;
	}

	public void setTargetSpeed(double targetSpeed) {
		this.targetSpeed = targetSpeed;
	}
}

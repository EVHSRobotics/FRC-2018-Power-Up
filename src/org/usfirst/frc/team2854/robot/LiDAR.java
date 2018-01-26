package org.usfirst.frc.team2854.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Timer;

public class LiDAR implements PIDSource {
	private I2C i2c;
	private byte[] distance;
	private java.util.Timer updater;
	
	private final int LIDAR_ADDR = 0x62;
	private final int LIDAR_CONFIG_REGISTER = 0x00;
	private final int LIDAR_DISTANCE_REGISTER = 0x8f;
	
	public LiDAR(Port port) {
		i2c = new I2C(port, LIDAR_ADDR);
		distance = new byte[2];
		updater = new java.util.Timer();
	}
	
	public double getDistance() {
		return (double)(Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedLong(distance[1]));
//			'<<' shifts bit pattern 8 left; change that value to unsigned      change byte at distance[1] to unsigned 		
			
	}
	
	public double pidGet() { //must be there if class is implementing PID source
		return getDistance();
	}
	
	public void start() {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100); //executes lidarUpdater every 100 milliseconds start from 0ms
	}
	
	public void start(int period) {
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period); //executes lidarUpdater every period milliseconds start from 0ms
	}
	
	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}
	
	public void update() {
		i2c.write(LIDAR_CONFIG_REGISTER, 0x04); //initiate measurement
		Timer.delay(0.04);				//delay measurement to be taken
		i2c.read(LIDAR_DISTANCE_REGISTER, 2, distance); //read in measurement
		Timer.delay(0.005);				//add delay to prevent polling
	}
	
	private class LIDARUpdater extends TimerTask {
		public void run() {
			while(true) {
				update(); 
				try {
					Thread.sleep(10);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}

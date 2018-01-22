package org.usfirst.frc.team2854.map;

import java.awt.Color;
import java.io.ObjectInputStream.GetField;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.elements.FieldMap;
import org.usfirst.frc.team2854.map.math.Matrix;
import org.usfirst.frc.team2854.map.math.Vector;

import com.ctre.CANTalon;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldMapDriver implements Runnable{

	private double fWidth, fHeight;
	private int sWidth, sHeight;
	private CvSink input;
	private CvSource output;
	private MapInput robotInput;
	public volatile boolean shouldRun = true;
	
	private FieldMap map;
	
	public FieldMapDriver(FieldMap map, int sWidth, int sHeight, MapInput robotInput) {
		this.fWidth = map.getField().getWidth();
		this.fHeight = map.getField().getHeight();
		this.sWidth = sWidth;
		this.sHeight = sHeight;
		this.map = map;
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		input = CameraServer.getInstance().getVideo();
		output = CameraServer.getInstance().putVideo("Map", sWidth, sHeight);
		this.robotInput = robotInput;
		new Thread(this).start();
	} 
	
	

	@Override
	public void run() {
		long startTime = System.nanoTime();
		long lastTime = System.nanoTime();
		double deltaTime = 0;
		while(shouldRun) {
			//System.out.println("Running");
			startTime = System.nanoTime();
			deltaTime = (startTime - lastTime)/1E9d;
			robotInput.update();
			//System.out.println("asd " + map.getRobotPosition());
			Vector pos = map.getRobotPosition();
			Vector delta = (new Vector(robotInput.getDeltaForward(), 0).muliply(new Matrix().rotation(robotInput.getRotation())));
			map.setRobotPosition(pos.add(delta).getX(), pos.add(delta).getY());
			SmartDashboard.putString("Robot Position", "[" + map.getRobotPosition().toString() + "]");
			Mat screen = new Mat(sHeight, sWidth, CvType.CV_8UC3);
			
			for(int i = 0; i < sWidth; i++) {
				for(int j = 0; j < sHeight; j++) {
					
					screen.put(j, i, new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff});	
				}
			}
			
			int y = (int)(map.getRobotPosition().getY()*sHeight/fHeight);
			int x = (int)(map.getRobotPosition().getX()*sWidth/fWidth);
			
			int size = 2;
			for(int i = -size; i < size; i++) {
				for(int j = -size; j < size; j++) {
					if(y+i >=0 && y+i < screen.rows() && x+j >= 0 && x+j < screen.cols()) {
						screen.put(y+i, x+j , new byte[]{0, (byte) 0xff, 0});
					}
				}
				
			}
			//System.out.println("Made it here");
			
			//map.getField().draw(screen, new Vector(0,0), Color.black); TODO causes death
			
			
			output.putFrame(screen);
			lastTime = startTime;
		}
		System.out.println("Exiting Field Map");
	}

}

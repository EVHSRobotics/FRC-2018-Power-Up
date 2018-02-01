package org.usfirst.frc.team2854.lidar;

import java.util.Arrays;

import javax.management.RuntimeErrorException;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LidarReader implements Runnable {
	private PacketSeperator ps;
	private SerialPort lidar;
	private byte[] rawData;

	private byte[] headerSignature = { (byte) 0xFA };

	private double[] distances = new double[360];

	private int dataSize;

	private int dataBit = 8;
	
	Mat lidarGui;
	int width = 400, height = 400;

	public LidarReader(SerialPort.Port port, int baudRate) {
		dataSize = 1446;
		try {
			lidar = new SerialPort(baudRate, port, dataBit);
			System.out.println("hand shake is :)");
		} catch (Exception e) {
			System.out.println("Handshake protocol died");
			throw new RuntimeErrorException(new Error(), "LIDAR DIDNT HAND SHAKE");
		}
		rawData = new byte[dataSize];
		ps = new PacketSeperator(rawData, new byte[] { (byte) 0xFA });
		System.out.println("lidar construction is :)");
	}

	public LidarReader(SerialPort.Port port) {
		this(port, 115200);
	}

	private boolean readRawData() {
		try {
			rawData = new byte[dataSize];
			rawData = lidar.read(dataSize);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}

	public boolean processData() {
		System.out.println("Procng data");
		if (readRawData()) {
			ps.setData(rawData);
			for (Packet p : ps.getArrayListPacket()) {
				for (int j = 0; j < 4; j++) {
					try {
						distances[p.getDegree() + j] = p.getDataPointArray()[j].getDistance();
					} catch (Exception e) {
						System.out.println("wut...");
					}
				}
			}
			SmartDashboard.putString("data", Arrays.toString(distances));
			return true;
		} else {
			return false;
		}
	}



	public Mat getMat() {
		int x0 = width / 2, y0 = height / 2;
		double[] distances = getDistances();
		lidarGui = Mat.zeros(height, width, CvType.CV_8UC1);
		for (int i = 0; i < distances.length; i++) {
			int x, y;
			x = (int) (Math.cos(i * Math.PI / 180.0) * distances[i]) + x0;
			y = (int) (Math.sin(i * Math.PI / 180.0) * distances[i]) + y0;

			if (x >= 0 && x <= width && y >= 0 && y <= height) {
				try {
					lidarGui.put(y, x, 255);
				} catch (Exception e) {
					System.out.println("Rip mat.put");
				}
			}
		}
		return lidarGui;
	}

	public double[] getDistances() {
		return distances;
	}

	@Override
	public void run() {
		while (true) {
			if(processData() && !getPacketSeperator().getArrayListPacket().isEmpty()) {
				SmartDashboard.putNumber("Lidar RPM", getPacketSeperator().getArrayListPacket().get(0).getRPM());
				Timer.delay(0.05d);
			}
			

		}
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public PacketSeperator getPacketSeperator() {
		return ps;
	}

	public void free() {
		lidar.free();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}

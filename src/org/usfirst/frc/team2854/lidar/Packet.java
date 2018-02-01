package org.usfirst.frc.team2854.lidar;


import java.util.Arrays;

public class Packet {
	private byte startingByte;
	private byte index;
	private DataPoint[] dp;
	private byte speed1;
	private byte speed2;
	private byte checksum1;
	private byte checksum2;

	private int packetSize = 22;

	public Packet(byte[] data) {
		dp = new DataPoint[4];
		if (data.length == packetSize) {
			startingByte = data[0];
			index = data[1];
			speed1 = data[2];
			speed2 = data[3];
			for (int i = 0; i < 4; i++) {
				dp[i] = new DataPoint(Arrays.copyOfRange(data, 4 + i * 4, 4 + 4 + i * 4));
			}
			checksum1 = data[20];
			checksum2 = data[21];
		} else {
			System.out.print("!CorrectSize" + data.length);
		}
	}

	public String toString() {
		return "StartingByte:" + startingByte + " Index:" + index + " DataPoints:" + Arrays.toString(dp) + " Speed1"
				+ speed1 + " Speed2" + speed2 + " Checksum1" + checksum1 + " Checksum2" + checksum2;

	}

	public byte getIndex() {
		return index;
	}

	public int getDegree() {
		return ((int) (index + 96)) * 4;
	}

	public byte getStartingByte() {
		return startingByte;
	}

	public byte getSpeed1() {
		return speed1;
	}

	public byte getSpeed2() {
		return speed2;
	}

	public double getSpeed() {
		return (((int) getSpeed1()) + (((int) (getSpeed2() << 8))));
	}

	public double getRPM() {
		return getSpeed() / 64.0;
	}

	public DataPoint[] getDataPointArray() {
		return dp;
	}
}

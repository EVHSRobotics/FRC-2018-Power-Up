package org.usfirst.frc.team2854.lidar;

import java.util.Arrays;

public class DataPoint {
	private byte[] data;

	public DataPoint(byte[] a) {
		if (a.length == 4) {
			data = a;
		} else {
			System.out.println("This data point doesn't have 4 bytes");
		}
	}

	public byte getDistanceByte() {
		return data[0];
	}

	// public int getDistance() {
	// byte b1 = data[0];
	// int b2 = data[1];
	// b2 = (byte) (b2 & 0x3f); // 00111111
	// b2 = (b2 << 8);
	// if ((b2 & 0x40) > 0 || ((b2 & 0x80) > 0)) {
	// System.out.println("ded bit");
	// return 0;
	// }
	// return Byte.toUnsignedInt((byte) (b1 + b2));
	// }

	public int getDistance() {
		byte b1 = data[0];
		int b2 = data[1];
		if ((b2 & 0x40) > 0 || ((b2 & 0x80) > 0)) {
			System.out.println("ded bit");
			return 0;
		}
		b2 = (byte) (b2 & 0x3f); // 00111111
		b2 = (b2 << 8);
		return Byte.toUnsignedInt((byte) (b1 + b2));
	}

	public byte getSignalByte() {
		return data[1];
	}

	public byte getInvalidDistanceByte() {
		return data[2];
	}

	public byte getInvalidStrengthByte() {
		return data[3];
	}

	public String toString() {
		return Arrays.toString(data);
	}
}

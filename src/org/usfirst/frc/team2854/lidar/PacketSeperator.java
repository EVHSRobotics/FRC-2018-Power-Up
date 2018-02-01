package org.usfirst.frc.team2854.lidar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PacketSeperator {
	private ArrayList<Integer> indexes;
	private ArrayList<Packet> packets;
	private ArrayList<DataPoint> dataPoints;
	private int packetSize = 22;
	private byte[] rawData;
	private byte[] headerSignature;

	public PacketSeperator(byte[] rawData, byte[] headerSignature) {
		this.rawData = rawData;
		this.headerSignature = headerSignature;
		indexes = new ArrayList<Integer>();
		packets = new ArrayList<Packet>();
	}

	private void searchIndexes() {
		for (int i = 0; i < rawData.length - headerSignature.length; i++) {
			if (Arrays.equals(headerSignature, Arrays.copyOfRange(rawData, i, i + headerSignature.length))) {
				indexes.add(new Integer(i));
			}
		}
	}

	private void seperatePackets() {
		int[] indexes = getIndexes();
		for (int i = 0; i < indexes.length - packetSize; i++) {
			packets.add(new Packet(Arrays.copyOfRange(rawData, indexes[i], packetSize + indexes[i])));
		}
	}

	public int[] getIndexes() {
		if (indexes.isEmpty()) {
			searchIndexes();
		}
		int[] out = new int[indexes.size()];
		for (int i = 0; i < indexes.size(); i++) {
			out[i] = indexes.get(i);
		}
		return out;
	}

	public Packet[] getPackets() {
		if (packets.isEmpty()) {
			seperatePackets();
		}
		Packet[] out = new Packet[packets.size()];
		for (int i = 0; i < indexes.size(); i++) {
			out[i] = packets.get(i);
		}
		return out;
	}

	public ArrayList<Packet> getArrayListPacket() {
		seperatePackets();
		return packets;
	}

	public byte[] getHeaderSignature() {
		return headerSignature;
	}

	public Iterator<DataPoint> getDataPointIterator() {
		Iterator<Packet> packetIterator = packets.iterator();
		while (packetIterator.hasNext()) {
			Packet currentPacket = packetIterator.next();
			dataPoints.add(currentPacket.getDataPointArray()[0]);
			dataPoints.add(currentPacket.getDataPointArray()[1]);
			dataPoints.add(currentPacket.getDataPointArray()[2]);
			dataPoints.add(currentPacket.getDataPointArray()[3]);
		}
		return dataPoints.iterator();
	}

	public void setData(byte[] rawData) {
		this.rawData = rawData;
		indexes.clear();
		packets.clear();
	}
}



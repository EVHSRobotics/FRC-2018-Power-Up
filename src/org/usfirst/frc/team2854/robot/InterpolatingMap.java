package org.usfirst.frc.team2854.robot;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class InterpolatingMap{ 

	private LinkedHashMap<Double, OutputValue> data;
	
	private class OutputValue {
		int count = 0;
		double value;
		
		public OutputValue(double value) {
			this.value = value;
			count++;
		}
		
		public void addPoint(double value) {
			this.value *= count;
			this.value += value;
			count++;
			this.value /= count;
		}
	}
	
	public InterpolatingMap() {
		data = new LinkedHashMap<>();
	}
	
	public void addDataPoint(Double input, double output) {
		if(data.containsKey(input)) {
			data.get(input).addPoint(output);
		} else {
			data.put(input, new OutputValue(output));
		}
	}
	
	public double getValue(Double input) {
		if(data.containsKey(input)) {
			return data.get(input).value;
		} else {
			double closestMin, closestMax; //numbers that are closest but bigger and smaller than output
			for(Double testInput : data.keySet()) {
				
			}
			return -1;
		}
	}
	
}

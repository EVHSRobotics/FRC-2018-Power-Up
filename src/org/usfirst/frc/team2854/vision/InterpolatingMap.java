package org.usfirst.frc.team2854.vision;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class InterpolatingMap{ 

	private LinkedHashMap<Double, OutputValue> data;
	private ArrayList<Double> sortedInputs;
	private double minInput, maxInput;
	private double averageSlope;
	
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
	
	public void addDataPoint(double input, double output) {
		addDataPoint(input, output);
	}
	
	public void addDataPoint(Double input, double output) {
		if(data.containsKey(input)) {
			data.get(input).addPoint(output);
		} else {
			data.put(input, new OutputValue(output));
		}
		sortedInputs = new ArrayList<Double>(data.keySet());
		Collections.sort(sortedInputs);
		minInput = sortedInputs.get(0);
		maxInput = sortedInputs.get(sortedInputs.size()-1);
		averageSlope = (data.get(maxInput).value - data.get(minInput).value) / (maxInput - minInput);
		
		
	}
	
	public double getValue(Double input) {
		if(input < minInput || input > maxInput) {
			//throw new RuntimeException("Can not Extrapolate data! The value " + input + " must be in range [" + minInput + ", " + maxInput + "]" );
			//System.out.println("Extrapolating! results probably will contain significant error");
			return (input - minInput) * averageSlope + data.get(minInput).value;
		}
		if(data.containsKey(input)) {
			return data.get(input).value;
		} else {
			Double closestMinInput = 0d, closestMaxInput = 0d;
			
			
			for(int i = 1; i < sortedInputs.size(); i++) { //TODO replcae with binary search for the effeciencies
				if(sortedInputs.get(i) > input) {
					closestMinInput = sortedInputs.get(i-1);
					closestMaxInput = sortedInputs.get(i);
					break;
				}
			}
			
			Double closestMinOutput = data.get(closestMinInput).value;
			Double closestMaxOutput = data.get(closestMinInput).value;

			double slope = (closestMaxOutput - closestMinOutput)/(closestMaxInput - closestMinInput);
			
			return (input - closestMinInput) * slope + closestMinOutput;
		}
	}
	
}

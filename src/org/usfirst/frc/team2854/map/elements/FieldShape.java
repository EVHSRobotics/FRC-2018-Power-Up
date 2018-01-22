package org.usfirst.frc.team2854.map.elements;

import org.usfirst.frc.team2854.map.math.RobotPosition;

public interface FieldShape extends MapDrawable{
	
	public boolean isWithinBounds(RobotPosition rp);

}

package org.usfirst.frc.team2854.map.elements;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.usfirst.frc.team2854.map.math.RobotPosition;
import org.usfirst.frc.team2854.map.math.Vector;

public class Field implements FieldShape {
	private double width;
	private double height;
	private ArrayList<FieldShape> innerFieldPieces;

	Field(double width, double height) {
		this.width = width;
		this.height = height;
		innerFieldPieces = new ArrayList<FieldShape>();
	}

	Field() {
		width = 10;
		height = 10;
		innerFieldPieces = new ArrayList<FieldShape>();
	}

	private boolean isOutOfInnerPieces(RobotPosition rp) {
		for (FieldShape fs : innerFieldPieces) {
			if (fs.isWithinBounds(rp)) {
				return false;
			}
		}
		return true;
	}

//	public boolean isWithinBounds(RobotPosition rp) {
//		double x = rp.getX();
//		double y = rp.getY();
//		return isOutOfInnerPieces(rp) && contains(rp.getX(), rp.getY()); TODO extending rectnalge 2d kills shit so like uh not using @ zheng
//	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public void addFieldShape(FieldShape fs) {
		innerFieldPieces.add(fs);
	}

	public Rectangle2D createIntersection(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle2D createUnion(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int outcode(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRect(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub

	}

	public double getX() {
		return 0;
	}

	public double getY() {
		return 0;
	}

	public boolean isEmpty() {
		return false;
	}



	@Override
	public void draw(Mat m, Vector translation, Color c) {
		byte[] colorByte = new byte[] {(byte) c.getBlue(), (byte) c.getGreen(), (byte) c.getRed()};
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				if(x==0||y==0||x==getWidth()-1||y==getHeight()-1) {//TODO make more effecient
					m.put((int)(y+getY()+translation.getY()), (int)( x+getX()+translation.getX()), colorByte);
				}
			}
		}
		for(FieldShape shape:innerFieldPieces) {
			shape.draw(m, new Vector(getX(), getY()), c);
		}
		
	}

	public ArrayList<FieldShape> getInnerFieldPieces() {
		return innerFieldPieces;
	}

	@Override
	public boolean isWithinBounds(RobotPosition rp) {
		// TODO Auto-generated method stub
		return false;
	}
}

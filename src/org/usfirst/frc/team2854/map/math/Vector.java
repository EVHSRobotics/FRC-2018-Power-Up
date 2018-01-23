package org.usfirst.frc.team2854.map.math;

/**
 * A class to represent a 2D Vector
 */
public class Vector {

	protected double x, y;
	/**
	 * Initializes the vector with <x, y>
	 * @param x the x value
	 * @param y the y value
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Initializes the vector with <0, 0>
	 */
	public Vector() {
		this(0, 0);
	}
	
	/**
	 * Adds a double r to all components of the Vector
	 * @param r the double to add to the Vector
	 * @return a new Vector with the components incremented by r
	 */
	public Vector add(double r) {
		return new Vector(x + r, y + r);
	}
	
	/**
	 * Adds a new Vector to this Vector
	 * @param v the Vector to add
	 * @return a new Vector that has components incremented by Vector v's respective components
	 */
	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY());
	}
	/**
	 * Copies this vector
	 * @return a new Vector with the same values
	 */
	public Vector copyOf() {
		return new Vector(x, y);
	}

	
	/**
	 * Divides this Vector by a double 
	 * @param r the number to divide the Vector by
	 * @return a new Vector with the components divided by double
	 */
	public Vector devide(double r) {
		return new Vector(x / r, y / r);
	}
	
	/**
	 * Calculates the dot product of this and another Vector
	 * @param v the other Vector to be multiplied	
	 * @return the dot product of this and Vector v
	 */
	public double dot(Vector v) {
		return x * v.x + y * v.y ;
	}
	
	/**
	 * Calculates the dot product of two vectors
	 * @param v1 the first Vector to be multiplied	
	 * @param v2 the other Vector to be dotted with
	 * @return the dot product of this and Vector v
	 */
	public static double dot(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y + v2.y ;
	}
	
	
	

	
	/**
	 * Gets the x value
	 * @return double x
	 * 	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y value
	 * @return double y
	 */
	public double getY() {
		return y;
	}
	

	
	/**
	 * Gets the length of the vector
	 * @return double length of the Vector
	 */
	public double length() {
		return  Math.sqrt(x * x + y * y);
	}
	
	/**
	 * @Precondition matrix has 4 rows, 4 columns or more.
	 * Multiplies this Vector by a Matrix m
	 * @param m the Matrix to multiply this Vector by
	 * @return a new Vector with multiplied components
	 */
	public Vector muliply(Matrix m) {
		double[][] mat = m.getMat();
		double nX = mat[0][0] * x + mat[0][1] * y;
		double nY = mat[1][0] * x + mat[1][1] * y;
		return new Vector(nX, nY);
	}
	
	/**
	 * Multiplies this Vector by a double r
	 * @param r the multiplication factor
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(double r) {
		return new Vector(x * r, y * r);
	}
	
	/**
	 * Multiplies this Vector by a new Vector v 
	 * @param r the multiplication Vector to multiply this Vector by 
	 * @return a new Vector with multiplied components
	 */
	public Vector multiply(Vector v) {
		return new Vector(x * v.x, y * v.y);
	}
	
	/**
	 * Normalizes this vector by dividing it by the length of this vector
	 * @return a new Vector with divided components 
	 */
	public Vector normalize() {
		return this.devide(this.length());
	}
	
	/**
	 * Scales the Vector by 3 factors, for each component
	 * @param x the factor to multiply x by 
	 * @param y the factor to multiply y by 
	 * @param z the factor to multiply z by 
	 * @return a new Vector with scaled components
	 */
	public Vector scale(double x, double y, double z) {
		return new Vector(this.x * x, this.y * y);
	}
	

	
	/**
	 * Sets x to a new double number
	 * @param x the new double number for x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets y to a new double number
	 * @param y the new double number for y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Subtracts a double r from this Vector by adding negative r
	 * @param r the double to subtract from this Vector
	 * @return a new Vector with subtracted components
	 */
	public Vector subtract(double r) {
		return add(-r);
	}
	
	/**
	 * Subtracts a Vector from this Vector
	 * @param v the Vector to subtract from this Vector
	 * @return a new Vector that has subtracted components
	 */
	public Vector subtract(Vector v) {
		return new Vector(x - v.getX(), y - v.getY());
	}

	/**
	 * A toString method that returns information about the Vector's components
	 * @return a String containing the x, y, z, and w components. 
	 */
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
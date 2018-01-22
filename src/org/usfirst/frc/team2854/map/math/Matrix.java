package org.usfirst.frc.team2854.map.math;

public class Matrix {

	private double[][] mat;
	
	public Matrix() {
		mat = new double[2][2];
	}
	
	public Matrix(double[][] data) {
		this.mat = data;
	}
	
	public Matrix identity() {
		mat = new double[][] {{1, 0},{0, 1}};
		return this;
	}
	
	public Matrix rotation(double theta) {
		double c = Math.cos(Math.toRadians(theta));
		double s = Math.sin(Math.toRadians(theta));
		mat = new double[][] {{c, -s},{s, c}};
		return this;
	}
	
	public static Matrix multiply(Matrix... mats) {
		Matrix ans = new Matrix();
		ans.identity();
		for (Matrix m : mats) {
			ans = ans.mul(m);
		}
		return ans;
	}
	
	public Matrix mul(Matrix other) {

		double[][] ans = new double[2][2];

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 2; k++) {
					ans[i][j] += mat[i][k] * other.mat[k][j];
				}
			}
		}

		return new Matrix(ans);
	}
	
	public double[][] getMat() {
		return mat;
	}
	
}

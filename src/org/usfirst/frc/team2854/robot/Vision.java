package org.usfirst.frc.team2854.robot;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision implements Runnable {
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource outputStream;
	private double distance;
	Mat source;
	Mat output;

	public void run() {
		camera = CameraServer.getInstance().startAutomaticCapture();

		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Distance", 640, 480);

		source = new Mat();
		output = new Mat();
		while (!Thread.interrupted()) {
			cvSink.grabFrame(source);
			Mat hsv = convertToHsv(source);
			int w = printSize(hsv);
			findDistance(w);
			outputStream.putFrame(output);
		}
	}
	
	public Mat convertToHsv(Mat img) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat hsvImg = new Mat();

		Imgproc.cvtColor(source, output, Imgproc.COLOR_RGB2HSV);
		// H S V
		Scalar lowerBoundYellow = new Scalar(85, 100, 100);
		Scalar upperBoundYellow = new Scalar(100, 255, 255);
		Core.inRange(output, lowerBoundYellow, upperBoundYellow, hsvImg);

		return output;
	}

	public int printSize(Mat hsvImg) {
		int largestHeight = 0;
		int largestWidth = 0;
		Mat hierarchy = new Mat();
		ArrayList<MatOfPoint> contours = new ArrayList<>();

		// blur grayscaled/hsv image
		Imgproc.blur(hsvImg, output, new Size(10, 10));
		Imgproc.findContours(output, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		// go through the contours
		for (int i = 0; i < contours.size(); i++) { // for each contour
			MatOfPoint2f approxCurve = new MatOfPoint2f();
			MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

			double approxDistance = Imgproc.arcLength(contour2f, true) * .02; // measure the length of a closed contour
																				// curve
			Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true); // connect the points of the contours to
																				// approximate a closed polygon

			MatOfPoint points = new MatOfPoint(approxCurve.toArray()); // convert back to matofpoint

			Rect rect = Imgproc.boundingRect(points); // create bounding box
			Imgproc.rectangle(hsvImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(255, 255, 255), 3);// draw the box

			for (int j = 0; j < contours.size(); j++) {
				int height = rect.height;
				int width = rect.width;
				if (height > largestHeight)
					largestHeight = height;
				if (width > largestWidth)
					largestWidth = width;
			}
		}
		System.out.println(largestHeight + " height");
		System.out.println(largestWidth + " width");

		return largestWidth;
	}

	public double findDistance(int p) { // some constant parameter of object, use width
		int focal = 756; // f = (image of object width(pixels) * distance(in))/width of object(in); focal
							// is in pixels

		// int distance = (int)(Math.ceil((double)(focal)/p)); // (focal * w) / p
		int objWidth = 12;
		distance = ((double) (focal * objWidth) / p) / 12;
		System.out.println("Distance to box is " + distance + " feet");

		return distance;
	}
	
	private double getDistance() {
		return distance;
	}
}

package org.usfirst.frc.team2854.vision;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision implements Runnable {
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource outputStream;
	// Mat source;
	// Mat output;

	InterpolatingMap data;
	private Mat img;
	private Scalar upperBoundValue;
	private Scalar lowerBoundValue;
	private final int imgHeight = 1008;
	private final int imgWidth = 756;
	private Double distance;
	private double angle;

	private double distanceToBox;

	private boolean shouldRun = true;

	public boolean hasProcessed = true;

	private CvSource outputStream1;

	private Point center;
	

	public Vision(UsbCamera camera) {
		data = new InterpolatingMap();

		// data.addDataPoint(608d, 1d);
		// data.addDataPoint(70d, 10d);
		// data.addDataPoint(64d, 11d);
		// data.addDataPoint(60d, 12d);
		// data.addDataPoint(367d, 2d);
		// data.addDataPoint(253d, 3d);
		// data.addDataPoint(189d, 4d);
		// data.addDataPoint(158d, 5d);
		// data.addDataPoint(111d, 6d);
		// data.addDataPoint(95d, 7d);
		// data.addDataPoint(87d, 8d);
		// data.addDataPoint(77d, 9d);
		System.out.println("starting to create point list");
		data.addDataPoint(96, 46.5);
		data.addDataPoint(119, 63);
		data.addDataPoint(156, 51);
		data.addDataPoint(270, 35.5);
		data.addDataPoint(365, 22.5);
		data.addDataPoint(553, 16.5);
		data.addDataPoint(282, 29);
		data.addDataPoint(124, 61);
		data.addDataPoint(38, 126.5);
		
		this.camera = camera;

		camera.setResolution(640, 480);
		camera.setExposureAuto();
		camera.setWhiteBalanceAuto();
		System.out.println("Using camera " + camera.getName());
		cvSink = CameraServer.getInstance().getVideo(camera.getName());
		outputStream1 = CameraServer.getInstance().putVideo("Distance", 640, 480);

		System.out.println("Creating object");

	}

	public void run() {
		System.out.println("Starting thread");

		// camera.setResolution(imgWidth, imgHeight);
		// output = new Mat();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		GripPipeline pipeLine = new GripPipeline();
		Mat input = new Mat();
		Mat inputCopy = new Mat();
		try {

			while (true) {

				if (!shouldRun) {
					// System.out.println("Sleeping " + Math.random());
					Thread.sleep(500);
					continue;
				}

				long error = (int) cvSink.grabFrame(input);
				cvSink.grabFrame(inputCopy);
				if (error == 0 || input.empty()) {
					System.out.println("Error grabbing frame or frame is empty, sleeping for .25 seconds");
					System.out.println(cvSink.getError());
					Thread.sleep(250);
					continue;
				}
				SmartDashboard.putNumber("running", Math.random());
				pipeLine.process(input);

				ArrayList<MatOfPoint> contours = pipeLine.findContoursOutput();
				drawContours(contours, inputCopy);
				outputStream1.putFrame(inputCopy);
				if (!contours.isEmpty()) {
					Rect largestContour = getLargestBoundingBox(contours);
					Imgproc.rectangle(inputCopy, largestContour.br(), largestContour.tl(), new Scalar(0 , 255, 0), 3);
					center = new Point(largestContour.x - largestContour.width / 2d,
							largestContour.y - largestContour.height / 2d);
					hasProcessed = true;
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void drawContours(List<MatOfPoint> contours, Mat img) {
		Random r = new Random();
		for (int i = 0; i < contours.size(); i++) {
			Imgproc.drawContours(img, contours, i, new Scalar(0, 255, 0), 2);
		}

	}

	public Mat convertToHsv(Mat img) {
		Mat kernel = Mat.ones(new Size(5, 5), CvType.CV_8U);

		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2HSV);
		Core.inRange(img, lowerBoundValue, upperBoundValue, img);
		Imgproc.erode(img, img, kernel, new Point(0, 0), 2);
		Imgproc.dilate(img, img, kernel, new Point(0, 0), 2);

		return img;
	}

	public Rect getLargestBoundingBox(ArrayList<MatOfPoint> contour) {
		if (!contour.isEmpty()) {
			Rect largest = Imgproc.boundingRect(contour.get(0));
			for (MatOfPoint mat : contour) {
				Rect temp = Imgproc.boundingRect(mat);
				if (temp.area() > largest.area()) {
					largest = temp;
				}
			}

			return largest;
		} else {
			return new Rect();
		}
	}

	public ArrayList<MatOfPoint> aproxPoly(ArrayList<MatOfPoint> contours) {
		ArrayList<MatOfPoint> aprox = new ArrayList<MatOfPoint>();
		for (int i = 0; i < contours.size(); i++) { // for each contour
			MatOfPoint2f approxCurve = new MatOfPoint2f();
			MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

			double approxDistance = Imgproc.arcLength(contour2f, true) * .02; // measure
			// the length of a closed contour
			// curve
			Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true); // connect the points of the contours to
			// approximate a closed polygon

			MatOfPoint points = new MatOfPoint(approxCurve.toArray()); // convert back to matofpoint
			aprox.add(points);
		}

		return aprox;

	}

	public ArrayList<MatOfPoint> getContours(Mat img, Mat drawImg) {
		ArrayList<MatOfPoint> results = new ArrayList<MatOfPoint>();

		Mat hierarchy = new Mat();
		ArrayList<MatOfPoint> contours = new ArrayList<>();

		Imgproc.blur(img, img, new Size(10, 10));
		img.convertTo(img, CvType.CV_8UC1);
		Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Random r = new Random();
		for (int i = 0; i < contours.size(); i++) {
			Imgproc.drawContours(drawImg, contours, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		}

		// for (int i = 0; i < contours.size(); i++) { // for each contour
		// MatOfPoint2f approxCurve = new MatOfPoint2f();
		// MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());
		//
		// double approxDistance = Imgproc.arcLength(contour2f, true) * .02; // measure
		// the length of a closed contour
		// // curve
		// Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true); //
		// connect the points of the contours to
		// // approximate a closed polygon
		//
		// MatOfPoint points = new MatOfPoint(approxCurve.toArray()); // convert back to
		// matofpoint
		// int verticies = points.height();
		// if (verticies == 4) {
		// results.add(points);
		// }
		// }

		return results;
	}

	public Mat getDisplayImage(ArrayList<MatOfPoint> data, Mat inputImg) {
		SmartDashboard.putNumber("dataSize", data.size());
		for (MatOfPoint p : data) {
			Rect rect = Imgproc.boundingRect(p); // create bounding box
			int x = rect.x;
			int y = rect.y;
			int height = rect.height;
			int width = rect.width;
			Imgproc.rectangle(inputImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(255, 255, 255), 3);// draw the
													// box

			// if (height > 60) {
			distance = this.data.getValue((double) height);
			angle = getAngle(imgHeight, imgWidth, x, y, height, width);

			Imgproc.putText(inputImg, "Distance(ft): " + new DecimalFormat("##.##").format(distance),
					new Point(rect.x, rect.y - 20), Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
			Imgproc.putText(inputImg, "Angle(deg) " + new DecimalFormat("##.##").format(angle),
					new Point(rect.x, rect.y - 40), Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
			// }
		}

		return inputImg;
	}

	public ArrayList<Double> getImageData(ArrayList<MatOfPoint> data) {

		ArrayList<Double> imgData = new ArrayList<Double>();

		for (MatOfPoint p : data) {
			Rect rect = Imgproc.boundingRect(p); // create bounding box
			int x = rect.x;
			int y = rect.y;
			int height = rect.height;
			int width = rect.width;

			if (height > 60) {
				distance = this.data.getValue((double) height);
				angle = getAngle(imgHeight, imgWidth, x, y, height, width);

				imgData.add(distance);
				imgData.add(angle);
			}
		}

		return imgData;
	}

	public ArrayList<Double> printSize(Mat hsvImg) {
		int height = 60;
		int width = 0;
		int x = 0;
		int y = 0;

		ArrayList<Double> results = new ArrayList<Double>();

		Mat hierarchy = new Mat();
		ArrayList<MatOfPoint> contours = new ArrayList<>();

		// blur grayscaled/hsv image
		Imgproc.blur(hsvImg, hsvImg, new Size(10, 10));
		hsvImg.convertTo(hsvImg, CvType.CV_8UC1);
		// System.out.println(output.channels() + " " + output.depth());
		Imgproc.findContours(hsvImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		// go through the contours
		// System.out.println(contours.size());
		for (int i = 0; i < contours.size(); i++) { // for each contour
			MatOfPoint2f approxCurve = new MatOfPoint2f();
			MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(i).toArray());

			double approxDistance = Imgproc.arcLength(contour2f, true) * .02; // measure the length of a closed contour
																				// curve
			Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true); // connect the points of the contours to
																				// approximate a closed polygon

			MatOfPoint points = new MatOfPoint(approxCurve.toArray()); // convert back to matofpoint
			int verticies = points.height();
			if (verticies == 4) {
				Rect rect = Imgproc.boundingRect(points); // create bounding box
				x = rect.x;
				y = rect.y;
				height = rect.height;
				width = rect.width;
				Imgproc.rectangle(hsvImg, new Point(rect.x, rect.y),
						new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255), 3);// draw the
																											// box

				if (height > 60) {
					distance = data.getValue((double) height);
					angle = getAngle(imgHeight, imgWidth, x, y, height, width);
					results.add(distance);
					results.add(angle);
					// System.out.println(distance + " at height " + height);
					// System.out.println(angle + " degrees");

					Imgproc.putText(hsvImg, "Distance(ft): " + new DecimalFormat("##.##").format(distance),
							new Point(rect.x, rect.y - 20), Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
					Imgproc.putText(hsvImg, "Angle(deg) " + new DecimalFormat("##.##").format(angle),
							new Point(rect.x, rect.y - 40), Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
				}
			}
		}

		return results;

	}

	public double getAngle(int imgHeight, int imgWidth, int x, int y, int boxH, int boxW) {
		int height = imgHeight - (y + boxH);
		int width = ((x + boxW / 2) - imgWidth / 2); // camera is always at center of frame

		return Math.toDegrees(Math.atan2(width, height)); // angle in degrees

	}

	public void setUpperBoundHue(int hue) {
		this.upperBoundValue = new Scalar(hue, upperBoundValue.val[1], upperBoundValue.val[2]);
	}

	public void setLowerBoundHue(int hue) {
		this.lowerBoundValue = new Scalar(hue, lowerBoundValue.val[1], lowerBoundValue.val[2]);
	}

	public void setUpperBoundValue(Scalar upperBoundValue) {
		this.upperBoundValue = upperBoundValue;
	}

	public void setLowerBoundValue(Scalar lowerBoundValue) {
		this.lowerBoundValue = lowerBoundValue;
	}

	public Scalar getUpperBoundValue() {
		return upperBoundValue;
	}

	public Scalar getLowerBoundValue() {
		return lowerBoundValue;
	}

	public double getDistanceToBox() {
		return distanceToBox;
	}

	public boolean isShouldRun() {
		return shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}

	public boolean isHasProcessed() {
		return hasProcessed;
	}

	public void setHasProcessed(boolean hasProcessed) {
		this.hasProcessed = hasProcessed;
	}

	public Point getCenter() {
		return center;
	}

}

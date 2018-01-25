package org.usfirst.frc.team2854.robot;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

public class Vision implements Runnable {
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource outputStream;
	Mat source;
	Mat output;
	
	InterpolatingMap data;
	private Mat img;
	private Scalar upperBoundValue;
	private Scalar lowerBoundValue;
	private final int imgHeight = 1008;
	private final int imgWidth = 756;
	private Double distance;
	private double angle;
	
	public Vision(Scalar lowerBoundVal, Scalar upperBoundVal) {
		data = new InterpolatingMap();
		
		data.addDataPoint(608d, 1d);
		data.addDataPoint(70d, 10d);
		data.addDataPoint(64d, 11d);
		data.addDataPoint(60d, 12d);
		data.addDataPoint(367d, 2d);
		data.addDataPoint(253d, 3d);
		data.addDataPoint(189d, 4d);
		data.addDataPoint(158d, 5d);
		data.addDataPoint(111d, 6d);
		data.addDataPoint(95d, 7d);
		data.addDataPoint(87d, 8d);
		data.addDataPoint(77d, 9d);
		
		upperBoundValue = upperBoundVal;
		lowerBoundValue = lowerBoundVal;
		
	}

	public void run() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Distance", 640, 480);

		source = new Mat();
		output = new Mat();
		
		while (!Thread.interrupted()) {
			cvSink.grabFrame(source);
			Mat hsv = convertToHsv(source);
			ArrayList<Double> data = printSize(hsv);
			System.out.println(data);
			outputStream.putFrame(output);
		}
	}
	
	public Mat convertToHsv(Mat img) {
		Mat hsvImg = new Mat();

		Imgproc.cvtColor(source, output, Imgproc.COLOR_RGB2HSV);
		Core.inRange(output, lowerBoundValue, upperBoundValue, hsvImg);
		Mat kernel = Mat.ones(new Size(5, 5), CvType.CV_8U);
		Imgproc.erode(hsvImg, hsvImg, kernel, new Point(0, 0), 5);
		Imgproc.dilate(hsvImg, hsvImg, kernel, new Point(0, 0), 5);

		return output;
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
			int verticies = points.height();
			if(verticies == 4) {
				Rect rect = Imgproc.boundingRect(points); // create bounding box
				x = rect.x;
				y = rect.y;
				height = rect.height;
				width = rect.width;
				Imgproc.rectangle(hsvImg, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(255, 255, 255), 3);// draw the box
				
				if(height > 60) {
					distance = data.getValue((double) height);
					angle = getAngle(imgHeight, imgWidth, x, y, height, width);
					results.add(distance);
					results.add(angle);
					System.out.println(distance + " at height " + height);
					System.out.println(angle + " degrees");
					
					Imgproc.putText(hsvImg, "Distance(ft): " + new DecimalFormat("##.##").format(distance), new Point(rect.x, rect.y - 20),
							Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
					Imgproc.putText(hsvImg, "Angle(deg) " + new DecimalFormat("##.##").format(angle), new Point(rect.x, rect.y - 40), 
							Core.FONT_HERSHEY_PLAIN, 1.2, new Scalar(250, 0, 0), 1);
				}
			}
		}
		
		return  results;
		
	}

	public double getAngle(int imgHeight, int imgWidth, int x, int y, int boxH, int boxW) {
		int height = imgHeight - (y + boxH);
		int width = ((x + boxW/2) - imgWidth/2); //camera is always at center of frame
		
		return Math.toDegrees(Math.atan2(width, height)); //angle in degrees
	
	}
	
	
	//IGNOREEEEEE
	public double findDistance(int p) { // some constant parameter of object, use width
		int focal = 683; // f = (image of object width(pixels) * distance(in))/width of object(in); focal
							// is in pixels

		// int distance = (int)(Math.ceil((double)(focal)/p)); // (focal * w) / p
		int objWidth = 12;
		//distance = ((double) (focal * objWidth) / p) / 12;
		System.out.println("Distance to box is " + distance + " feet");

		return 0;
	}
	
}




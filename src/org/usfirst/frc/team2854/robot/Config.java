package org.usfirst.frc.team2854.robot;

/**
 * This is a config file for global variables, such as saftey features and speed constants PID
 * contants are in the PIDConstant class
 */
public class Config {

  public static double manuelSpeedMultiplier = 1;
  public static double totalDriveSpeedMultiplier = 1;

  
  public static double driveEncoderCyclesPerRevolution = 256;  //TODO use this value in speed calculations
  
  public static double highTarget = 8750; // in units per 100 ms
  public static double lowTarget = 3000;
}

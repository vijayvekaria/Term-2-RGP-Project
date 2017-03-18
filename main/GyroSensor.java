package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public final class GyroSensor {
	static Port gyroSensorPort = LocalEV3.get().getPort("S1");
	static EV3GyroSensor gyroSensor = new EV3GyroSensor(gyroSensorPort);
	static SampleProvider gyroSensorProvidor = gyroSensor.getAngleMode();
	static float[] gyroSample = new float[gyroSensorProvidor.sampleSize()];
	
	static float tempAngle;			//Stores a temporary reference angle.
	
	private GyroSensor() {}
	
	/**
	 * Sets the start orientation of the robot to 0 degrees. Sensor measurements are given relative to this orientation.
	 */
	public void initialiseSensor(){
		gyroSensor.reset();
	}
	
	/**
	 * Gets the current angle of sensor relative to the start orientation.
	 * @return A float that is the current angle.
	 */
	public static float getAngle(){
		gyroSensorProvidor.fetchSample(gyroSample, 0);
		return gyroSample[0];
	}
	
	/**
	 * Gets the current angle and stores it in a temporary variable so an angle relative to another orientation can be given without altering the starting orientation.
	 */
	public static void setTempAngle() {
		tempAngle = getAngle();
	}
	
	/**
	 * Gets the temporary reference angle.
	 * @return The angle of a set temporary referenced orientation.
	 */
	public static float getTempAngle() {
		return tempAngle;
	}
	
	/**
	 * Calculates the relative angle to a set temporary orientation.
	 * @return The relative angle to a set temporary reference.
	 */
	public static float getTempAngleDifference(){
		return tempAngle - getAngle();
	}
	
	
	

	

}

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
	
	static float startingAngle;
	static float tempAngle;
	
	private GyroSensor() {}
	
	public static void initialiseGyro(){
		gyroSensorProvidor.fetchSample(gyroSample, 0);
		startingAngle = gyroSample[0];
	}
	
	public static float getStartingAngle() {
		return startingAngle;
	}
	
	public static void getRelativeAngle(){
		gyroSensorProvidor.fetchSample(gyroSample, 0);
		startingAngle = gyroSample[0];
	}
	
	public static void setTempAngle() {
		gyroSensorProvidor.fetchSample(gyroSample, 0);
		tempAngle = gyroSample[0];
	}
	
	public static float getAngleDifference(){
		gyroSensorProvidor.fetchSample(gyroSample, 0);
		return tempAngle - gyroSample[0];
	}
	

}

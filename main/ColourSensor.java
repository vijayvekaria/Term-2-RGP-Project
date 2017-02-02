package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColourSensor {
	Port colourSensorPort = LocalEV3.get().getPort("S3");
	EV3ColorSensor colourSensor = new EV3ColorSensor(colourSensorPort);
	SampleProvider colourSensorProvider = colourSensor.getRedMode();
	float[] colourSample = new float[colourSensorProvider.sampleSize()];
	
	public ColourSensor() {
		
	}
	
	public float getColourReading(){
		colourSensorProvider.fetchSample(colourSample, 0);
		return colourSample[0];
	}
}
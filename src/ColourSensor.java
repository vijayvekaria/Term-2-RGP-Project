package src;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public final class ColourSensor {
	static Port colourSensorPort = LocalEV3.get().getPort("S2");
	static EV3ColorSensor colourSensor = new EV3ColorSensor(colourSensorPort);
	static SampleProvider colourSensorProvider = colourSensor.getRedMode();
	static float[] colourSample = new float[colourSensorProvider.sampleSize()];
	
	private  ColourSensor() {}
	
	public static float getColourReading(){
		colourSensorProvider.fetchSample(colourSample, 0);
		return colourSample[0];
	}
	
	public static boolean getLineReading(){
		float measure = getColourReading();
		if (measure > 0.5){
			return true;
		}
		return false;
	}
}

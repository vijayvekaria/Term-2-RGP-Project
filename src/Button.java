package src;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class Button {
	
	static Port touchPort = LocalEV3.get().getPort("S3");
	static EV3TouchSensor touch = new EV3TouchSensor(touchPort);
	static SampleProvider touchProvider = touch.getTouchMode();	
	static float[] touchSample = new float[touchProvider.sampleSize()];
	
	static boolean ispressed(){
		touchProvider.fetchSample(touchSample, 0);
		return touchSample[0] == 1;
	}
	
	

}

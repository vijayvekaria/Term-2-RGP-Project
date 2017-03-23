package src;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

//pass grid class

public class Goal {
	static Port touchPort = LocalEV3.get().getPort("S3");
	static EV3TouchSensor touch = new EV3TouchSensor(touchPort);
	static SampleProvider touchProvider = touch.getTouchMode();	
	static float[] touchSample = new float[touchProvider.sampleSize()];
	
	static TextLCD screen = LocalEV3.get().getTextLCD();
	static Grid grid;
	
	static long timeDif;
	
	static int angle = 45;
	//int distance = 4000; //seconds to use in thread.sleep

	public static void enterGoal(Grid inputGrid){
		grid = inputGrid;
		//screen.drawString("Entering goal", 1, 1);
		
		Driver.rotateTo(angle);
		
		touchProvider.fetchSample(touchSample, 0);
		timeDif = System.currentTimeMillis();
		
		Driver.setMotorSpeed(40);
		Driver.moveForward();
		
		while(touchSample[0] == 0){
			touchProvider.fetchSample(touchSample, 0);
		}
		
		timeDif = System.currentTimeMillis() - timeDif; 
		
		Driver.stop();
		readColour();
		exitGoal();
	}
	
	private static void readColour(){
		ColourSensor.setRGBMode();
		if(ColourSensor.getGreenReading() > 0.1){
			grid.setGreen();
			//screen.drawString("Green identified", 1, 2);
		}
		else {
			grid.setRed();
			//screen.drawString("Red identified", 1, 2);	
		}
	}
	
	private static void exitGoal(){
		//screen.drawString("Backing out", 1, 3);
		Driver.setMotorSpeed(40);
		Driver.moveBackward();
		try { Thread.sleep(timeDif);} catch (InterruptedException e) {e.printStackTrace();}
		Driver.stop();
		//screen.drawString("Rotating out", 1, 4);
		Driver.rotateTo(180);
	}
}



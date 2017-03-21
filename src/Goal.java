package src;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.internal.ev3.EV3LCD;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
//import src.Driver;

//pass grid class

public class Goal 
{

	Port touchPort = LocalEV3.get().getPort("S3");
	EV3TouchSensor touch = new EV3TouchSensor(touchPort);
	SampleProvider touchProvider = touch.getTouchMode();	
	float[] touchSample = new float[touchProvider.sampleSize()];
	
	Port colourSensorPort = LocalEV3.get().getPort("S2");
	EV3ColorSensor colourSensor = new EV3ColorSensor(colourSensorPort);
	SampleProvider colourSensorProvider = colourSensor.getRGBMode();
	float[] colourSample = new float[colourSensorProvider.sampleSize()];
	
	TextLCD screen = LocalEV3.get().getTextLCD();
	Grid grid;
	
	long timeDif;
	
	int angle = 45;
	//int distance = 4000; //seconds to use in thread.sleep
	
	public Goal(Grid grid) 
	{
		this.grid = grid;
		enterGoal();
		screen.drawString("Entering goal", 1, 1);
	}

	private void enterGoal() 
	{
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
	
	private void readColour() 
	{
		colourSensorProvider.fetchSample(colourSample, 0);
		
		
		if(colourSample[1] > 0.1)
		{
			grid.setGreen();
			screen.drawString("Green identified", 1, 2);
		}
		else
		{
			
			grid.setRed();
			screen.drawString("Red identified", 1, 2);	
			
		}
	}
	
	private void exitGoal() 
	{
		screen.drawString("Backing out", 1, 3);
		Driver.setMotorSpeed(40);
		
		Driver.moveBackward();
		
		try {
			Thread.sleep(timeDif);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Driver.stop();
		
		screen.drawString("Rotating out", 1, 4);
		Driver.rotateTo(180);

	}
	
}



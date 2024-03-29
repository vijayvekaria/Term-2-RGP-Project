package testing;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class MotorTest 
{
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static DifferentialPilot dPilot = new DifferentialPilot(56, 120, leftMotor, rightMotor);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	Port gyroPort = LocalEV3.get().getPort("S1");
	EV3GyroSensor gyro = new EV3GyroSensor(gyroPort);
	SampleProvider gyroProvider = gyro.getAngleMode();	
	float[] gyroSample = new float[gyroProvider.sampleSize()];
	
	TextLCD screen = LocalEV3.get().getTextLCD();
	 
	public static void main(String[] args) 
	{
		MotorTest test = new MotorTest();
		
		//test.forwardTest();
		//test.backwardTest();
		//test.rotationTest();
		test.advancedRotationTest();
	}

	private void rotationTest() //measuring location error based on location of colour sensor
	{
		for(int i = 0; i < 4; i++)
 		{
 			leftMotor.setSpeed(307); //(614/2) which is 30cm per 2 seconds
 			rightMotor.setSpeed(307);
 			
 			leftMotor.synchronizeWith(syncList);
 			leftMotor.startSynchronization();
 			
 			leftMotor.forward();
 			rightMotor.forward();
 			
 			leftMotor.endSynchronization();
 			
 			try {
 				Thread.sleep(2000);
 			} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			
 			leftMotor.synchronizeWith(syncList);
 			leftMotor.startSynchronization();
 			
 			leftMotor.stop();
 			rightMotor.stop();
 			
 			leftMotor.endSynchronization();
			
			rotate(1); //1 == clockwise
			
			try {
				Thread.sleep(10000); //waits 10 seconds facing new direction
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
	
	}

	public void rotate(int direction) {
		
		gyro.reset();
		screen.clear();
		
		leftMotor.setSpeed(40);
		rightMotor.setSpeed(40);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		gyroProvider.fetchSample(gyroSample, 0);
		
		if (direction == 1)//CLOWCKWISE
		{ 
			leftMotor.forward();
			rightMotor.backward();
		} 
		else  //ANTICLOCKWISE
		{
			leftMotor.backward();
			rightMotor.forward();
		}
		
		leftMotor.endSynchronization();
		
		if (direction == 1)
		{
			while(gyroSample[0] <= 87)
			{
				screen.drawString(gyroSample[0] + "", 0, 1);
				gyroProvider.fetchSample(gyroSample, 0);
			}
		} 
		else 
		{
			while(gyroSample[0] >= -87)
			{
				screen.drawString(gyroSample[0] + "", 0, 1);
				gyroProvider.fetchSample(gyroSample, 0);
			}
		}

		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
	}
	
	public void advancedRotationTest() {
		//first 50cm northbound
		leftMotor.setSpeed(552); //50cm per 2 seconds
		rightMotor.setSpeed(552);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
			
		leftMotor.forward();
		rightMotor.forward();
			
		leftMotor.endSynchronization();
			
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
		
		
		//rotate 90 anticlock
		
		rotate(0); //0 ==  anticlockwise
		
		//second 30cm westbound
		
		leftMotor.setSpeed(307); //30cm per 2 seconds
		rightMotor.setSpeed(307);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
			
		leftMotor.forward();
		rightMotor.forward();
			
		leftMotor.endSynchronization();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
		
		
		//rotate 45 degrees clockwise
		
		gyro.reset();
		
		leftMotor.setSpeed(40);
		rightMotor.setSpeed(40);
		
		gyroProvider.fetchSample(gyroSample, 0);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
			
		leftMotor.forward();
		rightMotor.backward();
			
		leftMotor.endSynchronization();
		
		while(gyroSample[0] <= 135) {
			screen.clear();
			screen.drawString(gyroSample[0] + "", 0, 1);
			gyroProvider.fetchSample(gyroSample, 0);
		}
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
		
		//30cm northeast bound
		
		leftMotor.setSpeed(307); //30cm per 2 seconds
		rightMotor.setSpeed(307);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
			
		leftMotor.forward();
		rightMotor.forward();
			
		leftMotor.endSynchronization();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		
		leftMotor.stop();
		rightMotor.stop();
		
		leftMotor.endSynchronization();
		
		//measure location error at the final destination
	}

	public void forwardTest() {
		
		leftMotor.setSpeed(41); //2cm per second
		rightMotor.setSpeed(41);
		
		for(int i = 0; i < 10; i++)
		{
			leftMotor.synchronizeWith(syncList);
			leftMotor.startSynchronization();
			
			leftMotor.forward();
			rightMotor.forward();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			leftMotor.startSynchronization();
			
			leftMotor.stop();
			rightMotor.stop();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void backwardTest() {
		
		leftMotor.setSpeed(41); //2cm per second
		rightMotor.setSpeed(41);
		
		for(int i = 0; i < 10; i++)
		{
			leftMotor.synchronizeWith(syncList);
			leftMotor.startSynchronization();
			
			leftMotor.backward();
			rightMotor.backward();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			leftMotor.startSynchronization();
			
			leftMotor.stop();
			rightMotor.stop();
			
			leftMotor.endSynchronization();
			
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}

package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class RotationTest {
	
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	static int error = 2;
	
	public static void main(String[] args) {
//		rotateTo(180);
//		rotateTo(0);
//		rotateTo(90);
//		rotateTo(-90);
		//rotateTo(180);
		rotateTo(0);
		rotateTo(0);
		rotateTo(180);
		rotateTo(180);
		rotateTo(90);
		rotateTo(90);
		rotateTo(-90);
		rotateTo(-90);
		rotateTo(0);
		rotateTo(90);
		rotateTo(180);
		rotateTo(-90);
		
	}
	
	public static void rotateTo(int targetAngle){
		int currentAngle = (int) GyroSensor.getAngle();
		setMotorSpeed(110);
		try {Thread.sleep(1000);} catch (InterruptedException e) {}

		if (targetAngle == currentAngle){
			return;
		} else if (targetAngle == 180){
			if (currentAngle < 0){
				targetAngle = -180;
				startAntiClockwiseRotation();
				while (currentAngle > targetAngle + error){
					currentAngle = (int) GyroSensor.getAngle();
				}
				stop();
			} else {
				startClockwiseRotation();
				while (currentAngle < targetAngle - error){
					currentAngle = (int) GyroSensor.getAngle();
				}
				stop();
			}
		} else if (currentAngle < targetAngle){
			startClockwiseRotation();
			while (currentAngle < targetAngle - error){
				currentAngle = (int) GyroSensor.getAngle();
			}
			stop();
		} else {
			startAntiClockwiseRotation();
			while (currentAngle > targetAngle + error){
				currentAngle = (int) GyroSensor.getAngle();
			}
			stop();
		}
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
	}
	
	private static void startClockwiseRotation(){
		startSync();
		leftMotor.forward();
		rightMotor.backward();
		endSync();
	}
	
	private static void startAntiClockwiseRotation(){
		startSync();
		leftMotor.backward();
		rightMotor.forward();
		endSync();
	}
	
	public static void setMotorSpeed(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}
	
	private static void endSync() {
		leftMotor.endSynchronization();
	}
	
	private static void startSync() {
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
	}
	
	public static void stop() {
		startSync();
		leftMotor.stop();
		rightMotor.stop();
		endSync();
	}
}

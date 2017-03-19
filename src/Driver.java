package src;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

public final class Driver{
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	/**
	 * The number of degrees to adjust rotation from a given point.
	 */
	int rotationErrorThreshold = 3;

	private Driver() {}
	
	/**
	 * Rotates robot to a specific orientation given a bearing (0 - 360).
	 * @param degrees The bearing to rotate to.
	 */
	public void rotateTo(int degrees){
		int currentAngle = currentAngleTo360();
		int rotateAngleDifference = degrees - currentAngle;
		
		setMotorSpeed(40);
		
		if (rotateAngleDifference <=  (360 - degrees) + currentAngle){
			rotateAngleDifference = 360 - ((360 - degrees + currentAngle) % 360);
			startClockwiseRotation();
			while(rotateAngleDifference > rotationErrorThreshold){
				currentAngle = currentAngleTo360();
				rotateAngleDifference = 360 - ((360 - degrees + currentAngle) % 360);
			}
		} else {
			rotateAngleDifference = (360 - degrees + currentAngle) % 360;
			startAntiClockwiseRotation();
			while(rotateAngleDifference > rotationErrorThreshold){
				currentAngle = currentAngleTo360();
				rotateAngleDifference = (360 - degrees + currentAngle) % 360;
			}
		}
		stop();
	}

	private int currentAngleTo360() {
		int currentAngle = 10;
		System.out.println("input: " + currentAngle);
		if (currentAngle < 0){
			currentAngle += 360;
		}
		System.out.println("output: " + currentAngle);
		return currentAngle;
	}
	
	private void startClockwiseRotation(){
		startSync();
		leftMotor.forward();
		rightMotor.backward();
		endSync();
	}
	
	private void startAntiClockwiseRotation(){
		startSync();
		leftMotor.backward();
		rightMotor.forward();
		endSync();
	}
	
	private static void setMotorSpeed(int speed){
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

	private static void moveForward() {
		startSync();
		leftMotor.forward();
		rightMotor.forward();
		endSync();
	}

	private static void stop() {
		startSync();
		leftMotor.stop();
		rightMotor.stop();
		endSync();
	}

	public static void moveToNextLineSegment() {
		setMotorSpeed(41);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static void goToLineSegment(int location){
		Driver.moveToLocation(location+1);
	}
	
	public static void moveToLocation(int location){
		setMotorSpeed(41);	
		moveForward();	
		try { Thread.sleep(1000*(30-location)); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
	}
}

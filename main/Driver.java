package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

public final class Driver{
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	static DifferentialPilot dPilot = new DifferentialPilot(56, 125, leftMotor, rightMotor);
	
	private Driver() {}
	
	private static void stop() {
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
	}

	private static void moveForward() {
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
	}
	
	public static void moveToNextLineSegment() {
		leftMotor.setSpeed((int) 41);
		rightMotor.setSpeed((int) 41);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	
	public static void moveToLocation(int location){
		leftMotor.setSpeed((int) 41);
		rightMotor.setSpeed((int) 41);	
		moveForward();	
		try { Thread.sleep(1000*(30-location)); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
	}
	
	public static void goToLineSegment(int location){
		Driver.moveToLocation(location+1);
	}
	
	public static void moveToNextCell() { //moves forward or backwards		
		GyroSensor.setTempAngle();		
		leftMotor.setSpeed((int) 102);
		rightMotor.setSpeed((int) 102);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		float angleDiff = GyroSensor.getAngleDifference();
		if (-3 > angleDiff  || angleDiff > 3){
			dPilot.rotate(angleDiff);
		}
	}
	
	public void rotate(int degrees){
		GyroSensor.setTempAngle();
		dPilot.rotate(degrees);
		float angleDiff = GyroSensor.getAngleDifference();
		//To Be Completed.
	}
}

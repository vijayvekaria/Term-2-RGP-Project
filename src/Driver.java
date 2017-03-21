package src;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

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
	static int rotationErrorThreshold = 3;

	private Driver() {}
	
	public void traversePath(ArrayList<Cell> path){
		Cell currentCell;
		Cell previousCell = path.get(0);
		for (int i = 1; i < path.size() - 1; ++i){
			currentCell = path.get(i);
			if (currentCell.getX() == previousCell.getX()){
				if (currentCell.getY() < previousCell.getY()){
					rotateTo(getCurrentAngleTo360() - 90);
					moveToNextCell();
				} else {
					rotateTo(getCurrentAngleTo360() + 90);
					moveToNextCell();
				}
			} else {
				if (currentCell.getX() < previousCell.getX()){
					rotateTo(getCurrentAngleTo360() + 180);
					moveToNextCell();
				} else {
					moveToNextCell();
				}
			}
			previousCell = currentCell;
		}
	}
	
	/**
	 * Rotates robot to a specific orientation given a bearing (0 - 360).
	 * @param degrees The bearing to rotate to.
	 */
	public static void rotateTo(int degrees){
		int currentAngle = getCurrentAngleTo360();
		int rotateAngleDifference = degrees - currentAngle;
		setMotorSpeed(40);
		if (rotateAngleDifference <=  (360 - degrees) + currentAngle){
			rotateAngleDifference = 360 - ((360 - degrees + currentAngle) % 360);
			startClockwiseRotation();
			while(rotateAngleDifference > rotationErrorThreshold){
				currentAngle = getCurrentAngleTo360();
				rotateAngleDifference = 360 - ((360 - degrees + currentAngle) % 360);
			}
		} else {
			rotateAngleDifference = (360 - degrees + currentAngle) % 360;
			startAntiClockwiseRotation();
			while(rotateAngleDifference > rotationErrorThreshold){
				currentAngle = getCurrentAngleTo360();
				rotateAngleDifference = (360 - degrees + currentAngle) % 360;
			}
		}
		stop();
	}

	private static int getCurrentAngleTo360() {
		int currentAngle = (int) GyroSensor.getAngle();
		return currentAngle % 360;
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

	public static void moveForward() {
		startSync();
		leftMotor.forward();
		rightMotor.forward();
		endSync();
	}
	
	public static void moveBackward() {
		startSync();
		leftMotor.backward();
		rightMotor.backward();
		endSync();
	}

	public static void stop() {
		startSync();
		leftMotor.stop();
		rightMotor.stop();
		endSync();
	}
	
	private static void moveToNextCell(){
		setMotorSpeed(51);
		moveForward();
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
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

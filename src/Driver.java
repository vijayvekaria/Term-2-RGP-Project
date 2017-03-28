package src;

import java.util.ArrayList;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public final class Driver{
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static RegulatedMotor[] syncList = {rightMotor};
	
	/**
	 * The number of degrees to adjust rotation from a given point.
	 */
	static int error = 2;

	private Driver() {}
	
	public static void traversePath(ArrayList<int[]> path){
		for (int i = 0; i < path.size(); ++i){
			rotateTo(path.get(i)[0]);
			moveCells(path.get(i)[1]);
		}
	}
	
	/**
	 * Rotates robot to a specific orientation given a bearing (0 - 360).
	 * @param degrees The bearing to rotate to.
	 */
	public static void rotateTo(int targetAngle){
		System.out.println("Rotating to: " + targetAngle);
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
		try {Thread.sleep(50);} catch (InterruptedException e) {}
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
	
	private static void moveCells(int numberOfCells){
		setMotorSpeed(102);
		moveForward();
		try { Thread.sleep(numberOfCells * 1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static void moveCellsBack(int numberOfCells){
		setMotorSpeed(102);
		moveBackward();
		try { Thread.sleep(numberOfCells * 1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static void moveToNextLineSegment() {
		setMotorSpeed(41);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static void goToLineSegment(int location){
		location += 1;
		setMotorSpeed(164);	
		moveBackward();	
		try { Thread.sleep(250*(location-12)); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
	}
}

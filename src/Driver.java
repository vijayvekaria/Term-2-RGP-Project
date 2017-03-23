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
	static int rotationErrorThreshold = 3;

	private Driver() {}
	
	public static void traversePath(ArrayList<Cell> path){
		Cell currentCell;
		Cell previousCell = path.get(0);
		for (int i = 1; i < path.size(); ++i){
			currentCell = path.get(i);
			int prevX = previousCell.getX();
			int prevY = previousCell.getY();
			int currX = currentCell.getX();
			int currY = currentCell.getY();
			if (prevY == currY){
				if (prevX < currX){
					System.out.println("Rotating 90");
					rotateTo(90);
				} else {
					System.out.println("Rotating -90");
					rotateTo(-90);
				}
			} else {
				if (prevY > currY){
					System.out.println("Rotating 180");
					rotateTo(180);
				} else { 
					System.out.println("Rotating 0");
					rotateTo(0);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			moveToNextCell();
			previousCell = currentCell;
		}
	}
	
	/**
	 * Rotates robot to a specific orientation given a bearing (0 - 360).
	 * @param degrees The bearing to rotate to.
	 */
	public static void rotateTo(int degrees){
		
		int errorAdjustment = 0;
		int currentAngle = (int) GyroSensor.getAngle();
		
		if(currentAngle == degrees){
			System.out.println("already facing correct direction");

			return;
		}
		setMotorSpeed(110);
		switch(degrees){
			case -90:
				while (degrees + errorAdjustment < currentAngle){
					startAntiClockwiseRotation();
					currentAngle = (int) GyroSensor.getAngle();
				}
				//stop();
				break; 
			case 0:
				if (currentAngle < 0) {
					while (currentAngle < degrees){
						startClockwiseRotation();
						currentAngle = (int) GyroSensor.getAngle();
					}
					//stop();
					break; 
				} else {
					while (currentAngle > degrees){
						startAntiClockwiseRotation();
						currentAngle = (int) GyroSensor.getAngle();
					}
					//stop();
					break; 
				}
			case 90:
				while (currentAngle < degrees - errorAdjustment){
					startClockwiseRotation();
					currentAngle = (int) GyroSensor.getAngle();
				}
				//stop();
				break; 
			case 180:
				if (currentAngle < 0) {
					while (currentAngle > -degrees + errorAdjustment){
						startAntiClockwiseRotation();
						currentAngle = (int) GyroSensor.getAngle();
					}
					//stop();
					GyroSensor.initialiseSensor();
					break; 
				} else {
					while (currentAngle < degrees - errorAdjustment){
						startClockwiseRotation();
						currentAngle = (int) GyroSensor.getAngle();
					}
					//stop();
					break; 
				}
				
			case 45:
				while (currentAngle < degrees){
					startClockwiseRotation();
					currentAngle = (int) GyroSensor.getAngle();
				}
				//stop();
				break; 
		}
		stop();
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
		setMotorSpeed(102);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public static void moveToNextLineSegment() {
		setMotorSpeed(41);
		moveForward();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static void goToLineSegment(int location){
		location += 1;
		setMotorSpeed(82);	
		moveBackward();	
		try { Thread.sleep(500*(location-11)); } catch (InterruptedException e) { e.printStackTrace(); }
		stop();
	}
}

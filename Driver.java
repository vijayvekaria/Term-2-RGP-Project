package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Driver extends DifferentialPilot {
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
	
	RegulatedMotor[] syncList = {rightMotor};
	
	public Driver() {
		super(56, 125, leftMotor, rightMotor);
		
	}
	
	public void moveToNextLineSegment() {
		leftMotor.setSpeed((int) 41);
		rightMotor.setSpeed((int) 41);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
		
		try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public void moveToLocation(int location)
	{
		leftMotor.setSpeed((int) 41);
		rightMotor.setSpeed((int) 41);
		
		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.forward();
		rightMotor.forward();
		leftMotor.endSynchronization();
		
		try { Thread.sleep(1000*(30-location)); } catch (InterruptedException e) { e.printStackTrace(); }

		leftMotor.synchronizeWith(syncList);
		leftMotor.startSynchronization();
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.endSynchronization();
		
		//try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
	}
}

package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class Driver extends DifferentialPilot {
	static RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	static RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);

	public Driver() {
		super(56, 56, leftMotor, rightMotor);
	}
	
	public void moveToNextLineSegment(){
		this.setTravelSpeed(40.9);
		this.forward();
		Delay.msDelay(1000);
		this.stop();
	}

}

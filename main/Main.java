package main;

public class Main {
	Driver driver;
	ColourSensor cSensor;
	Localiser localiser;
	
	public Main() {
		driver = new Driver();
		cSensor = new ColourSensor();
		localiser = new Localiser(driver, cSensor);
		localiser.start();
	}
}

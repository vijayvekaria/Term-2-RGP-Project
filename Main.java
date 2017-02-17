package main;

public class Main {
	Driver driver;
	ColourSensor cSensor;
	Localiser localiser;
	
	public Main() {
		Grid grid = new Grid();
		driver = new Driver();
		cSensor = new ColourSensor();
		localiser = new Localiser(driver, cSensor);
		localiser.start();
	}
	public static void main(String[] args) {
		Main main = new Main();
	}
}

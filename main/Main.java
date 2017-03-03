package main;

public class Main {
	public Main() {
		Grid grid = new Grid();
		Localiser.start();
		GyroSensor.initialiseGyro();
		
	}
	public static void main(String[] args) {
		Main main = new Main();
	}
}

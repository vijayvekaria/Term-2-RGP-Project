package src;

public class Main {
	public Main() {
		Grid grid = new Grid();
		Localiser.start();
		GyroSensor.initialiseSensor();
		
	}
	public static void main(String[] args) {
		Main main = new Main();
	}
}

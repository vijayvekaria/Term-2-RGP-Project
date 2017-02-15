package main;

public class Localiser {
	
	Boolean[] line = {false, true, false, true, true, false, false, true, false, false, 
			true, true, false, false, false, true, false, false, false, true,
			true, false, false, false, true, true, true, false, false, false, 
			false, true, true, true};
	
	double[] probabilities = new double[34];
	
	double moveWork = 0.99;
	double sensorWorks = 0.99;
	
	Driver driver;
	ColourSensor cSensor;
	
	public Localiser(Driver driver, ColourSensor cSensor) {
		this.driver = driver;
		this.cSensor = cSensor;
		
		for (int i = 0; i < 34; ++i){
			probabilities[i] = 1.0 / 34.0;
		}
	}
	
	public void start(){
		Integer location = null;
		while (location == null){
			update();
			normaliseProbability();
			moveRobot();
			normaliseProbability();
			location = checkProbability();
		}
	}
	
	private void update(){
		boolean measure = cSensor.getLineReading();
		for (int i = 0; i < 34; ++i){
			if (line[i] == measure){
				probabilities[i] = probabilities[i] * sensorWorks ;
			} else {
				probabilities[i] = probabilities[i] * (1 - sensorWorks) ;
			}
		}
	}
	
	private void moveRobot(){
		driver.moveToNextLineSegment();
		for (int i = 33; i >0; --i){
			probabilities[i] = (probabilities[i - 1] * moveWork) + (probabilities[i] * (1-moveWork));
		}
	}

	private void normaliseProbability(){
		double sum = 0;
		for (double value : probabilities){
			sum += value;
		}
		for(int i = 0; i < 34; ++i){
			probabilities[i] = probabilities[i] / sum;
		}
	}
	
	private Integer checkProbability(){
		for(int i = 0; i < 34; ++i){
			if (probabilities[i] > 0.90){
				return i;
			}
		}
		return null;
	}
}

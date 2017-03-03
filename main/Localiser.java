package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;

public class Localiser {
	
	static Boolean[] line = {false, true, false, true, true, false, false, true, false, false, 
			true, true, false, false, false, true, false, false, false, true,
			true, false, false, false, true, true, true, false, false, false, 
			false, true, true, true};
	
	static double[] probabilities = new double[34];
	
	static double moveWork = 0.99;
	static double sensorWorks = 0.99;

	static TextLCD ev3Screen = LocalEV3.get().getTextLCD();
	
	private Localiser() {}

	private static void generateLine() {
		for (int i = 0; i < 34; ++i){
			probabilities[i] = 1.0 / 34.0;
		}
	}
	
	public static void start(){
		generateLine();
		Integer location = null;
		while (location == null){
			update();
			normaliseProbability();
			moveRobot();
			normaliseProbability();
			location = checkProbability();
		}	
		Driver.goToLineSegment(location);
	}
	
	private static void update(){
		boolean measure = ColourSensor.getLineReading();
		for (int i = 0; i < 34; ++i){
			if (line[i] == measure){
				probabilities[i] = probabilities[i] * sensorWorks ;
			} else {
				probabilities[i] = probabilities[i] * (1 - sensorWorks) ;
			}
		}
	}
	
	private static void moveRobot(){
		Driver.moveToNextLineSegment();
		for (int i = 33; i > 0; --i){
			probabilities[i] = (probabilities[i - 1] * moveWork) + (probabilities[i] * (1-moveWork));
		}
	}

	private static void normaliseProbability(){
		double sum = 0;
		for (double value : probabilities){
			sum += value;
		}
		for(int i = 0; i < 34; ++i){
			probabilities[i] = probabilities[i] / sum;
		}
	}
	
	private static Integer checkProbability(){
		for(int i = 0; i < 34; ++i){
			if (probabilities[i] > 0.90){
				return i;
			}
		}
		return null;
	}
}
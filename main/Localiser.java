package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Localiser {
	Boolean[] line = {false, true, false, true, true, false, false, true, false, false, 
					true, true, false, false, false, true, false, false, false, true,
					true, false, false, false, true, true, true, false, false, false, 
					false, true, true, true};
	HashMap<Integer, ArrayList<Double>> probabilities  = new HashMap<Integer, ArrayList<Double>>();
	ArrayList<Boolean> previouslyVisited;
	
	Driver driver;
	ColourSensor cSensor;
	
	public Localiser(Driver driver, ColourSensor colourSensor) {
		this.driver = driver;
		this.cSensor = colourSensor;
		for (int i = 1; i < 35; ++i){
			probabilities.put(i, new ArrayList<Double>());
		}
	}

	public int start(){
		Integer location = checkLocation();
		while(location == null){
			driver.moveToNextLineSegment();
			if (cSensor.getColourReading() > 0.5){
				previouslyVisited.add(true);
			} else {
				previouslyVisited.add(false);
			}
			updateProbabilities();
			location = checkLocation();
		}
		return location;
	}
	
	private void updateProbabilities(){
		for (int i = 1; i < 35; ++i){
			probabilities.get(i).add(calculateProbability(i));
		}
	}
	
	private Integer checkLocation(){
		for (int i = 1; i < 35; ++i){
			if (probabilities.get(i).get(probabilities.get(i).size() - 1) == 1){
				return i;
			}
		}
		return null;
	}
	
	private double calculateProbability(Integer step){
		int count = 0;
		Boolean[] copy = Arrays.copyOfRange(line, 0, line.length);
		while (copy.length > previouslyVisited.size()){
			Arrays.asList(line);
			int indexOfPattern = Collections.indexOfSubList(Arrays.asList(line), previouslyVisited);
			if (indexOfPattern == -1){
				break;
			} else {
				count =+ 1;
				copy = Arrays.copyOfRange(line, indexOfPattern, line.length);
			}
		}
		if (count == 0){
			return 0;
		} else {
			return (1 / count);
		}
	}
}

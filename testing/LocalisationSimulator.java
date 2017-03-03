package testing;

public class LocalisationSimulator {
	
	Boolean[] line = {false, true, false, true, true, false, false, true, false, false, 
			true, true, false, false, false, true, false, false, false, true,
			true, false, false, false, true, true, true, false, false, false, 
			false, true, true, true};
	double[] probabilities = new double[34];
	
	double moveWork = 0.99;
	double sensorWorks = 0.99;
	
	public static void main(String[] args) {
		LocalisationSimulator test = new LocalisationSimulator();
	}
	
	public LocalisationSimulator() {
		for (int i = 0; i < 34; ++i){
			probabilities[i] = 1.0 / 34.0;
		}
		
		int counter = 8;
		int steps = 0;
		Integer location = null;
		
		while (location == null){
			update(line[counter]);
			normaliseProbability();
			moveRobot();
			normaliseProbability();
			location = checkProbability();
			counter++;
			steps++;
		}
		System.out.println("found location " + counter + "   after steps: " + steps);
	}
	
	private void update(boolean measure){
		for (int i = 0; i < 34; ++i){
			if (line[i] == measure){
				probabilities[i] = probabilities[i] * sensorWorks ;
			} else {
				probabilities[i] = probabilities[i] * (1 - sensorWorks) ;
			}
		}
	}
	
	private void moveRobot(){
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
	
	private void printProbabilities() {
		double sum = 0;
		for (double value : probabilities){
			sum += value;
		}
		System.out.print("SUM: " + sum + ", [");
		for (double i : probabilities){
			System.out.print(i + ", ");
		}
		System.out.print("] \n");
		
	}
	
	private void getCSV(){
		for (int i = 0; i < 34; ++i){
			System.out.print(i + "," + probabilities[i] + "\n");
		}
		
	}
}

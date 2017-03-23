package src;

import java.util.ArrayList;


public class Main {
	public Main() {
		Grid grid = new Grid();
		
		Localiser.start();
		
		GyroSensor.initialiseSensor();
		
		ArrayList<Cell> path = null;
		try { path =  PathPlanner.GetOptimalPath(grid, grid.getCell(17, 4), grid.getCell(10,21)); } catch (PathNotFoundException e) {System.out.println("No path found no.1"); }
		
		Driver.traversePath(path);
		
		Goal.enterGoal(grid);
		
		
		try { path =  PathPlanner.GetOptimalPath(grid, grid.getCell(10,21), grid.getCell(17, 4)); } catch (PathNotFoundException e) {System.out.println("No path found no.2");}
		Driver.traversePath(path);
		
	}
	public static void main(String[] args) {
		Main main = new Main();
	}
}

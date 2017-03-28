package src;

import java.io.File;
import java.util.ArrayList;

import lejos.hardware.Sound;


public class Main {
	public Main() {
		GyroSensor.initialiseSensor();
		Grid grid = new Grid();
		
		
		
		Localiser.start();
		
		
		
		ArrayList<int[]> path = null;
		try { path =  PathPlanner.GetOptimalPath(grid, grid.getCell(17, 3), grid.getCell(10,21)); } catch (PathNotFoundException e) {System.out.println("No path found no.1"); }
		
		Driver.traversePath(path);
		
		Driver.rotateTo(-45);
		Driver.setMotorSpeed(70);
		Driver.moveForward();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Driver.stop();
		
		Goal.enterGoal(grid);
		
		Driver.rotateTo(-45);
		Driver.setMotorSpeed(70);
		Driver.moveBackward();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Driver.stop();
		
		Driver.rotateTo(0);
		Driver.moveCellsBack(2);
		
//		Driver.rotateTo(-90);
//		Driver.moveCellsBack(2);
		Driver.rotateTo(180);
//		GyroSensor.initialiseSensor();
		
		
		try { path =  PathPlanner.GetOptimalPath(grid, grid.getCell(10,19), grid.getCell(17, 4)); } catch (PathNotFoundException e) {System.out.println("No path found no.2");}
		Driver.traversePath(path);
		
		Driver.rotateTo(180);
		
		Driver.setMotorSpeed(40);
		Driver.moveForward();
		
		while(!Button.ispressed()){
			
		}
		Driver.stop();
		
//		File fuck = new File("src\fuck.wav");
//		System.out.println(fuck.exists());
//		System.out.println(Sound.playSample(fuck, 100));
		
		Sound.beep();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Main main = new Main();
	}
}

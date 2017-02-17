package main;

import lejos.robotics.RegulatedMotor;

public class Cell {
	int x;
	int y;
	int costToGoal;
	
	boolean obstacleOn = false;
	boolean robotOn = false;
	
	public Cell(int x, int y, int cost) {
		this.x = x;
		this.y = y;
		this.costToGoal = cost;
	}
	

	public void setObstacleOn(boolean obstacleOn) {
		this.obstacleOn = obstacleOn;
	}
	public void setRobotOn(boolean onOff){
		this.robotOn = onOff;
	}
	public void setCostToGoal(int costToGoal) {
		this.costToGoal = costToGoal;
	}
	
	public int getCostToGoal() {
		return costToGoal;
	}
	public boolean getObstacleOn(){
		return obstacleOn;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}

package main;

import java.util.ArrayList;

import lejos.robotics.RegulatedMotor;

public class Cell {
	private Position<Integer, Integer> pos;
	private int hN;
	private int fN;
	private int gN;
	
	//possible extra stuff
	private boolean traversable;
	private Cell cameFrom;
	private ArrayList<Cell > canGoTo;
	
	
//	boolean obstacleOn = false;
//	boolean robotOn = false;
	
	public Cell(Integer x, Integer y, int cost) {
		this.pos = new Position(x, y);
		this.hN = cost;
	}
	
	


//	public void setObstacleOn(boolean obstacleOn) {
//		this.obstacleOn = obstacleOn;
//	}
//	public void setRobotOn(boolean onOff){
//		this.robotOn = onOff;
//	}
	
	public void sethN(int costToGoal) {
		this.hN = costToGoal;
	}
	
	
	public void setgN(int gN) {
		this.gN = gN;
		fN = gN + hN;
	}
//	public boolean getObstacleOn(){
//		return obstacleOn;
//	}
	
	public int getX() {
		return pos.getX();
	}
	public int getY() {
		return pos.getY();
	}
	
	public int gethN() {
		return hN;
	}
	
	public int getfN() {
		return fN;
	}
	
	public int getgN() {
		return gN;
	}
	
	public Cell getCameFrom() {
		return cameFrom;
	}
	public ArrayList<Cell> getCanGoTo() {
		return canGoTo;
	}
	public boolean getTraversable(){
		return traversable;
	}
	public void setCameFrom(Cell cameFrom) {
		this.cameFrom = cameFrom;
	}
	//not needed
//	public void setCanGoTo(ArrayList<Cell> canGoTo) {
//		this.canGoTo = canGoTo;
//	}
	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}
	
	public void addToCanGoTo(Cell cell){
		canGoTo.add(cell);
	}
}

package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class PathPlanner {

	
	
	public static LinkedList<Cell> GetOptimalPath(Grid grid, Cell start, Cell end) throws PathNotFoundException
	{
		ArrayList<Cell> openList = new ArrayList<Cell>();
		//adds all possible cells around the starting cell to the openList and sets their heuristics
		
		//for x values (1 to left and right of square)
		for (int i = -1; i < 2; i+= 2) {
			
			Cell currentCell = grid.getCell(start.getX() + i, start.getY());
			
			if(currentCell == start){
				continue;
			}
			
			if(currentCell.getTraversable()){
				//distance from current cell to starting cell gN
				currentCell.setgN(Math.abs( (start.getX() - currentCell.getX())) + Math.abs((start.getY() - start.getY()) ) );
			
				//distance from current cell to goal hN
				currentCell.sethN(Math.abs( (end.getX() - currentCell.getX())) + Math.abs((end.getY() - start.getY()) ) );
								
				openList.add(currentCell);
			}
			
		}
		//for y values (1 to above and below of square
		for (int j = -1; j < 2; j+= 2) {
				
				Cell currentCell = grid.getCell(start.getX(), start.getY() + j);
				
				if(currentCell == start){
					continue;
				}
				
				if(currentCell.getTraversable()){
					//distance from current cell to starting cell gN
					currentCell.setgN(Math.abs( (start.getX() - currentCell.getX())) + Math.abs((start.getY() - start.getY()) ) );
				
					//distance from current cell to goal hN
					currentCell.sethN(Math.abs( (end.getX() - currentCell.getX())) + Math.abs((end.getY() - start.getY()) ) );
										
					openList.add(currentCell);
				}
			}
		
		
		ArrayList<Cell> closedList = new ArrayList<Cell>();
		//add where we started from to the closed List
//		closedList.add(start);
		
		
		//incorrect to add non traverasable to the close list!
		//add all non traversable squares to closed list
//		for (int x = 0; x < 35; x++) {
//			
//			for (int y = 0; y < 27; y++) {
//				Cell CurrentCell = grid.getCell(x, y);
//				if(!CurrentCell.getTraversable()){
//					closedList.add(CurrentCell);
//				}
//			}
//			
//		}
		
		//where the traversable stuff begins
		Cell lowestFN = null;
		while(!openList.isEmpty()){
			
			//find lowest value in the current open list
			for (Cell cell : openList) {
				if(lowestFN == null){
					lowestFN = cell;
				}else if(cell.getfN() < lowestFN.getfN()){
					lowestFN = cell;
				}
			}
			//if end if found then exit
			closedList.add(lowestFN);
			openList.remove(lowestFN);
			if(lowestFN == end){
				
				break;
			}
			
			ArrayList<Cell> possibleOptionsList = new ArrayList<Cell>();

			
			//for x values (1 to left and right of square)
			for (int i = -1; i < 2; i+= 2) {
				
				Cell currentCell = grid.getCell(start.getX() + i, start.getY());
				
				if(currentCell == start){
					continue;
				}
				
				if(currentCell.getTraversable()){
					//distance from current cell to starting cell gN
					currentCell.setgN(Math.abs( (start.getX() - currentCell.getX())) + Math.abs((start.getY() - start.getY()) ) );
				
					//distance from current cell to goal hN
					currentCell.sethN(Math.abs( (end.getX() - currentCell.getX())) + Math.abs((end.getY() - start.getY()) ) );
										
					possibleOptionsList.add(currentCell);
				}
				
			}
			//for y values (1 to above and below of square
			for (int j = -1; j < 2; j+= 2) {
					
					Cell currentCell = grid.getCell(start.getX(), start.getY() + j);
					
					if(currentCell == start){
						continue;
					}
					
					if(currentCell.getTraversable()){
						//distance from current cell to starting cell gN
						currentCell.setgN(Math.abs( (start.getX() - currentCell.getX())) + Math.abs((start.getY() - start.getY()) ) );
					
						//distance from current cell to goal hN
						currentCell.sethN(Math.abs( (end.getX() - currentCell.getX())) + Math.abs((end.getY() - start.getY()) ) );
												
						possibleOptionsList.add(currentCell);
					}
				}
			
			//union the two lists so there are no duplicates
			Set<Cell> set = new HashSet<Cell>();

	        set.addAll(openList);
	        set.addAll(possibleOptionsList);

	        openList = new ArrayList<Cell>(set);
	        
		
		}
		
		if(!closedList.contains(end)){
	        throw new PathNotFoundException("path was not found in closed list");
	    }
		//create linked list here
		Cell currentCell = end;
		LinkedList<Cell> returnList = new LinkedList<Cell>();
		returnList.addFirst(currentCell);
		while(currentCell.getCameFrom() != null){
			returnList.addFirst(currentCell.getCameFrom());
			currentCell = currentCell.getCameFrom();
		}
		
		return returnList;
	}
	
	
	
	
	

//	private void startSearch(Cell cell) {
//		
//		Cell[] currentReachable = getReachableCells(startCell); //
//		addToOpenList(currentReachable);
//		
//	}
//
//private void addToOpenList(Cell[] currentReachable) {
//		
//		
//	}

//	private void calculateValues(Cell[] currentReachable) {
//		
//		
//	}

//	private Cell[] getReachableCells(Cell cell) {
//		Cell[] reachable = new Cell[4];
//		int x = cell.getX();
//		int y = cell.getY();
//		//array goes clockwise from N
//		reachable[0] = grid.getCell(x, y+1);
//		reachable[1] = grid.getCell(x+1, y);
//		reachable[2] = grid.getCell(x, y-1);
//		reachable[3] = grid.getCell(x-1, y);
//		
//		int g = cell.getgN();
//		
//		for(int i = 0; i < 4; i++)
//		{
//			reachable[i].setgN(g++);
//			reachable[i].setfN();
//		}
//		
//		return reachable;
//		
//	}
}

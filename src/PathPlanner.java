package src;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;


public class PathPlanner {

	private static PriorityQueue<Cell> openList;
	private static PriorityQueue<Cell> closedList;
	private static Set<Cell> openListSet;
	private static Set<Cell> closedListSet;

	private static Grid grid;
	private static Cell start;
	private static Cell end;

	
	public static ArrayList<int[]> GetOptimalPath(Grid inputGrid, Cell inputStart, Cell inputEnd) throws PathNotFoundException
	{
		
		grid = inputGrid;
		start = inputStart;
		end = inputEnd;
		
		openListSet = new HashSet<Cell>();
		closedListSet = new HashSet<Cell>();
		
		openList = new PriorityQueue<Cell>(10, new Comparator<Cell>() {
			public int compare(Cell o1, Cell o2) {
				if (o1.getfN() < o2.getfN()){
					return 1;
				}
				else if(o1.getfN() < o2.getfN()){
					return 0;
				}
				return -1;
			}
		});

		closedList = new PriorityQueue<Cell>(10, new Comparator<Cell>() {
			public int compare(Cell o1, Cell o2) {
				if (o1.getfN() < o2.getfN()){
					return 1;
				}
				else if(o1.getfN() < o2.getfN()){
					return 0;
				}
				return -1;
			}
		});
		//add where we started from to the closed List
		//adds all possible cells around the starting cell to the openList and sets their heuristics
		start.sethN(Math.abs( (end.getX() - start.getX())) + Math.abs((end.getY() - start.getY()) ) );

		addToClosedList(start);
		inspectSurroundingCells(start);

		//System.out.println(openList.size());

		//where the traversable stuff begins
		Cell lowestFN;
		while(!openList.isEmpty()){
			//System.out.println(openList.peek().getX() + "," + openList.peek().getY());
			
			//lowestFN = openList.peek();
			lowestFN = null;
			for(Cell cell : openListSet){
				if(lowestFN == null){
					lowestFN = cell;
				}
				else if(cell.getfN() < lowestFN.getfN()){
					lowestFN = cell;
				}
			}
			removeFromOpenList(lowestFN);
			//if end if found then exit
			addToClosedList(lowestFN);
			if(lowestFN == end){
				
				break;
			}
			inspectSurroundingCells(lowestFN);




		}
		
		if(!closedListSet.contains(end)){
	        throw new PathNotFoundException("path was not found in closed list");
	    }
		System.out.println("path has been found!");
		//create linked list here
		Cell currentCell = end;
		LinkedList<Cell> returnList = new LinkedList<Cell>();
		returnList.addFirst(currentCell);
		
		while(currentCell.getCameFrom() != null){
			//System.out.println(currentCell.getX() + "," + currentCell.getY());
			returnList.addFirst(currentCell);
			currentCell.setPartOfPath(true);
			currentCell = currentCell.getCameFrom();
		}
		//System.out.println(currentCell.getX() + "," + currentCell.getY());
		returnList.addFirst(currentCell);
		currentCell.setPartOfPath(true);

		
		//this part is only for looking at the grid then resetting the part of path values (not part of needed code)
		grid.printGrid();
		for(Cell cell: returnList){
			cell.setPartOfPath(false);
		}
		
		
		resetCameFrom();
		return condensePath(new ArrayList<>(returnList));
	}
	
	private static void resetCameFrom() {
		for (int i = 0; i < grid.getXLength(); i++) {
			for (int j = 0; j < grid.getYHeight(); j++) {
				grid.getCell(i, j).setCameFrom(null);
				
			}
			
		}
		
	}

	private static void inspectSurroundingCells(Cell CellToCheck) {
        //for x values (1 to left and right of square)
        Cell previousCell = CellToCheck.getCameFrom();
        for (int i = -1; i < 2; i += 2) {
            //checks if out of bounds
            if (CellToCheck.getX() + i < 0 || CellToCheck.getX() + i > grid.getXLength() - 1) {
                continue;
            }

            Cell currentCell = grid.getCell(CellToCheck.getX() + i, CellToCheck.getY());

            if (currentCell.getTraversable()) {
                if (currentCell.getCameFrom() == null && currentCell != start) {
                    currentCell.setCameFrom(CellToCheck);
                    if (previousCell != null) {
                        if (currentCell.getX() == previousCell.getX() || currentCell.getY() == previousCell.getY()) {
                            currentCell.setgN(CellToCheck.getgN() + 1);
                        } else {
                            currentCell.setgN(CellToCheck.getgN() + 3);

                        }
                    } else {
                        currentCell.setgN(CellToCheck.getgN() + 1);
                    }


                    currentCell.sethN(Math.abs((end.getX() - currentCell.getX())) + Math.abs((end.getY() - currentCell.getY())));


                } else if (previousCell != null) {
                    if (currentCell.getX() == previousCell.getX() || currentCell.getY() == previousCell.getY()) {
                        if (currentCell.getgN() > CellToCheck.getgN() + 1) {
                            //distance from current cell to starting cell gN
                            currentCell.setCameFrom(CellToCheck);

                            currentCell.setgN(CellToCheck.getgN() + 1);

                        }
                    } else if (currentCell.getgN() > CellToCheck.getgN() + 3) {
                        //distance from current cell to starting cell gN
                        currentCell.setCameFrom(CellToCheck);

                        currentCell.setgN(CellToCheck.getgN() + 3);

                    }

                }
                addToOpenList(currentCell);

            }
        }
        //for y values (1 to above and below of square
        for (int j = -1; j < 2; j += 2) {
            //checks if out of bounds
            if (CellToCheck.getY() + j < 0 || CellToCheck.getY() + j > grid.getYHeight() - 1) {
                continue;
            }

            Cell currentCell = grid.getCell(CellToCheck.getX(), CellToCheck.getY() + j);
            if (currentCell.getTraversable()) {
                if (currentCell.getCameFrom() == null && currentCell != start) {
                    currentCell.setCameFrom(CellToCheck);
                    if (previousCell != null) {
                        if (currentCell.getX() == previousCell.getX() || currentCell.getY() == previousCell.getY()) {
                            currentCell.setgN(CellToCheck.getgN() + 1);
                        } else {
                            currentCell.setgN(CellToCheck.getgN() + 3);

                        }
                    } else {
                        currentCell.setgN(CellToCheck.getgN() + 1);
                    }


                    currentCell.sethN(Math.abs((end.getX() - currentCell.getX())) + Math.abs((end.getY() - currentCell.getY())));


                } else if (previousCell != null) {
                    if (currentCell.getX() == previousCell.getX() || currentCell.getY() == previousCell.getY()) {
                        if (currentCell.getgN() > CellToCheck.getgN() + 1) {
                            //distance from current cell to starting cell gN
                            currentCell.setCameFrom(CellToCheck);

                            currentCell.setgN(CellToCheck.getgN() + 1);

                        }
                    } else if (currentCell.getgN() > CellToCheck.getgN() + 3) {
                        //distance from current cell to starting cell gN
                        currentCell.setCameFrom(CellToCheck);

                        currentCell.setgN(CellToCheck.getgN() + 3);

                    }

                }
                //distance from current cell to goal hN

                addToOpenList(currentCell);


            }
        }
    }
	
	private static void addToOpenList(Cell cell){
		if(openListSet.contains(cell)) {
			openList.remove(cell);
		} else if (closedListSet.contains(cell)){
			return;
		}
		openListSet.add(cell);
		openList.add(cell);
	}
	
	private static void addToClosedList(Cell cell){
		if(closedListSet.contains(cell)){
			return;
		}
		closedListSet.add(cell);
		closedList.add(cell);
	}
	
	private static void removeFromOpenList(Cell cell){
		openList.remove(cell);
		openListSet.remove(cell);
	}
	
	public static ArrayList<int[]> condensePath(ArrayList<Cell> path){
		ArrayDeque<int[]> test = new ArrayDeque<>();
		
		int lastAngle = 0;

		Cell currentCell;
		Cell previousCell = path.get(0);
		for (int i = 1; i < path.size() - 1; ++i){
			currentCell = path.get(i);
			int prevX = previousCell.getX();
			int prevY = previousCell.getY();
			int currX = currentCell.getX();
			int currY = currentCell.getY();
			
			if (prevY == currY){
				if (prevX < currX){
					filterDirection(test, 90);
				} else {
					filterDirection(test, -90);
				}
			} else {
				if (prevY > currY){
					filterDirection(test, 180);
				} else {
					filterDirection(test, 0);
				}
			}
			previousCell = currentCell;
		}
		
		return new ArrayList<>(test);
	}

	private static void filterDirection(ArrayDeque<int[]> test, int direction) {
		if (test.isEmpty()){
			int[] tmp1 = {direction, 1};
			test.add(tmp1);
			return;
		}
		if (test.getLast()[0] == direction){
			test.getLast()[1] += 1;
		} else {
			int[] tmp1 = {direction, 1};
			test.add(tmp1);
		}
	}
}
	

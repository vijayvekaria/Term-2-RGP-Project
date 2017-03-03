package main;

public class Grid {
	//starting at position (18, 7)/[18][7] after localiser
	static Cell[][] grid = new Cell[25][25];
	int goalX;
	int goalY;
	
	
	public Grid() {
		createGrid();
		setFalseHeuristics();
		//printGrid();
		printLine();
	}

	private void createGrid() {
		for (int i = 0; i < 25; ++i){
			for (int j = 0; j < 25; ++j){
				grid[i][j] = new Cell(i, j, calculateCostToGoal(i, j));
			}
		}
	}
	
	private void setFalseHeuristics() {
		int triangleCounter = 1;
		for (int x = 19; x < 25; ++x){
			for (int y = 0; y < triangleCounter; ++y){
				grid[x][y].sethN(49);
			}
			++triangleCounter;
		}
		
		int triangleCounter1 = 6;
		for (int x = 0; x < 6; ++x){
			for (int y = 24; y > 25 - triangleCounter; --y){
				grid[x][y].sethN(49);
			}
			--triangleCounter;
		}
	}
	
	public int calculateCostToGoal(int xG, int yG){
		return Math.abs(xG - goalX) + Math.abs(yG - goalY);
	}
	
	public void printGrid(){
		for(int x = 0; x < 25; x++)
		{
			for(int y = 0; y < 25; y++)
			{
				if(grid[x][y].gethN() == 49)
				{
					System.out.print("X ");
				}
				else
				{
					System.out.print("O ");
				}
				
			}
			System.out.println();
		}
	}
	
	public void printLine()
	{
		int shortestSeg = 0;
		double shortestDev = 100;
		for(int i = 0; i < 35; i++)
		{
			double result = ((2*(i+1))+21)*Math.cos(45);
			//if ((result - ))
			System.out.println("i: " + i + ", " + result);
		}
	}
	
	public Cell getCell(int x, int y)
	{
		return grid[x][y];
	}
}

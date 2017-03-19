package src;

public class Grid {
	 //starting at position (18, 7)/[18][7] after localiser
	private static int xLength = 35;
	private static int yHeight = 27;
	private static Cell[][] grid = new Cell[xLength][yHeight];

	private static final int firstBlockLocation = 1; //1 = left, 2 = middle, 3 = right

	
	
	public Grid() {
		createGrid();
		setFalseHeuristics();
		printGrid();
		//printLine();
		System.out.println(" ");
	}

	private void createGrid() {
		for (int i = 0; i < xLength; ++i){
			for (int j = 0; j < yHeight; ++j){
				grid[i][j] = new Cell(i, j);
			}
		}
	}
	
	private void setFalseHeuristics() {
//		int triangleCounter = 1;
//		for (int x = 19; x < 25; ++x){
//			for (int y = 0; y < triangleCounter; ++y){
//				grid[x][y].setTraversable(false);
//			}
//			++triangleCounter;
//		}
//
//		int triangleCounter1 = 6;
//		for (int x = 0; x < 6; ++x){
//			for (int y = 24; y > 25 - triangleCounter1; --y){
//				grid[x][y].setTraversable(false);
//			}
//			--triangleCounter1;
//		}
//
//		//for the first test the right half of the board will be blocked
//		for (int i = 12; i < xLength; i++) {
//			grid[i][13].setTraversable(false);
//		}
//
//		//sets where the first block will be
//		int startingCell = 1;
//		switch(firstBlockLocation){
//			case 1: startingCell = 3;
//				break;
//			case 2: startingCell = 5;
//				break;
//			case 3: startingCell = 7;
//				break;
//		}
//		for (int y = -1; y <= 1; y++) {
//			for (int x = -1; x <=1 ; x++) {
//				grid[startingCell + x][13 + y].setTraversable(false);
//			}
//		}
//
//
//
//
//		//set your own blocks with these for loops
//
////		for (int i = 1; i < xLength; i++) {
////			grid[i][13].setTraversable(false);
////		}
////		for (int i = 1; i < 13; i++) {
////			grid[1][i].setTraversable(false);
////		}
////		for (int i = 1; i < 13; i++) {
////			grid[9][i].setTraversable(false);
////		}



		//the real board
		for(int y = 0; y < 27; y++){
			for(int x = 0; x < 35; x++){
				//grid[x][y] = 0;
				//System.out.print(grid[x][y]);
				//count++;
			}
			//count = 0;
			//System.out.println();
		}

		int count = 13;
		for(int y = 0; y < 14; y++){
			for(int x = count; x >= 0; x--){
				grid[x][y].setTraversable(false);
			}
			count--;
		}

		int count2 = 13;

		for(int y = 26; y >= 14; y--){
			for(int x = count2; x >= 0; x--){
				grid[x][y].setTraversable(false);
			}
			count2--;
		}

		int count3 = 21;

		for(int y = 0; y < 14; y++){

			for(int x = count3; x < 35; x++){
				grid[x][y].setTraversable(false);
			}
			count3++;
		}

		int count4 = 21;

		for(int y = 26; y >= 14; y--){
			for(int x = count4; x < 35; x++){
				grid[x][y].setTraversable(false);
			}
			count4++;
		}

		//int count = 0;

//		for(int y = 0; y < 27; y++){
//			for(int x = 0; x < 35; x++){
//				System.out.print(grid[x][y]);
//
//			}
//			System.out.println();
//		}


	}

	
	public void printGrid(){
		for(int y = 0; y < yHeight; y++)
		{
			for(int x = 0; x < xLength; x++)
			{
				if(!grid[x][y].getTraversable())
				{
					System.out.print("\u001B[41m" + "\u001B[31m" + "X " + "\u001B[0m");
				}
				else if(grid[x][y].isPartOfPath()){
					System.out.print("\u001B[42m" + "\u001B[32m" + "# " + "\u001B[0m");

				}
				else
				{
					System.out.print("\u001B[47m" + "\u001B[37m" + "O " + "\u001B[0m");
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

	public int getXLength() {
		
		return xLength;
	}
	
	public int getYHeight() {
		
		return yHeight;
	}
	
	
}

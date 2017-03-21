package src;

public class Grid {
	 //starting at position (18, 7)/[18][7] after localiser
	private static int xLength = 35;
	private static int yHeight = 27;
	private static Cell[][] grid = new Cell[xLength][yHeight];

	private static final int firstBlockLocation = 1; //1 = left, 2 = middle

	// POSSIBLE TARGET FOR THE GOAL FROM START IS (11,20)
	
	public Grid() {
		createGrid();
		setFalseHeuristics();
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
		//set wall
		for (int i = 12; i < 23; i++){
			grid[i][13].setTraversable(false);
		}
		//closer to the wall
		if(firstBlockLocation == 2){
			for (int i = 6; i < 12; i++) {
				grid[i][13].setTraversable(false);
				
			}
		}
		//on the far left side
		else if(firstBlockLocation == 1){
			for (int i = 1; i < 8; i++) {
				grid[i][13].setTraversable(false);
				
			}
		}
		
		for(int i = 20; i < 27; i++) {
			grid[13][i].setTraversable(false);
		}


	}

	
	public void printGrid(){
		for(int y = yHeight - 1; y >=0; y--)
		{
			for(int x = 0; x < xLength; x++)
			{
				if(!grid[x][y].getTraversable())
				{
					System.out.print("X "); //("\u001B[41m" + "\u001B[31m" + "X " + "\u001B[0m");
				}
				else if(grid[x][y].isPartOfPath()){
					System.out.print("# "); //("\u001B[42m" + "\u001B[32m" + "# " + "\u001B[0m");

				}
				else
				{
					System.out.print("O "); //("\u001B[47m" + "\u001B[37m" + "O " + "\u001B[0m");
				}
				
			}
			System.out.println(" " + y);
		}
		System.out.println("(0,0) starts from bottom left corner");
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
	
	public void setGreen(){
		for (int i = 0; i < 27; i++) {
			grid[i][13].setTraversable(false);
		}
		
	}
	
	public void setRed(){
		for (int i = 0; i < 22; i++) {
			grid[i][13].setTraversable(false);
		}
		
		for (int i = 28; i < xLength; i++) {
			grid[i][13].setTraversable(false);
		}
		

	}
}

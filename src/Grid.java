package src;

public class Grid {
	 //starting at position (18, 7)/[18][7] after localiser
	private static int xLength = 35;
	private static int yHeight = 27;
	private static Cell[][] grid = new Cell[xLength][yHeight];

	private static final int firstBlockLocation = 2; //1 = left, 2 = middle

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
		
		//add extra layer to top left wall
		int xCount = 0;
		for (int i = 13; i < 25; i++) {
			grid[++xCount][i].setTraversable(false);
			
		}
		//add extra layer to bottom left wall
		int xCount2 = 0;
		for (int i = 13; i >= 0; i--) {
			grid[++xCount2][i].setTraversable(false);
		}
		//TODO add extra layer bottom right
		//TODO add extra layer of thickness from 15 to 16
		//set wall
		for (int i = 10; i < 23; i++){
			grid[i][13].setTraversable(false);
		}
		for (int i = 10; i < 23; i++){
			grid[i][15].setTraversable(false);
		}
		grid[22][12].setTraversable(false);
		grid[22][14].setTraversable(false);
		
		grid[10][12].setTraversable(false);
		grid[10][14].setTraversable(false);
		
		//set right side of map as blocked
		for (int i = 22; i < xLength; i++){
			grid[i][13].setTraversable(false);
		}
		//closer to the wall
		if(firstBlockLocation == 2){
			for (int i = 7; i < 12; i++) {
				grid[i][13].setTraversable(false);
				
			}
			grid[7][12].setTraversable(false);
			grid[7][14].setTraversable(false);
			
			for (int i = 7; i < 12; i++) {
				grid[i][15].setTraversable(false);
				
			}
		}
		
		//on the far left side
		else if(firstBlockLocation == 1){
			for (int i = 1; i < 9; i++) {
				grid[i][13].setTraversable(false);
				
			}
			grid[8][12].setTraversable(false);
			grid[8][14].setTraversable(false);
			
			for (int i = 1; i < 9; i++) {
				grid[i][15].setTraversable(false);
				
			}
			
		}
		
		for(int i = 20; i < 27; i++) {
			grid[13][i].setTraversable(false);
		}
		for(int i = 20; i < 27; i++) {
			grid[15][i].setTraversable(false);
		}
		grid[12][20].setTraversable(false);


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
		for (int i = 0; i < 27; i++) {
			grid[i][15].setTraversable(false);
		}
		for (int i = 27; i < xLength - 1; i++) {
			grid[i][13].setTraversable(true);
		}
		for (int i = 27; i < xLength - 1; i++) {
			grid[i][15].setTraversable(true);
		}
		grid[26][12].setTraversable(false);
		grid[26][14].setTraversable(false);
		
	}
	
	public void setRed(){
		for (int i = 0; i < 22; i++) {
			grid[i][13].setTraversable(false);
		}
		for (int i = 0; i < 22; i++) {
			grid[i][15].setTraversable(false);
		}
		
		for (int i = 23; i < 28; i++) {
			grid[i][13].setTraversable(true);
		}
		
		for (int i = 28; i < xLength; i++) {
			grid[i][13].setTraversable(false);
		}
		
		grid[28][12].setTraversable(false);
		grid[28][14].setTraversable(false);


	}
}

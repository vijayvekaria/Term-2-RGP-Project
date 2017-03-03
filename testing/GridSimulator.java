package testing;

public class GridSimulator {
	//location of segment no. 19 at cell [18][7]	
	public GridSimulator() {
		int[][] grid = new int[35][27];
		//int count = 0;
		for(int y = 0; y < 27; y++){
			for(int x = 0; x < 35; x++){
				grid[x][y] = 0;
				//System.out.print(grid[x][y]);
				//count++;
			}
			//count = 0;
			//System.out.println();
		}

		int count = 13;
		for(int y = 0; y < 14; y++){
			for(int x = count; x >= 0; x--){
				grid[x][y] = 1;
			}
			count--;
		}

		int count2 = 13;

		for(int y = 26; y >= 14; y--){
			for(int x = count2; x >= 0; x--){
				grid[x][y] = 1;
			}
			count2--;
		}

		int count3 = 21;

		for(int y = 0; y < 14; y++){

			for(int x = count3; x < 35; x++){
				grid[x][y] = 1;
			}
			count3++;
		}

		int count4 = 21;

		for(int y = 26; y >= 14; y--){
			for(int x = count4; x < 35; x++){
				grid[x][y] = 1;
			}
			count4++;
		}

		//int count = 0;

		for(int y = 0; y < 27; y++){
			for(int x = 0; x < 35; x++){
				System.out.print(grid[x][y]);
				
			}
			System.out.println();
		}
	}
}

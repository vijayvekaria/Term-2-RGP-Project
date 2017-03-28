package src;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;

public class PathTestv2 {
	
	public ArrayList<int[]> condensePath(ArrayList<Cell> path){
		ArrayDeque<int[]> test = new ArrayDeque<>();
		
		int lastAngle = 0;

		Cell currentCell;
		Cell previousCell = path.get(0);
		for (int i = 1; i < path.size(); ++i){
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

	private void filterDirection(ArrayDeque<int[]> test, int direction) {
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

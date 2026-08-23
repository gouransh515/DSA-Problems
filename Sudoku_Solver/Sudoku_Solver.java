package BackTracking;

import java.util.Iterator;

public class Sudoku_Solver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int[][] grid = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 }, { 5, 2, 0, 0, 0, 0, 0, 0, 0 }, { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
				{ 0, 0, 3, 0, 1, 0, 0, 8, 0 }, { 9, 0, 0, 8, 6, 3, 0, 0, 5 }, { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
				{ 1, 3, 0, 0, 0, 0, 2, 5, 0 }, { 0, 0, 0, 0, 0, 0, 0, 7, 4 }, { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
		Print(grid, 0, 0);
	}
	public static void Print(int[][] grid,int cr,int cc) {
		if(cc==9) {
			cc=0;
			cr++;
		}
		if(cr==9) {
			display(grid);
			return;
		}
		if(grid[cr][cc]!=0) {
			Print(grid,cr,cc+1);
			
		}
		else {
			for(int val=1;val<=9;val++) {
				if(isitsafe(grid,cr,cc,val)==true) {
					grid[cr][cc]=val;
					Print(grid,cr,cc+1);
					grid[cr][cc]=0;
				}
			}
		}
	}
	private static boolean isitsafe(int[][] grid, int cr, int cc, int val) {
		for(int i=0;i<9;i++) {
			if(grid[cr][i]==val) {
				return false;
			}
		}
		for(int i=0;i<9;i++) {
			if(grid[i][cc]==val) {
				return false;
			}
		}
		int r=cr-cr%3;
		int c=cc-cc%3;
		for (int i = r; i < r+3; i++) {
			for (int j = c; j < c+3; j++) {
				if(grid[i][j]==val) {
					return false;
				}
			}
		}
		return true;
	}
	private static void display(int[][] grid) {
		// TODO Auto-generated method stub
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[0].length;j++) {
				System.out.print(grid[i][j]+" ");
			}System.out.println();
		}
	}

}

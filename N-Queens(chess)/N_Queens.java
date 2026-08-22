package BackTracking;

public class N_Queens {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=8;
		boolean [][] board=new boolean[n][n];
		queens(board,0,n);

	}

	private static void queens(boolean[][] board, int row, int tq) {
		// TODO Auto-generated method stub
		if(tq==0) {
			display(board);
			return;
		}
		for(int col=0;col<board.length;col++) {
			if(isitsafe(board,row,col)==true) {
				
				board[row][col]=true;
				queens(board, row+1, tq-1);
				board[row][col]=false;
			}
		}
		
	}

	private static void display(boolean[][] board) {
		// TODO Auto-generated method stub
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				if(board[i][j]==true) {
					System.out.print("Q ");
				}
				else {
					System.out.print("X ");
				}
			}System.out.println();
		}
		System.out.println();
		
	}

	private static boolean isitsafe(boolean[][] board, int row, int col) {
		int r=row;int c=col;
		while(r>=0) {
			if(board[r][c]==true) {
				return false;
			}
		r--;
		}
		r=row;c=col;
		while(c<board.length&&r>=0) {
			if(board[r][c]==true) {
				return false;
			}
		r--;
		c++;

		}
		r=row;c=col;
		while(c>=0&&r>=0) {
			if(board[r][c]==true) {
				return false;
			}
		r--;
		c--;

		}
		return true;
	}

}

package BackTracking;
import java.util.Scanner;
public class rat_chases_cheese {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int m=sc.nextInt();
		char[][] maze=new char[n][m];
		int[][] ans=new int[n][m];
		for(int i=0;i<maze.length;i++) {
			String s=sc.next();
			for(int j=0;j<s.length();j++) {
				maze[i][j]=s.charAt(j);
			}
		}
		rat_maze(maze,0,0,ans);
       if(flag==false) {
    	   System.out.println("No Path Found");
       }
	}
	static boolean flag=false;
	public static void rat_maze(char[][] maze,int cr,int cc ,int[][] ans) {
		if (cr == maze.length - 1 && cc == maze[0].length - 1 && maze[cr][cc] != 'X') {
			ans[cr][cc] = 1;
			display(ans);
			flag = true;
			return;

		}
		if(cr<0||cc<0||cr>=maze.length||cc>=maze[0].length||maze[cr][cc]=='X') {
			
			return;
		}
		
		maze[cr][cc]='X';
		ans[cr][cc]=1;
		rat_maze(maze,cr+1,cc,ans);
		rat_maze(maze,cr-1,cc,ans);
		rat_maze(maze,cr,cc-1,ans);
		rat_maze(maze,cr,cc+1,ans);
		maze[cr][cc]='O';
		ans[cr][cc]=1;
	}
	private static void display(int[][] ans) {
		// TODO Auto-generated method stub
		for(int i=0;i<ans.length;i++) {
			for(int j=0;j<ans[0].length;j++) {
				System.out.print(ans[i][j]+" ");
			}
			System.out.println();
		}
		
	}

}

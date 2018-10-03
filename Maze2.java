/*
Laura Vail
COSC 600
4/4/18

MAZE PROBLEM
This program takes as input a 2x2 graphical maze in which 0 is a path and 1 is a wall, and user input for a start position. It marks visited positions with a + and navigates towards the finish E position. As output it prints either that there was no way out, or the final path out.
*/

import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Maze {
	
	public static void main(String args[]) throws FileNotFoundException, java.io.IOException{

		Scanner kb = new Scanner(System.in);
		StringBuilder builder = new StringBuilder();
		int row_num = 20;
		int col_num = 20;
		int row = -1;
		int col = -1;
		char[][] maze_unbordered = new char[row_num][col_num];
		//add 2 to each side for border for final maze
		char[][] maze = new char[row_num+2][col_num+2];
		Stack<String> move_options = new Stack<String>();
			
		// read in the maze text file to a StringBuilder
		List<String> lines = Files.readAllLines(Paths.get("maze.txt"));
		//System.out.println(lines.size());
		for (String line : lines) {
			for (int i=0; i<line.length(); i++) {
				builder.append(line.charAt(i));
			}
		}
		//System.out.println(builder);
		//System.out.println(builder.length());


		// convert StringBuilder to string, then to 1d char array
		String str = builder.toString();
		//System.out.println(str.length());
		char[] maze1d = str.toCharArray();
		//System.out.println(maze1d.length);


		// copy 1d char array into 2d char array
		for (int i=0; i<20; i++) {
			for (int j=0; j<20; j++) {
				maze_unbordered[i][j] = maze1d[(i*20 + j%20)];
			}
		}


		// add a border of 1s to the maze
		// rows 0 and 21, and cols 0 and 21 are set to 1
		for (int i=0; i<row_num+2; i++) {
			for (int j=0; j<col_num+2; j++) {
				if (i % (row_num+1) == 0 || j % (col_num+1) == 0) {
					maze[i][j] = '1';
				} else {
					maze[i][j] = maze_unbordered[i-1][j-1];
				}
			}
		}
		
		
		// print the maze
		printMaze(maze);


		// get user input for starting position

		do {
			try {
				System.out.println("Enter a starting position on a '0'");
				System.out.print("Enter a starting row, 1-20: ");
				row = kb.nextInt();
				System.out.print("Enter a starting column, 1-20: ");
				col = kb.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Enter an number");
			}
			kb.nextLine();
		}
		while ((row<0) || (row>20) || (col<0) || (col>20));

		// check if a position in maze
		if ((row > 20) || (col > 20)) {
			System.out.println("Enter a number 1-20");
			System.exit(0); 
			}

		// check for valid start position in maze
		if (maze[row][col] == '1') {
			System.out.println("Maze location must be 0");
			System.exit(0); 
		}
		else { // print maze with start position
			maze[row][col] = 'S';
			//printMaze(maze);
		}	
	

		// check for valid move options at start, push to stack if valid
		// prioritize moving up and left by pushing those last
		boolean right = valid_move(row, col+1, maze); //test moving up
		if (right == true) {
			move_options.push("right");
		}		

		boolean down = valid_move(row+1, col, maze); //test moving up
		if (down == true) {
			move_options.push("down");
		}		

		boolean left = valid_move(row, col-1, maze); //test moving up
		if (left == true) {
			move_options.push("left");
		}		

		boolean up = valid_move(row-1, col, maze); //test moving up
		if (up == true) {
			move_options.push("up");
		}		

		//System.out.println(move_options);


		// after filling with inital moves, repeat move evaluation while the stack is not empty (ie there is a 0 or E position available)
		// prioritize moving up and left by pushing those last
		while (move_options.empty() == false) {
			
			// 1. pop the stack to get the next move
			String next_move = move_options.pop();
			//System.out.println(next_move);

			// 2. depending on the move, change row or col as needed
			// break immediately if reach E
			switch (next_move) {
			

				case "up":
				if (maze[row][col] != 'S') {
					maze[row][col] = '+';
				}
				row -= 1;
				if (maze[row][col] == 'E') {
					printMaze(maze);
					System.out.println("I am free");
					System.exit(0);
				}
				break;


				case "left":
				if (maze[row][col] != 'S') {
					maze[row][col] = '+';
				}
				col -= 1;
				if (maze[row][col] == 'E') {
					printMaze(maze);
					System.out.println("I am free");
					System.exit(0);
				}
				break;


				case "down":
				if (maze[row][col] != 'S') {
					maze[row][col] = '+';
				}
				row += 1;
				if (maze[row][col] == 'E') {
					printMaze(maze);
					System.out.println("I am free");
					System.exit(0);
				}
				break;


				case "right":
				if (maze[row][col] != 'S') {
					maze[row][col] = '+';
				}
				col += 1;
				if (maze[row][col] == 'E') {
					printMaze(maze);
					System.out.println("I am free");
					System.exit(0);
				}
				break;
			} // end switch

			// 3. after changing the position, evaluate new move options from that position, and push valid options to stack
			// prioritize moving up and left by pushing those last
			right = valid_move(row, col+1, maze);
			if (right == true) {
				move_options.push("right");
			}			
			//System.out.println(next_move);


			down = valid_move(row+1, col, maze);
			if (down == true) {
				move_options.push("down");
			}			
			//System.out.println(next_move);


			left = valid_move(row, col-1, maze);
			if (left == true) {
				move_options.push("left");
			}			
			//System.out.println(next_move);


			up = valid_move(row-1, col, maze);
			if (up == true) {
				move_options.push("up");
			}			
			//System.out.println(next_move);
		} // end while loop

		// if the position did not land on E during while loop, stuck
		printMaze(maze);
		System.out.println("Help I am stuck in the maze");

		// evaluate end position
	} // end main	


	public static boolean valid_move(int r, int c, char[][] m) {

		boolean valid = true;

		// check if row and col values are within array bounds
		// and if location in maze is 1 or +
		if ((r<0) || (r>21) || (c<0) || (c>21)
			|| (m[r][c] == '1') || (m[r][c] == '+')) {
			valid = false;
		}
		return valid;
	}


	public static void printMaze(char[][] maze2d) {

		// print with labeled axes
		System.out.println("");
		System.out.print("   ");
		
		for (int i=0; i < maze2d[0].length; i++) {
  			System.out.printf("%2s", i);
		}
		
		System.out.println();
		System.out.println("");

		for(int i=0; i<maze2d.length; i++){
  			
			System.out.printf("%2s ", i);
  			for(int j=0; j<maze2d[i].length; j++){
    				System.out.printf("%2s", maze2d[i][j]);
  			}	
				
  			System.out.println();
		}
		System.out.println("");

	}
}

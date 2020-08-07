import java.util.InputMismatchException;
import java.util.Scanner;

public class Board {
	int [][] board = new int[7][6];
	int winner = 0;
	boolean turn1 = true;
	static boolean win = false;
	Scanner scnr = new Scanner(System.in);
	AI computer = new AI(2);

	//initialize the board
	//fills all indexes with 0 to represent empty spots
	public Board() {
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6; y++) {
				board[x][y] = 0;
			}
		}
		display();
	}

	//whilst no one has won the game
	//a scanner scans for a number to be inputed
	//checks if the inputed number is from 1-6 and isn't anything like a letter or symbol
	//checks if anyone has won the game
	public void makeMove() {
		while(win == false) {
			int move = 0;
			while(true) {
				try {
					move = scnr.nextInt() - 1;
					while(move < 0 || move > 6 || isFull(move)) {
						System.out.println("Please enter a valid column");
						move = scnr.nextInt() - 1;
					}
					break;
				}
				catch(InputMismatchException e) {
					System.out.println("Please enter a valid number");
					scnr.next();
				}
			}
			
			if(move >= 0 && move < 7) {
				drop(move);
				drop(computer.makeMove());
			}
		}
		if(!win) 
			System.out.println("No Winner");
		else if(turn1)
			System.out.println("AI Wins");
		else 
			System.out.println("Player Wins");
	}

	//checks if the column is full
	public boolean isFull(int num) {
		if(board[num][5] == 0)
			return false;
		System.out.println("This column is full, try another");
		return true;
	}

	//if the game has been won and the specified column isn't full a piece is dropped
	public boolean drop(int num) {
		if(win == false && !isFull(num)) {
			for(int i = 0; i < 6; i++) {
				if(board[num][i] == 0) {
					if(turn1)
						board[num][i] = 1;
					else 
						board[num][i] = 2;
					turn1 = !turn1;
					display();
					computer.check(board);
					//computer.display();
					//computer.displayOpen();
					checkWin();
					return true;
				}
			}
		}
		return false;
	}

	//checks if either player has won
	public int checkWin() {
		for(int i = 0; i < 7; i++) {
			int count = 0;
			while(count  < 6 && board[i][count] != 0) {
				if(checkRight(i, count) != 0||
						checkUp(i, count) != 0 ||
						checkDU(i, count) != 0 ||
						checkDD(i, count) != 0) {
					win = true;
					return winner;
				}
				count++;
			}
		}
		return 0;
	}

	//checks the spaces to the right of the specified location
	public int checkRight(int row, int col){
		if(row < 4) {
			int temp = board[row][col];
			for(int i = 1; i < 4; i++) {
				if(board[row + i][col] != temp)
					return 0;
			}
			winner = temp;
			return temp;
		}
		return 0;
	}
	
	//checks the spaces above of the specified location
	public int checkUp(int row, int col){
		if(col < 3) {
			int temp = board[row][col];
			for(int i = 1; i < 4; i++) {
				if(board[row][col + i] != temp)
					return 0;
			}
			winner = temp;
			return temp;			
		}
		return 0;
	}
	
	//checks the spaces diagonally up to the right of the specified location
	public int checkDU(int row, int col){
		if(row < 4 && col < 3) {
			int temp = board[row][col];
			for(int i = 1; i < 4; i++) {
				if(board[row + i][col + i] != temp)
					return 0;
			}
			winner = temp;
			return temp;

		}
		return 0;
	}
	
	//checks the spaces diagonally down to the right of the specified location
	public int checkDD(int row, int col){
		if(row < 4 && col > 2) {
			int temp = board[row][col];
			for(int i = 1; i < 4; i++) {
				if(board[row + i][col - i] != temp)
					return 0;
			}
			winner = temp;
			return temp;
		}
		return 0;
	}

	//displays the board and the difference pieces
	public void display() {
		System.out.println();
		for (int y = 5; y >= 0; y--) {
			System.out.print("| ");
			for(int x = 0; x < 7; x++) {
				if(board[x][y] == 1)
					System.out.print("X");
				if(board[x][y] == 2)
					System.out.print("O");
				if(board[x][y] == 0)
					System.out.print(" ");
				System.out.print(" | ");
			}
			System.out.println();
		}
		System.out.println("-----------------------------");
	}

	//initializes a board object
	//begins the game by making a move
	public static void main(String [] args) {
		Board b = new Board();
		b.makeMove();
	}
}
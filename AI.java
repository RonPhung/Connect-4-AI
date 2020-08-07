
public class AI {
	int [][] board = new int[7][6];
	int [] result = {0,0,0,0,0,0,0};
	int [] enemyResult = {0,0,0,0,0,0,0};
	int [] openSpot = {0,0,0,0,0,0,0};
	int piece;
	int enemy;

	//AI object constructor
	//parameter piece is used to determine which piece the AI will be
	public AI(int piece) {
		if(piece == 1) {
			this.piece = 1;
			enemy = 2;
		}
		else {
			this.piece = 2;
			enemy = 1;
		}
	}

	//this objects creates a copy of the board
	//result and enemyResult are reset to accomodate for new moves
	//checks the board in multiple directions
	public void check(int[][] arr) {
		board = arr;
		result = new int [] {0,0,0,0,0,0,0};
		enemyResult = new int [] {0,0,0,0,0,0,0};
		checkOpen();
		checkD();
		checkH();
		checkDU();
		checkDD();
		makeMove();
	}

	//AI determines where to move
	//if the AI can win then it will do that first
	//then it will stop the player from winning
	//if no player is going to win then the AI will drop a piece randomly
	public int makeMove() {
		for(int i = 0; i < 7; i++) {
			if(result[i] != 0)
				return i;
			if(enemyResult[i] != 0)
				return i;
		}
		int temp = (int)(Math.random() * 7);
		while(openSpot[temp] == -1)
			temp = (int)(Math.random() * 7);

		return temp;
	}
	
	//displays open spots on each column
	public void displayOpen() {
		System.out.print("| ");
		for(int i = 0; i < 7; i++) {
			System.out.print(openSpot[i]);
			System.out.print(" | ");
		}
		System.out.println("Open Spots");
	}

	//checks for the rows pieces can be dropped in each column
	public void checkOpen() {
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				if(board[i][5] != 0) {
					openSpot[i] = -1;
					break;
				}
				if(board[i][j] == 0) {
					openSpot[i] = j;
					break;
				}
			}
		}
	}

	//checks the spaces below of the specified location
	public void checkD() {
		int count = 0;
		int enemyCount = 0;
		for(int i = 0; i < 7; i++) {
			if(openSpot[i] > 2) {
				for(int j = 1; j < 4; j++) {
					if(piece == board[i][openSpot[i] - j])
						count++;
					if(enemy == board[i][openSpot[i] - j])
						enemyCount++;
				}
				if(count >= 3)
					result[i] += 1;
				if(enemyCount >= 3)
					enemyResult[i] += 1;
				count = 0;
				enemyCount = 0;
			}
		}
	}

	//checks left, right, and the the previous pieces are the same
	public int checkH() {
		int count = 0;
		int enemyCount = 0;
		for(int x = 0; x < 7; x++) {
			int y = openSpot[x];
			if(y != -1) {
				for(int r = 1; r < 4; r++) {
					if((x + r) < 7) {
						if(r > 1 && board[x + r][y] != board[x + r - 1][y])
							break;
						if(piece == board[x + r][y])
							count++;
						else if(enemy == board[x + r][y])
							enemyCount++;
						else
							break;
					}
				}
				for(int l = 1; l < 4; l++) {
					if((x - l) >= 0) {
						if(l > 1 && board[x - l][y] != board[x - l + 1][y])
							break;
						if(piece == board[x - l][y])
							count++;
						else if(enemy == board[x - l][y])
							enemyCount++;
						else
							break;
					}
				}
				if(count >= 3 && board[x][y] == 0)
					result[x] += 1;
				if(enemyCount >= 3 && board[x][y] == 0)
					enemyResult[x] += 1;
				count = 0;
				enemyCount = 0;
			}
		}
		return 0;
	}
	
	//checks diagonally upward both left and right
	public int checkDU() {
		int count = 0;
		int enemyCount = 0;
		for(int x = 0; x < 7; x++) {
			int y = openSpot[x];
			if(y != -1) {
				for(int r = 1; r < 4; r++) {
					if((x + r) < 7 && (y + r) < 6) {
						if(r > 1 && board[x + r][y + r] != board[x + r - 1][y + r - 1])
							break;
						if(piece == board[x + r][y + r])
							count++;
						else if(enemy == board[x + r][y + r])
							enemyCount++;
						else
							break;
					}
				}
				for(int l = 1; l < 4; l++) {
					if((x - l) >= 0 && (y - l) >= 0) {
						if(l > 1 && board[x - l][y - l] != board[x - l + 1][y - l + 1])
							break;
						if(piece == board[x - l][y - l])
							count++;
						else if(enemy == board[x - l][y - l])
							enemyCount++;
						else
							break;
					}
				}
				if(count >= 3 && board[x][y] == 0)
					result[x] += 1;
				if(enemyCount >= 3 && board[x][y] == 0)
					enemyResult[x] += 1;
				count = 0;
				enemyCount = 0;
			}
		}
		return 0;
	}

	//check diagonally down both left and right
	public int checkDD() {
		int count = 0;
		int enemyCount = 0;
		for(int x = 0; x < 7; x++) {
			int y = openSpot[x];
			if(y != -1) {
				for(int r = 1; r < 4; r++) {
					if((x + r) < 7 && (y - r) >= 0) {
						if(r > 1 && board[x + r][y - r] != board[x + r - 1][y - r + 1])
							break;
						if(piece == board[x + r][y - r])
							count++;
						else if(enemy == board[x + r][y - r])
							enemyCount++;
						else
							break;
					}
				}
				for(int l = 1; l < 4; l++) {
					if((x - l) >= 0 && (y + l) < 6) {
						if(l > 1 && board[x - l][y + l] != board[x - l + 1][y + l - 1])
							break;
						if(piece == board[x - l][y + l])
							count++;
						else if(enemy == board[x - l][y + l])
							enemyCount++;
						else
							break;
					}
				}
				if(count >= 3 && board[x][y] == 0)
					result[x] += 1;
				if(enemyCount >= 3 && board[x][y] == 0)
					enemyResult[x] += 1;
				count = 0;
				enemyCount = 0;
			}
		}
		return 0;
	}

	//displays whether either player has a specified spot to win
	public void display() {
		System.out.print("| ");
		for(int i = 0; i < 7; i++) {
			System.out.print(result[i]);
			System.out.print(" | ");
		}
		if(piece == 1)
			System.out.print("(X) ");
		else 
			System.out.print("(O) ");
		System.out.println("AI Vision");

		System.out.print("| ");
		for(int i = 0; i < 7; i++) {
			System.out.print(enemyResult[i]);
			System.out.print(" | ");
		}
		if(enemy == 1)
			System.out.print("(X) ");
		else 
			System.out.print("(O) ");
		System.out.println("Player Vision");
	}
}

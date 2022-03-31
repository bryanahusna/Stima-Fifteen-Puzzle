package bryan.fifteenpuzzle;

import java.io.File;
import java.util.Scanner;

public class GameBoard {
	protected int[][] arr;
	protected int emptyCellRow;
	protected int emptyCellCol;
	
	public static void main(String[] args) {
		try {
			// read file, print, and check sigma and solvable
			GameBoard gb = new GameBoard("test/config1.txt");
			gb.printBoard();
			System.out.println(gb.sigmaKurangAndX());
			System.out.println("Solvable: " + gb.isSolvable());
			// check movement
			gb.moveLeft();
			gb.printBoard();
			gb.moveUp();
			gb.printBoard();
			gb.moveRight();
			gb.printBoard();
			gb.moveDown();
			gb.printBoard();
			// check copy constructor
			GameBoard gb2 = new GameBoard(gb);
			gb2.moveDown();
			gb2.printBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public GameBoard() {
		this.arr = new int[4][4];
		this.emptyCellRow = -1;
		this.emptyCellCol = -1;
	}
	
	/* Constructor pembuatan GameBoard dari pembacaan file */
	public GameBoard(String configPath) throws Exception {
		this();
		File config = new File(configPath);
		Scanner reader = new Scanner(config);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				this.arr[i][j] = reader.nextInt();
				if(this.arr[i][j] == 16) {
					this.emptyCellRow = i;
					this.emptyCellCol = j;
				}
			}
		}
		reader.close();
	}
	
	public GameBoard(GameBoard gb) {
		this();
		this.emptyCellRow = gb.emptyCellRow;
		this.emptyCellCol = gb.emptyCellCol;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				this.arr[i][j] = gb.arr[i][j];
			}
		}
	}
	
	/* Pengecekan status awal */
	public boolean isSolvable() {
		return this.sigmaKurangAndX() % 2 == 0;
	}
	
	public int sigmaKurangAndX() {
		int sum = 0;
		for(int i = 1; i <= 16; i++) {
			sum += this.kurang(i);
		}
		int X = (this.emptyCellRow + this.emptyCellCol) % 2 == 0 ? 0 : 1;
		sum += X;
		
		return sum;
	}
	
	public int kurang(int i) {
		boolean found = false;
		int rowIdx = 0;
		int colIdx = 0;
		for(rowIdx = 0; rowIdx < 4; rowIdx++) {
			for(colIdx = 0; colIdx < 4; colIdx++) {
				if(this.arr[rowIdx][colIdx] == i) {
					found = true;
					break;
				}
			}
			if(found)
				break;
		}
		
		int lessCount = 0;
		for(int a = rowIdx; a < 4; a++) {
			for(int b = colIdx; b < 4; b++) {
				if(this.arr[a][b] < i) {
					lessCount++;
				}
			}
			colIdx = 0;
		}
		
		return lessCount;
	}
	
	/* Mengecek apakah dari state sekarang bisa bergeser ke arah tertentu */
	public boolean canMoveRight() {
		return this.emptyCellCol < 3;
	}
	public boolean canMoveLeft() {
		return this.emptyCellCol > 0;
	}
	public boolean canMoveDown() {
		return this.emptyCellRow < 3;
	}
	public boolean canMoveUp() {
		return this.emptyCellRow > 0;
	}
	
	/* Aksi move, asumsi bisa (sudah dicek dengan canMove<direction>) */
	public void moveRight() {
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow][emptyCellCol + 1];
		this.arr[emptyCellRow][emptyCellCol + 1] = 16;
		this.emptyCellCol++;
	}
	public void moveLeft() {
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow][emptyCellCol - 1];
		this.arr[emptyCellRow][emptyCellCol - 1] = 16;
		this.emptyCellCol--;
	}
	public void moveDown() {
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow + 1][emptyCellCol];
		this.arr[emptyCellRow + 1][emptyCellCol] = 16;
		this.emptyCellRow++;
	}
	public void moveUp() {
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow - 1][emptyCellCol];
		this.arr[emptyCellRow - 1][emptyCellCol] = 16;
		this.emptyCellRow--;
	}
	
	
	/* Mencetak papan */
	public void printBoard() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(i == this.emptyCellRow && j == this.emptyCellCol) {
					System.out.print("   ");
				} else {
					System.out.printf("%2d ", arr[i][j]);
				}
			}
			System.out.println();
		}
	}
}

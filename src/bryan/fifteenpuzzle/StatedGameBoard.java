package bryan.fifteenpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Kelas yang menyimpan papan dengan perhitungan biaya dan sejarah perpindahannya
// digunakan untuk node pada pohon di PriorityQueue
public class StatedGameBoard extends GameBoard implements Comparable<StatedGameBoard> {
	public enum Direction {
		LEFT, UP, RIGHT, DOWN
	}
	
	protected int currentCost;	// cost dari root sampai node ini (terealisasi)
	protected int estimatedCost;	// estimasi node ini sampai tujuan
	public List<Direction> steps;
	
	public StatedGameBoard() {
		super();
		this.currentCost = 0;
		this.estimatedCost = 0;
		this.steps = new ArrayList<>();
	}
	
	public StatedGameBoard(String configPath) throws Exception {
		super(configPath);
		this.currentCost = 0;
		this.estimatedCost = 0;
		this.steps = new ArrayList<>();
	}
	
	public StatedGameBoard(StatedGameBoard sgb) {
		super(sgb);
		this.currentCost = sgb.currentCost;
		this.estimatedCost = sgb.estimatedCost;
		this.steps = new ArrayList<>(sgb.steps);
	}
	
	/* Implementasi compareTo untuk PriorityQueue */
	@Override
	public int compareTo(StatedGameBoard o) {
		if(this.currentCost + this.estimatedCost < o.currentCost + o.estimatedCost) {
			return -1;
		} else if(this.currentCost + this.estimatedCost > o.currentCost + o.estimatedCost) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/* Update dan perhitungan harga, serta aksesornya */
	public void updateEstimatedCost() {
		this.estimatedCost = this.calculateEstimatedCost();
	}
	
	public int calculateEstimatedCost() {
		return 0;
	}
	
	public int getTotalCost() {
		return this.currentCost + this.estimatedCost;
	}
	
	public boolean isSolution() {
		return this.estimatedCost == 0;
	}
	
	public List<Direction> getSteps() {
		return this.steps;
	}
	
	public void displayFromSteps(List<Direction> steps) {
		for(Direction dir : steps) {
			System.out.println(dir);
			if(dir == Direction.LEFT) {
				super.moveLeft();
				this.printBoard();
			} else if(dir == Direction.UP) {
				super.moveUp();
				this.printBoard();
			} else if(dir == Direction.RIGHT) {
				super.moveRight();
				this.printBoard();
			} else if(dir == Direction.DOWN) {
				super.moveDown();
				this.printBoard();
			}
			System.out.println();
		}
	}
	
	/* Override move<direction> agar selain memindahkan juga mengupdate harga estimasi */
	@Override
	public void moveRight() {
		super.moveRight();
		this.currentCost++;
		this.updateEstimatedCost();
		this.steps.add(Direction.RIGHT);
	}
	@Override
	public void moveLeft() {
		super.moveLeft();
		this.currentCost++;
		this.updateEstimatedCost();
		this.steps.add(Direction.LEFT);
	}
	@Override
	public void moveDown() {
		super.moveDown();
		this.currentCost++;
		this.updateEstimatedCost();
		this.steps.add(Direction.DOWN);
	}
	@Override
	public void moveUp() {
		super.moveUp();
		this.currentCost++;
		this.updateEstimatedCost();
		this.steps.add(Direction.UP);
	}
	public void printSteps() {
		for(Direction dir : this.steps) {
			System.out.println(dir);
		}
	}
}

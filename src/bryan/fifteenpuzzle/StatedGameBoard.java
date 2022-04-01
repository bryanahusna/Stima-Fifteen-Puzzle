package bryan.fifteenpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class StatedGameBoard extends GameBoard implements Comparable<StatedGameBoard> {
	public enum Direction {
		LEFT, UP, RIGHT, DOWN
	}
	
	protected int currentCost;	// cost dari root sampai node ini (terealisasi)
	protected int estimatedCost;	// estimasi node ini sampai tujuan
	private List<Direction> steps;
	
	public static void main(String[] args) {
		PriorityQueue<StatedGameBoard> pq = new PriorityQueue<>();
		try {
			StatedGameBoard sgd = new StatedGameBoard("test/config1.txt");
			StatedGameBoard sgd1 = new StatedGameBoard(sgd);
			StatedGameBoard sgd2 = new StatedGameBoard(sgd);
			StatedGameBoard sgd3 = new StatedGameBoard(sgd);
			StatedGameBoard sgd4 = new StatedGameBoard(sgd);
			
			sgd1.moveUp();
			sgd2.moveRight();
			sgd3.moveDown();
			sgd4.moveLeft();
			
			pq.add(sgd1);
			pq.add(sgd2);
			pq.add(sgd3);
			pq.add(sgd4);
			while(!pq.isEmpty()) {
				System.out.println(pq.poll().getTotalCost());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
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
			// Jika totalCost keduanya sama, prioritaskan yang memiliki currentCost lebih tinggi (terdalam)
			/*if(this.currentCost > o.currentCost) {
				return 1;
			} else if(this.currentCost < o.currentCost) {
				return -1;
			} else {
				return 0;
			}*/
		}
	}
	
	/* Update dan perhitungan harga, serta aksesornya */
	public void updateEstimatedCost() {
		this.estimatedCost = this.calculateEstimatedCost();
	}
	
	public int calculateEstimatedCost() {
		int estimated = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(this.arr[i][j] == 16) {
					continue;
				} else {
					//estimated += arr[i][j] == (4*i + j + 1) ? 0 : 1;
					int inum = (arr[i][j]-1) / 4;
					int jnum = (arr[i][j]-1) % 4;
					estimated += Math.abs(inum - i) + Math.abs(jnum - j);
				}
			}
		}
		return estimated;
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

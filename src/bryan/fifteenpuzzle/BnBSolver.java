package bryan.fifteenpuzzle;

import java.util.List;
import java.util.PriorityQueue;

import bryan.fifteenpuzzle.StatedGameBoard.Direction;

// Kelas Solver untuk algoritma Branch and Bound karena mirip
// Ada dua metode: titik tidak tepat (kode 1) dan manhattan distance (kode 2)
// Secara umum manhattan distance lebih efisien daripada titik tidak tepat
public class BnBSolver implements Solver {
	private StatedGameBoard gameBoardInitial;	// menyimpan status awal
	PriorityQueue<StatedGameBoard> stateQueue;
	private List<Direction> solutionSteps;
	
	public boolean isFound = false;
	private int simpulDibangkitkan = 0;
	private int simpulDiperiksa = 0;
	
	public BnBSolver(int method) {
		this.stateQueue = new PriorityQueue<>();
		if(method == 1) {
			this.gameBoardInitial = new OutPositionBoard(); 
		} else {
			this.gameBoardInitial = new ManhattanBoard();
		}
		this.gameBoardInitial.updateEstimatedCost();
	}
	
	public BnBSolver(String configPath, int method) throws Exception {
		this.stateQueue = new PriorityQueue<>();
		if(method == 1) {
			this.gameBoardInitial = new OutPositionBoard(configPath);
		} else {
			this.gameBoardInitial = new ManhattanBoard(configPath);
		}
		this.gameBoardInitial.updateEstimatedCost();
	}
	
	public BnBSolver(StatedGameBoard gameBoard) {
		this.stateQueue = new PriorityQueue<>();
		this.gameBoardInitial = new StatedGameBoard(gameBoard);
		this.gameBoardInitial.updateEstimatedCost();
	}
	
	@Override
	public void startSolving() {
		this.simpulDibangkitkan = 0;
		this.simpulDiperiksa = 0;
		
		StatedGameBoard sgb;
		if(this.gameBoardInitial instanceof ManhattanBoard) {
			sgb = new ManhattanBoard((ManhattanBoard)this.gameBoardInitial);
		} else {
			sgb = new OutPositionBoard((OutPositionBoard)this.gameBoardInitial);
		}
		stateQueue.add(sgb);
		
		this.simpulDiperiksa = 0;
		while(!stateQueue.isEmpty() && !isFound) {
			this.simpulDiperiksa++;
			sgb = stateQueue.poll();
			if(sgb.isSolution()) {
				this.isFound = true;
				this.solutionSteps = sgb.getSteps();
			} else {
				if(sgb.canMoveLeft()) {
					StatedGameBoard temp;
					if(this.gameBoardInitial instanceof ManhattanBoard) {
						temp = new ManhattanBoard((ManhattanBoard)sgb);
					} else {
						temp = new OutPositionBoard((OutPositionBoard)sgb);
					}
					temp.moveLeft();
					stateQueue.add(temp);
					this.simpulDibangkitkan++;
				}
				if(sgb.canMoveUp()) {
					StatedGameBoard temp;
					if(this.gameBoardInitial instanceof ManhattanBoard) {
						temp = new ManhattanBoard((ManhattanBoard)sgb);
					} else {
						temp = new OutPositionBoard((OutPositionBoard)sgb);
					}
					temp.moveUp();
					stateQueue.add(temp);
					this.simpulDibangkitkan++;
				}
				if(sgb.canMoveRight()) {
					StatedGameBoard temp;
					if(this.gameBoardInitial instanceof ManhattanBoard) {
						temp = new ManhattanBoard((ManhattanBoard)sgb);
					} else {
						temp = new OutPositionBoard((OutPositionBoard)sgb);
					}
					temp.moveRight();
					stateQueue.add(temp);
					this.simpulDibangkitkan++;
				}
				if(sgb.canMoveDown()) {
					StatedGameBoard temp;
					if(this.gameBoardInitial instanceof ManhattanBoard) {
						temp = new ManhattanBoard((ManhattanBoard)sgb);
					} else {
						temp = new OutPositionBoard((OutPositionBoard)sgb);
					}
					temp.moveDown();
					stateQueue.add(temp);
					this.simpulDibangkitkan++;
				}
			}
		}
	}
	@Override
	public void displaySolution() {
		StatedGameBoard sgb = new StatedGameBoard(this.gameBoardInitial);
		sgb.displayFromSteps(this.solutionSteps);
		System.out.println();
		
		System.out.println("Jumlah langkah solusi: " + this.solutionSteps.size());
		System.out.println("Jumlah simpul dibangkitkan: " + this.simpulDibangkitkan);
		System.out.println("Jumlah simpul diperiksa: " + this.simpulDiperiksa);
	}
	
	@Override
	public StatedGameBoard getGameBoardInitial() {
		return this.gameBoardInitial;
	}
	
	@Override
	public List<Direction> getSolutionSteps() {
		return this.solutionSteps;
	}
}

package bryan.fifteenpuzzle;

import java.util.List;
import java.util.PriorityQueue;

import bryan.fifteenpuzzle.StatedGameBoard.Direction;

public class Solver {
	private StatedGameBoard gameBoardInitial;
	private List<Direction> solutionSteps;
	PriorityQueue<StatedGameBoard> stateQueue;
	public boolean isFound = false;
	
	public static void main(String[] args) {
		try {
			Solver solver = new Solver("test/config5_solvable.txt");
			if(solver.gameBoardInitial.isSolvable())
				solver.startSolving();
			else
				System.out.println("Not solvable");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Solver(String configPath) throws Exception {
		this.stateQueue = new PriorityQueue<>();
		this.gameBoardInitial = new StatedGameBoard(configPath);
		this.gameBoardInitial.updateEstimatedCost();
	}
	
	public Solver(StatedGameBoard gameBoard) {
		this.stateQueue = new PriorityQueue<>();
		this.gameBoardInitial = new StatedGameBoard(gameBoard);
		this.gameBoardInitial.updateEstimatedCost();
	}
	
	public void startSolving() {
		StatedGameBoard sgb = new StatedGameBoard(gameBoardInitial);
		sgb.printBoard(); System.out.println();
		stateQueue.add(sgb);
		int evaluated = 0;
		int bangkited = 0;
		while(!stateQueue.isEmpty() && !isFound) {
			evaluated++;
			sgb = stateQueue.poll();
			if(sgb.isSolution()) {
				this.isFound = true;
				this.solutionSteps = sgb.getSteps();
				
				sgb.printBoard(); System.out.println();
				System.out.println("Total biaya: " + sgb.getTotalCost());
				System.out.println("Yang dievaluasi: " + evaluated);
				System.out.println("Yang dibangkitkan: " + bangkited);
				sgb.printSteps();
			} else {
				if(sgb.canMoveLeft()) {
					StatedGameBoard temp = new StatedGameBoard(sgb);
					temp.moveLeft();
					stateQueue.add(temp);
					bangkited++;
				}
				if(sgb.canMoveUp()) {
					StatedGameBoard temp = new StatedGameBoard(sgb);
					temp.moveUp();
					stateQueue.add(temp);
					bangkited++;
				}
				if(sgb.canMoveRight()) {
					StatedGameBoard temp = new StatedGameBoard(sgb);
					temp.moveRight();
					stateQueue.add(temp);
					bangkited++;
				}
				if(sgb.canMoveDown()) {
					StatedGameBoard temp = new StatedGameBoard(sgb);
					temp.moveDown();
					stateQueue.add(temp);
					bangkited++;
				}
			}
		}
	}
}

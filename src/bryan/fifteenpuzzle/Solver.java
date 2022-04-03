package bryan.fifteenpuzzle;

import java.util.List;

import bryan.fifteenpuzzle.StatedGameBoard.Direction;

public interface Solver {
	public StatedGameBoard getGameBoardInitial();
	public void startSolving();
	public void displaySolution();
	public List<Direction> getSolutionSteps();
}

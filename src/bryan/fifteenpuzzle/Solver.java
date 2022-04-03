package bryan.fifteenpuzzle;

import java.util.List;

import bryan.fifteenpuzzle.StatedGameBoard.Direction;

// Interface untuk menyatukan Solver dan UltraSolver
// keduanya berbeda cukup jauh, tetapi memiliki interface yang sama
public interface Solver {
	public StatedGameBoard getGameBoardInitial();
	public void startSolving();
	public void displaySolution();
	public List<Direction> getSolutionSteps();
}

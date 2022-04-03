package bryan.fifteenpuzzle;

public interface Solver {
	public StatedGameBoard getGameBoardInitial();
	public void startSolving();
	public void displaySolution();
}

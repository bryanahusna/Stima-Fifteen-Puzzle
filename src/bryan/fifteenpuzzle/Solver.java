package bryan.fifteenpuzzle;

public class Solver {
	private GameBoard gameBoard;
	
	
	public Solver(String configPath) throws Exception {
		this.gameBoard = new GameBoard(configPath);
	}
	
	public Solver(GameBoard gameBoard) {
		this.gameBoard = new GameBoard(gameBoard);
	}
	
	
}

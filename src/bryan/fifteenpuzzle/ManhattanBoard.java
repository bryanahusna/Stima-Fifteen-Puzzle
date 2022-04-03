package bryan.fifteenpuzzle;

// Perhitungan dengan estimasi jarak Manhattan
public class ManhattanBoard extends StatedGameBoard {
	public ManhattanBoard() {
		super();
	}
	public ManhattanBoard(String configPath) throws Exception{
		super(configPath);
	}
	public ManhattanBoard(ManhattanBoard mb) {
		super(mb);
	}

	@Override
	public int calculateEstimatedCost() {
		int estimated = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(this.arr[i][j] == 16) {
					continue;
				} else {
					int inum = (arr[i][j]-1) / 4;
					int jnum = (arr[i][j]-1) % 4;
					estimated += Math.abs(inum - i) + Math.abs(jnum - j);
				}
			}
		}
		return estimated;
	}
}

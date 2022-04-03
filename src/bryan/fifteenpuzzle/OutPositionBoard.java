package bryan.fifteenpuzzle;

// Kelas yang perhitungannya dengan estimasi tile yang tidak berada pada tempatnya
public class OutPositionBoard extends StatedGameBoard {
	public OutPositionBoard() {
		super();
	}
	public OutPositionBoard(String configPath) throws Exception{
		super(configPath);
	}
	public OutPositionBoard(OutPositionBoard opb) {
		super(opb);
	}

	@Override
	public int calculateEstimatedCost() {
		int estimated = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(this.arr[i][j] == 16) {
					continue;
				} else {
					estimated += arr[i][j] == (4*i + j + 1) ? 0 : 1;
				}
			}
		}
		return estimated;
	}
}

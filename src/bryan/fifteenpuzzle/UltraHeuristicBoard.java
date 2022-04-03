package bryan.fifteenpuzzle;

import bryan.fifteenpuzzle.NextTarget.Type;

// Kelas papan untuk pencarian solusi yang cepat, TETAPI tidak selalu optimal
// keunggulannya selalu cepat
public class UltraHeuristicBoard extends StatedGameBoard{
	private boolean[] locked;	// locked[i] berarti posisi i sudah benar sehingga tidak boleh dipindah
	private NextTarget nextTarget;
	
	public UltraHeuristicBoard() {
		super();
		this.locked = new boolean[16];
		for(int i = 0; i < 16; i++) {
			this.locked[i] = false;
		}
		this.nextTarget = new NextTarget(1, 1, Type.MOVEANDLOCK);
	}
	
	public UltraHeuristicBoard(String configPath) throws Exception {
		super(configPath);
		this.locked = new boolean[16];
		for(int i = 0; i < 16; i++) {
			this.locked[i] = false;
		}
		this.nextTarget = new NextTarget(1, 1, Type.MOVEANDLOCK);
	}
	
	public UltraHeuristicBoard(UltraHeuristicBoard uhb) {
		super(uhb);
		this.locked = new boolean[16];
		for(int i = 0; i < 16; i++) {
			this.locked[i] = uhb.locked[i];
		}
		this.nextTarget = uhb.nextTarget;
	}
	
	public void setTarget(NextTarget nt) {
		this.nextTarget = nt;
	}
	
	@Override
	public void updateEstimatedCost() {
		this.estimatedCost = this.calculateEstimatedCost();
	}
	@Override
	public int calculateEstimatedCost() {
		int fromNumToPosDist = this.calculateManhattanDistance(nextTarget.fromNum, nextTarget.toPos);
		if(fromNumToPosDist == 0) return 0;
		
		int fromNumPos = this.findPos(this.nextTarget.fromNum);
		int ifrom = (fromNumPos-1) / 4;
		int jfrom = (fromNumPos-1) % 4;
		int itarget = (nextTarget.toPos-1) / 4;
		int jtarget = (nextTarget.toPos-1) % 4;
		int iempty = this.emptyCellRow;
		int jempty = this.emptyCellCol;
		
		if(ifrom == itarget) {
			int itargetemptyhor = ifrom;
			int jtargetemptyhor = jfrom + (jtarget-jfrom)/Math.abs(jtarget-jfrom);
			
			return fromNumToPosDist*4 + Math.abs(itargetemptyhor-iempty) + Math.abs(jtargetemptyhor-jempty);
		} else if(jfrom == jtarget) {
			int itargetemptyvert = ifrom + (itarget-ifrom)/Math.abs(itarget-ifrom);
			int jtargetemptyvert = jfrom;
			
			return fromNumToPosDist*4 + Math.abs(itargetemptyvert-iempty) + Math.abs(jtargetemptyvert-jempty);
		} else {
			int itargetemptyvert = ifrom + (itarget-ifrom)/Math.abs(itarget-ifrom);
			int jtargetemptyvert = jfrom;
			
			int itargetemptyhor = ifrom;
			int jtargetemptyhor = jfrom + (jtarget-jfrom)/Math.abs(jtarget-jfrom);
			int targetemptydistmin = Math.min(Math.abs(itargetemptyhor-iempty) + Math.abs(jtargetemptyhor-jempty), Math.abs(itargetemptyvert-iempty) + Math.abs(jtargetemptyvert-jempty));
			
			return fromNumToPosDist*4 +  targetemptydistmin;
		}
	}
	
	public boolean isTargetFulfilled() {
		return this.calculateEstimatedCost() == 0;
	}
	
	public void lockPosition(int pos) {
		this.locked[pos-1] = true;
	}
	public void unlockPosition(int pos) {
		this.locked[pos-1] = false;
	}
	public boolean isLocked(int pos) {
		return this.locked[pos-1];
	}
	
	
	/* Override canMove<dir> agar mengakomodasi lock */
	@Override
	public boolean canMoveRight() {
		int emptyNumber = GameBoard.convertCoorToNumber(this.emptyCellRow, this.emptyCellCol);
		return super.canMoveRight() && !this.locked[emptyNumber-1 + 1];
	}
	@Override
	public boolean canMoveDown() {
		int emptyNumber = GameBoard.convertCoorToNumber(this.emptyCellRow, this.emptyCellCol);
		return super.canMoveDown() && !this.locked[emptyNumber-1 + 4];
	}
	@Override
	public boolean canMoveLeft() {
		int emptyNumber = GameBoard.convertCoorToNumber(this.emptyCellRow, this.emptyCellCol);
		return super.canMoveLeft() && !this.locked[emptyNumber-1 - 1];
	}
	@Override
	public boolean canMoveUp() {
		int emptyNumber = GameBoard.convertCoorToNumber(this.emptyCellRow, this.emptyCellCol);
		return super.canMoveUp() && !this.locked[emptyNumber-1 - 4];
	}
}

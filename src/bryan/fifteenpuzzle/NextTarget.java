package bryan.fifteenpuzzle;

// Kelas digunakan untuk UltraHeuristic
public class NextTarget{
	// num dan pos adalah 1-indexed
	public enum Type{
		MOVEANDLOCK,
		MOVEONLY,
		LOCKONLY,
		UNLOCKONLY
	} 
	
	public int fromNum;
	public int toPos;
	public Type type;
	public NextTarget(int fromNum, int toPos, Type type) {
		this.fromNum = fromNum;
		this.toPos = toPos;
		this.type = type;
	}
}

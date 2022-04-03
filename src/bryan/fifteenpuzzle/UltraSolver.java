package bryan.fifteenpuzzle;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import bryan.fifteenpuzzle.NextTarget.Type;
import bryan.fifteenpuzzle.StatedGameBoard.Direction;

public class UltraSolver implements Solver{
	private UltraHeuristicBoard gameBoardInitial;
	public boolean isFound = false;
	Queue<NextTarget> nextTargetQueue;
	private List<Direction> solutionSteps;
	private int simpulDibangkitkan = 0;
	private int simpulDiperiksa = 0;
	
	
	public static void main(String[] args) {
		try {
			UltraSolver us = new UltraSolver("test/config9_solvable.txt");
			if(us.gameBoardInitial.isSolvable())
				us.startSolving();
			else
				System.out.println("Tidak dapat diselesaikan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UltraSolver() {
		this.gameBoardInitial = new UltraHeuristicBoard();
		this.gameBoardInitial.updateEstimatedCost();
		this.initTargetQueue();
	}
	
	public UltraSolver(String configPath) throws Exception {
		this.gameBoardInitial = new UltraHeuristicBoard(configPath);
		this.gameBoardInitial.updateEstimatedCost();
		this.initTargetQueue();
	}
	
	public UltraSolver(UltraHeuristicBoard gameBoard) {
		this.gameBoardInitial = new UltraHeuristicBoard(gameBoard);
		this.initTargetQueue();
	}
	
	public void initTargetQueue() {
		this.nextTargetQueue = new LinkedList<>();
		
		this.nextTargetQueue.add(new NextTarget(1, 1, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(2, 2, Type.MOVEANDLOCK));
		
		this.nextTargetQueue.add(new NextTarget(4, 3, Type.MOVEONLY));
		this.nextTargetQueue.add(new NextTarget(4, 3, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(3, 7, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 3, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(4, 4, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 7, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(3, 3, Type.MOVEANDLOCK));
		// Row 1 beres
		
		this.nextTargetQueue.add(new NextTarget(5, 5, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(6, 6, Type.MOVEANDLOCK));
		
		this.nextTargetQueue.add(new NextTarget(8, 7, Type.MOVEONLY));
		this.nextTargetQueue.add(new NextTarget(8, 7, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(7, 11, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 7, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(8, 8, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 11, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(7, 7, Type.MOVEANDLOCK));
		// ROW 2 beres
		
		this.nextTargetQueue.add(new NextTarget(13, 9, Type.MOVEONLY));
		this.nextTargetQueue.add(new NextTarget(13, 9, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(9, 10, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 9, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(13, 13, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 10, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(9, 9, Type.MOVEANDLOCK));
		// KOLOM 1 beres
		
		this.nextTargetQueue.add(new NextTarget(14, 10, Type.MOVEONLY));
		this.nextTargetQueue.add(new NextTarget(14, 10, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(10, 11, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 10, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(14, 14, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(-99, 11, Type.UNLOCKONLY));
		this.nextTargetQueue.add(new NextTarget(10, 10, Type.MOVEANDLOCK));
		// KOLOM 2 beres
		
		this.nextTargetQueue.add(new NextTarget(11, 11, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(12, 12, Type.MOVEANDLOCK));
		this.nextTargetQueue.add(new NextTarget(15, 15, Type.MOVEANDLOCK));
		// Semua beres
	}
	
	@Override
	public void startSolving() {
		this.simpulDibangkitkan = 0;
		this.simpulDiperiksa = 0;
		
		UltraHeuristicBoard uhb = new UltraHeuristicBoard(this.gameBoardInitial);
		while(!this.nextTargetQueue.isEmpty()) {
			int targetPos = this.nextTargetQueue.peek().toPos;
			int fromNum = this.nextTargetQueue.peek().fromNum;
			boolean passNow = false;
			
			if(targetPos != fromNum && fromNum > 0) {
				int nextAdjusment = -1;
				if(uhb.isThePos(fromNum, 15) || uhb.isThePos(fromNum, 12) || uhb.isThePos(fromNum, 11)) {
					
				} else if(fromNum == 4 && (uhb.isTheRightIs(fromNum, 3) || uhb.isTheBelowIs(fromNum, 3) || uhb.isTheRightBelowIs(fromNum, 3))){
					nextAdjusment = 3;
					passNow = true;
				} else if(fromNum == 8 && (uhb.isTheRightIs(fromNum, 7) || uhb.isTheBelowIs(fromNum, 7) || uhb.isTheRightBelowIs(fromNum, 7))){
					passNow = true;
					nextAdjusment = 7;
				} else if(fromNum == 13 && (uhb.isTheRightIs(13, 9) || uhb.isTheBelowIs(13, 9) || uhb.isTheRightBelowIs(fromNum, 9))){
					passNow = true;
					nextAdjusment = 9;
				} else if(fromNum == 14 && (uhb.isTheRightIs(fromNum, 10) || uhb.isTheBelowIs(fromNum, 10) || uhb.isTheRightBelowIs(fromNum, 10))){
					passNow = true;
					nextAdjusment = 10;
				}
				if(passNow) {
					uhb = this.solveNext(uhb, new NextTarget(nextAdjusment, 16, Type.MOVEONLY));
				}
			}
			if(!passNow) {
				uhb = this.solveNext(uhb, this.nextTargetQueue.poll());
			}
		}
		
		this.solutionSteps = uhb.steps;
		this.isFound = true;
	}
	
	public UltraHeuristicBoard solveNext(UltraHeuristicBoard uhb, NextTarget nextTarget) {
		if(nextTarget.type == Type.UNLOCKONLY) {
			uhb.unlockPosition(nextTarget.toPos);
			return uhb;
		}
		
		PriorityQueue<UltraHeuristicBoard> pq = new PriorityQueue<>();
		UltraHeuristicBoard current = new UltraHeuristicBoard(uhb);
		current.setTarget(nextTarget);
		
		pq.add(current);
		boolean found = false;
		while(!pq.isEmpty() && !found) {
			this.simpulDiperiksa++;
			current = pq.poll();
			if(current.isTargetFulfilled()) {
				found = true;
			} else {
				if(current.canMoveUp()) {
					UltraHeuristicBoard temp = new UltraHeuristicBoard(current);
					temp.moveUp();
					pq.add(temp);
					this.simpulDibangkitkan++;
				}
				if(current.canMoveDown()) {
					UltraHeuristicBoard temp = new UltraHeuristicBoard(current);
					temp.moveDown();
					pq.add(temp);
					this.simpulDibangkitkan++;
				}
				if(current.canMoveLeft()) {
					UltraHeuristicBoard temp = new UltraHeuristicBoard(current);
					temp.moveLeft();
					pq.add(temp);
					this.simpulDibangkitkan++;
				}
				if(current.canMoveRight()) {
					UltraHeuristicBoard temp = new UltraHeuristicBoard(current);
					temp.moveRight();
					pq.add(temp);
					this.simpulDibangkitkan++;
				}
			}
		}
		if(nextTarget.type == Type.MOVEANDLOCK)
			current.lockPosition(nextTarget.toPos);
		
		return current;
		
	}
	
	@Override
	public void displaySolution() {
		UltraHeuristicBoard uhb = new UltraHeuristicBoard(this.gameBoardInitial);
		uhb.displayFromSteps(this.solutionSteps);
		System.out.println();
		
		System.out.println("Jumlah langkah solusi: " + this.solutionSteps.size());
		System.out.println("Jumlah simpul dibangkitkan: " + this.simpulDibangkitkan);
		System.out.println("Jumlah simpul diperiksa: " + this.simpulDiperiksa);
	}
	
	@Override
	public UltraHeuristicBoard getGameBoardInitial() {
		return this.gameBoardInitial;
	}
}

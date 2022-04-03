import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import bryan.fifteenpuzzle.BnBSolver;
import bryan.fifteenpuzzle.GraphicalBoard;
import bryan.fifteenpuzzle.Solver;
import bryan.fifteenpuzzle.StatedGameBoard;
import bryan.fifteenpuzzle.UltraSolver;

public class Main implements ActionListener {
	public static void main(String[] args) {
		try {
			
			//Solver solver = new BnBSolver("test/config1_solvable.txt", 2);
			Solver solver = new UltraSolver("test/config1_solvable.txt");
			solver.startSolving();
			GraphicalBoard gb = new GraphicalBoard(solver.getGameBoardInitial(), solver.getSolutionSteps());
			gb.animate();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		
		int src, method;
		String configPath = null;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Selamat datang di 15-Puzzle Solver");
		System.out.println("Dibuat oleh: Bryan Amirul Husna / 13520146");
		System.out.println();
		
		System.out.println("Asal Puzzle:");
		System.out.println("1. Randomly generated");
		System.out.println("2. File");
		System.out.print("Pilihan: ");
		src = scanner.nextInt();
		System.out.println();
		if(src < 1 || src > 2) {
			System.out.println("Masukkan tidak valid!");
			scanner.close();
			return;
		}
		
		if(src == 2) {
			System.out.print("File Konfigurasi (harus terletak di folder testing): ");
			configPath = "test/" + scanner.next();
			try{
				File file = new File(configPath);
				Scanner temp = new Scanner(file);
				temp.close();
			} catch(Exception e) {
				System.out.println("File tidak ada (di folder test)");
				scanner.close();
				return;
			}
		}
		
		
		System.out.println("Metode:");
		System.out.println("1. B&B posisi tidak tepat");
		System.out.println("2. B&B jarak manhattan");
		System.out.println("3. Cara kreasi sendiri (UltraHeuristic), tidak selalu optimal tetapi perhitungan selalu cepat");
		System.out.print("Pilihan: ");
		method = scanner.nextInt();
		System.out.println();
		if(method < 1 || method > 3) {
			System.out.println("Masukkan tidak valid!");
			scanner.close();
			return;
		}
		scanner.close();
		
		try {
			Solver s;
			if(method == 3) {
				UltraSolver us;
				if(src == 2)
					us = new UltraSolver(configPath);
				else
					us = new UltraSolver();
				s = us;
			} else {
				BnBSolver bs;
				if(method == 1) {
					if(src == 2)
						bs = new BnBSolver(configPath, 1);
					else
						bs = new BnBSolver(1);
				} else {
					if(src == 2)
						bs = new BnBSolver(configPath, 2);
					else
						bs = new BnBSolver(2);
				}
				s = bs;
			}
			
			System.out.println("Board Initial:");
			s.getGameBoardInitial().printBoard();
			System.out.println();
			for(int i = 1; i <= 16; i++) {
				System.out.printf("Kurang(%d)= %d\n", i, s.getGameBoardInitial().kurang(i));
			}
			System.out.println("X + Sigma Kurang(i) = " + s.getGameBoardInitial().sigmaKurangAndX());
			System.out.println();
			
			if(s.getGameBoardInitial().isSolvable()) {
				System.out.println("Sedang mencari solusi...");
				long startTime = System.nanoTime();
				s.startSolving();
				long duration = System.nanoTime() - startTime;
				duration /= 1000000;	// ubah ke milidetik
				
				System.out.println("Solusi:");
				s.displaySolution();
				System.out.printf("Waktu eksekusi: %d ms\n", duration);
			} else {
				System.out.println("Karena X + Sigma Kurang(i) ganjil, puzzle tidak dapat diselesaikan");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

import java.awt.Component;
import java.io.File;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import bryan.fifteenpuzzle.BnBSolver;
import bryan.fifteenpuzzle.GraphicalBoard;
import bryan.fifteenpuzzle.Solver;
import bryan.fifteenpuzzle.UltraSolver;

// Program Utama
// Menerima masukan pengguna, kemudian memanggil berbagai kelas Solver, lalu menampilkannya di kelas GUI
public class Main {
	public static void main(String[] args) {
		int src, method;	// menyimpan pilihan sumber dan metode
		String configPath = null;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Selamat datang di 15-Puzzle Solver");
		System.out.println("Dibuat oleh: Bryan Amirul Husna / 13520146");
		System.out.println();
		
		// Pemilihan sumber puzzle
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
			System.out.print("File Konfigurasi: ");
			// Menggunakan File Chooser GUI agar konsisten (tidak bergantung di mana program dijalankan)
			JFileChooser fc = new ModifiedFileChooser();
			fc.setDialogTitle("Pilih File");
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				configPath = fc.getSelectedFile().getPath();
				System.out.println(configPath);
				System.out.println();
			}
			// Mengecek apakah file ada, jika tidak ada beri pesan dan keluar
			try{
				File file = new File(configPath);
				Scanner temp = new Scanner(file);
				temp.close();
			} catch(Exception e) {
				if(configPath == null)
						System.out.println("Dibatalkan");
				else
					System.out.println("File tidak ada (di folder test)");
				scanner.close();
				return;
			}
		}
		
		// Pemilihan metode pemecahan
		System.out.println("Metode:");
		System.out.println("1. B&B posisi tidak tepat");
		System.out.println("2. B&B jarak manhattan");
		System.out.println("3. Cara kreasi sendiri, solusi tidak selalu optimal tetapi perhitungan selalu cepat");
		System.out.print("Pilihan: ");
		method = scanner.nextInt();
		System.out.println();
		if(method < 1 || method > 3) {
			System.out.println("Masukkan tidak valid!");
			scanner.close();
			return;
		}
		scanner.close();
		
		// Mencari solusi
		try {
			Solver solver;
			if(method == 3) {
				UltraSolver us;
				if(src == 2)	// dari file
					us = new UltraSolver(configPath);
				else
					us = new UltraSolver();
				solver = us;
			} else {
				BnBSolver bs;
				if(method == 1) {	// metode posisi tidak tepat
					if(src == 2)	// dari file
						bs = new BnBSolver(configPath, 1);
					else
						bs = new BnBSolver(1);	// buat puzzle acak
				} else {						// metode jarak manhattan
					if(src == 2)				// dari file
						bs = new BnBSolver(configPath, 2);
					else
						bs = new BnBSolver(2);	// buat puzzle acak
				}
				solver = bs;
			}
			
			// Pencetakan papan dan solusi
			System.out.println("Board Initial:");
			solver.getGameBoardInitial().printBoard();
			System.out.println();
			for(int i = 1; i <= 16; i++) {
				System.out.printf("Kurang(%d)= %d\n", i, solver.getGameBoardInitial().kurang(i));
			}
			System.out.println("X + Sigma Kurang(i) = " + solver.getGameBoardInitial().sigmaKurangAndX());
			System.out.println();
			
			if(solver.getGameBoardInitial().isSolvable()) {
				System.out.println("Sedang mencari solusi...");
				long startTime = System.nanoTime();
				
				solver.startSolving();		// memulai pemecahan
				
				long duration = System.nanoTime() - startTime;
				duration /= 1000000;	// ubah ke milidetik
				
				System.out.println("Solusi:");
				solver.displaySolution();
				System.out.printf("Waktu eksekusi: %d ms\n", duration);
				
				// Menampilkan GUI, tunggu 2,5 detik dahulu
				System.out.println("\nGUI animasi akan segera muncul pada jendela baru...");
				Thread.sleep(2500);
				GraphicalBoard gb = new GraphicalBoard(solver.getGameBoardInitial(), solver.getSolutionSteps());
			} else {
				System.out.println("Karena X + Sigma Kurang(i) ganjil, puzzle tidak dapat diselesaikan");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}

// Kelas modifikasi JFileChooser
class ModifiedFileChooser extends JFileChooser {
	protected JDialog createDialog(Component parent) {
        JDialog fc = super.createDialog(parent);
        fc.setAlwaysOnTop(true);
        return fc;
    }
}

package bryan.fifteenpuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GraphicalBoard extends StatedGameBoard implements ActionListener {
	public JFrame jf;
	private Cell[][] cells = new Cell[4][4];
	private int k = 0;
	private int i, j;
	private int nextX, nextY;
	private int deltaX = 5, deltaY = 5;
	private boolean curElFinish = true;
	
	Timer tm = new Timer(10, this);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(curElFinish) {
			Direction dir = this.steps.get(this.k);
			if(dir == Direction.LEFT) {
				this.moveLeft();
				Cell cur = this.cells[i][j];
				deltaX = 5;
				deltaY = 0;
				nextX = cur.getBounds().x + 85;
				nextY = cur.getBounds().y;
			} else if(dir == Direction.UP) {
				this.moveUp();
				Cell cur = this.cells[i][j];
				deltaX = 0;
				deltaY = 5;
				nextX = cur.getBounds().x;
				nextY = cur.getBounds().y + 85;
			} else if(dir == Direction.RIGHT) {
				this.moveRight();
				Cell cur = this.cells[i][j];
				deltaX = -5;
				deltaY = 0;
				nextX = cur.getBounds().x - 85;
				nextY = cur.getBounds().y;
			} else if(dir == Direction.DOWN) {
				this.moveDown();
				Cell cur = this.cells[i][j];
				deltaX = 0;
				deltaY = -5;
				nextX = cur.getBounds().x;
				nextY = cur.getBounds().y - 85;
			}
			curElFinish = false;
		} else {
			Cell cur = this.cells[i][j];
			if(cur.getBounds().x == this.nextX && cur.getBounds().y == nextY) {
				this.curElFinish = true;
				this.k++;
				if(k >= this.steps.size()) {
					tm.stop();
				}
			} else {
				cur.setBounds(cur.getBounds().x + deltaX, cur.getBounds().y + deltaY, cur.getBounds().height, cur.getBounds().width);
			}
		}
	}
	
	public GraphicalBoard(StatedGameBoard sgb, List<Direction> steps) {
		super(sgb);
		this.steps = steps;
		
		jf = new JFrame();
		jf.setTitle("15-Puzzle Solver");
		jf.setSize(800, 600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(this.arr[i][j] == 16) continue;
				cells[i][j] = new Cell(this.arr[i][j]);
				jf.add(cells[i][j]);
				cells[i][j].setBounds(20 + 85*j, 20 + 85*i, 80, 80);
			}
		}
		
		tm.start();
	}
	
	public void animate() {
		this.k = 0;
		
		tm.start();
	}
	
	@Override
	public void moveRight() {
		this.i = this.emptyCellRow;
		this.j = this.emptyCellCol;
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow][emptyCellCol + 1];
		this.arr[emptyCellRow][emptyCellCol + 1] = 16;
		this.cells[emptyCellRow][emptyCellCol] = this.cells[emptyCellRow][emptyCellCol + 1];
		this.cells[emptyCellRow][emptyCellCol + 1] = null;
		this.emptyCellCol++;
	}
	@Override
	public void moveLeft() {
		this.i = this.emptyCellRow;
		this.j = this.emptyCellCol;
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow][emptyCellCol - 1];
		this.arr[emptyCellRow][emptyCellCol - 1] = 16;
		this.cells[emptyCellRow][emptyCellCol] = this.cells[emptyCellRow][emptyCellCol - 1];
		this.cells[emptyCellRow][emptyCellCol - 1] = null;
		this.emptyCellCol--;
	}
	@Override
	public void moveDown() {
		this.i = this.emptyCellRow;
		this.j = this.emptyCellCol;
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow + 1][emptyCellCol];
		this.arr[emptyCellRow + 1][emptyCellCol] = 16;
		this.cells[emptyCellRow][emptyCellCol] = this.cells[emptyCellRow + 1][emptyCellCol];
		this.cells[emptyCellRow + 1][emptyCellCol] = null;
		this.emptyCellRow++;
	}
	@Override
	public void moveUp() {
		this.i = this.emptyCellRow;
		this.j = this.emptyCellCol;
		this.arr[emptyCellRow][emptyCellCol] = this.arr[emptyCellRow - 1][emptyCellCol];
		this.arr[emptyCellRow - 1][emptyCellCol] = 16;
		this.cells[emptyCellRow][emptyCellCol] = this.cells[emptyCellRow - 1][emptyCellCol];
		this.cells[emptyCellRow - 1][emptyCellCol] = null;
		this.emptyCellRow--;
	}
}

class Cell extends JTextField {
	public Cell(int number) {
		super.setHorizontalAlignment(JTextField.CENTER);
		super.setFont(new Font("Gill Sans", Font.BOLD, 30));
		super.setEditable(false);
		if(number != 16)
			super.setText(number + "");
		else
			super.setText("");
		super.setBackground(new Color(0x03, 0x25, 0x4c));
		super.setForeground(Color.WHITE);
		super.setMargin(new Insets(10, 10, 10, 10));
	}
}
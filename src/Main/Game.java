package Main;

import java.awt.Point;

import javax.swing.JFrame;

public class Game {
	
	public Game(){
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setTitle("2D Runner");
		f.setContentPane(new GamePanel(f));
		f.setLocation(new Point(500,100));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

package Main;

import java.awt.Point;

import javax.swing.JFrame;

public class Game {

	// creates the jframe for the game
	// this constructur is called by mainmenucontent/actionperformed/startbutton
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

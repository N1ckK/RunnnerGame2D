package Menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Game;

public class MainMenuContent extends JPanel implements ActionListener{
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	private static boolean instanceOpen;
	
	private JFrame f;

	private JButton startGame;
	private JButton options;
	private JButton exit;
	private JLabel scoreL;
	
	public MainMenuContent(JFrame f, int score){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		f.setLocation((int)f.getLocation().getX() -WIDTH/2, (int)f.getLocation().getY() - HEIGHT/2);
		this.f = f;
		setLayout(null);
		instanceOpen = true;
		
		if (score > 0){
			scoreL = new JLabel(String.valueOf(score) + " was your Score !");
			scoreL.setBounds(100, 20,200,40);
			add(scoreL);
		}
		
		startGame = new JButton();
		startGame.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 - 100, 200, 50);
		startGame.setText("Start Game");
		startGame.addActionListener(this);
		add(startGame);
		
		options = new JButton();
		options.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 - 30, 200, 50);
		options.setText("(*Settings*)");
		options.addActionListener(this);
		add(options);
		
		exit = new JButton();
		exit.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 + 40, 200, 50);
		exit.setText("Exit");
		exit.addActionListener(this);
		add(exit);
	}
	
	public static boolean isInstanceOpen(){
		return instanceOpen;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startGame){
			instanceOpen = false;
			f.dispose();
			new Game();
		}
		if (e.getSource() == options){}
		if (e.getSource() == exit){
			System.exit(0);
		}
	}
	
}

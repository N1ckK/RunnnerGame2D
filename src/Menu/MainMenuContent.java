package Menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.Game;

@SuppressWarnings("serial")
public class MainMenuContent extends JPanel implements ActionListener{
	
	// Size of Main Menu Window
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	//new JFrame in order to maniplulate the JFrame in the MainMenu class
	private JFrame f;

	//Buttons are decleared
	private JButton startGame;
	private JButton options;
	private JButton exit;
	private JLabel scoreL;
	
	public MainMenuContent(JFrame f, int score){
		// setting size of the Main Menu window
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// setting the location of the window to center xy
		f.setLocation((int)f.getLocation().getX() -WIDTH/2, (int)f.getLocation().getY() - HEIGHT/2);
		// setting the pointer f to this.f to have a pointer from this jframe
		// to the one in MainMenu
		this.f = f;
		// selsecting no layout inoder to move object (eg buttons) with setlocation xy
		setLayout(null);
		
		// when game first time started score is 0 so no score is shown
		// when you die and are redirected to the Main Menu score > 0
		// so you see your score above
		if (score > 0){
			scoreL = new JLabel(String.valueOf(score) + " was your Score !");
			scoreL.setBounds(100, 20,200,40);
			add(scoreL);
		}
		
		// creating start button
		startGame = new JButton();
		startGame.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 - 100, 200, 50);
		startGame.setText("Start Game");
		startGame.addActionListener(this);
		add(startGame);
		
		//creating option button
		options = new JButton();
		options.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 - 30, 200, 50);
		options.setText("(*Settings*)");
		options.addActionListener(this);
		add(options);
		
		// creating exit button
		exit = new JButton();
		exit.setBounds(WIDTH/2 - 100, HEIGHT/2 - 25 + 40, 200, 50);
		exit.setText("Exit");
		exit.addActionListener(this);
		add(exit);
	}
	
	public void actionPerformed(ActionEvent e) {
		// if you press the startbutton the Main Menu jframe is closed
		// and a new instance of Game is opened
		if (e.getSource() == startGame){
			f.dispose();
			new Game();
		}
		if (e.getSource() == options){}
		// if you press exit the program will exit
		if (e.getSource() == exit){
			System.exit(0);
		}
	}
	
}

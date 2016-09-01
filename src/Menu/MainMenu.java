package Menu;

import javax.swing.JFrame;

public class MainMenu {
	
	public MainMenu(int score){
		JFrame f = new JFrame("Main Menu");
		f.setContentPane(new MainMenuContent(f, score));
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		f.setLocationRelativeTo(null);
			
	}
	
	public static void main (String[] args){
		new MainMenu(0);
	}

}

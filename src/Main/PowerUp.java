package Main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PowerUp extends JPanel {
	
	int x;
	int y;
	int selectedPowerUp;
	BufferedImage[] image;
	
	public PowerUp(JPanel p, int YBACKGROUND, int powerUp){
		image = new BufferedImage[3];
		selectedPowerUp = powerUp;
		try {
			image[0] = ImageIO.read(new File("res/textures/PowerUp1.png"));
			image[1] = ImageIO.read(new File("res/textures/PowerUp2.png"));
			image[2] = ImageIO.read(new File("res/textures/PowerUp3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Random r = new Random();
		x = r.nextInt(p.getWidth() - image[selectedPowerUp].getWidth());
		y = r.nextInt(p.getHeight()) - YBACKGROUND - 750;
	}
	
	public void paint(Graphics g2){
		g2.drawImage(image[selectedPowerUp],x,y,null);	
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x,y,image[selectedPowerUp].getWidth(),image[selectedPowerUp].getHeight());
	}

	public int getPowerUpType(){
		return selectedPowerUp;
	}
}

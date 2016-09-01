package Main;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Obstacle extends JPanel {
	
	int x;
	int y;
	BufferedImage image;
	
	public Obstacle(JPanel p, int YBACKGROUND){
		
		try {
			image = ImageIO.read(new File("res/textures/Rock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Random r = new Random();
		x = r.nextInt(p.getWidth() - image.getWidth());
		y = r.nextInt(p.getHeight()) - YBACKGROUND - 750;
	}
	
	public void paint(Graphics2D g2){
		g2.drawImage(image,x,y,null);	
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(x, y, image.getWidth(), image.getHeight());
	}
}

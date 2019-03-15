package Main;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player implements Runnable {
	
	private static final int WIDTH = 220;
	private static final int HEIGHT = 420;
	
	private BufferedImage[] playerSprites;
	private int index;
	
	private BufferedImage currentImage;
	boolean changeImage;
	int wait;
	
	private int x,y;
	private int velX;
	
	private Thread thread;
	
	public Player(){
		playerSprites = new BufferedImage[3];
		changeImage = false;
		wait = 0;
		velX = 0;
		loadImages();
		x = WIDTH*2/2 - currentImage.getWidth()/2; 
		y = HEIGHT*2 - getImage().getHeight() - 20;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while (true){
			if (wait > 90){
				if (changeImage) index = 0;
				if (!changeImage) index = 1;
				wait = 0;
			}
			currentImage = playerSprites[index];
			x += velX;
			try {
				Thread.sleep(2);
				wait ++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changeImage = !changeImage;
		}
	}
	
	public BufferedImage getImage(){
		return currentImage;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(getX(), getY(), getImage().getWidth(), getImage().getHeight());
	}
	
	boolean left, right;
	
	public void keyHandler(int key, int keyR){
		if (key == KeyEvent.VK_A) left = true;
		if (key == KeyEvent.VK_D) right = true;
		if (keyR == KeyEvent.VK_A) left = false;
		if (keyR == KeyEvent.VK_D) right = false;
		
		if (left && right){
			velX = 0; 
			return;
		}
		if (left){
			velX = -1;
		}
		if (right){
			velX = 1;
		}
		if (!right && !left) velX = 0;
		
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return y;
	}
	
	public void setSmallMode(int time){
		if (time > 0){
			try {
				playerSprites[0] = ImageIO.read(new File("res/textures/Player_Walking1.png"));
				playerSprites[1] = ImageIO.read(new File("res/textures/Player_Walking2.png"));
				playerSprites[2] = ImageIO.read(new File("res/textures/Player_Standing.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				playerSprites[0] = ImageIO.read(new File("res/textures/Player_Walking1_Big.png"));
				playerSprites[1] = ImageIO.read(new File("res/textures/Player_Walking2_Big.png"));
				playerSprites[2] = ImageIO.read(new File("res/textures/Player_Standing_Big.png"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadImages(){
		try {
			playerSprites[0] = ImageIO.read(new File("res/textures/Player_Walking1_Big.png"));
			playerSprites[1] = ImageIO.read(new File("res/textures/Player_Walking2_Big.png"));
			playerSprites[2] = ImageIO.read(new File("res/textures/Player_Standing_Big.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentImage = playerSprites[0];
	}

}

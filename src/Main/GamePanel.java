package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Menu.MainMenu;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	// setting the dimensions of the jframe for the game
	public static final int WIDTH = 220;
	public static final int HEIGHT = 420;
	public static final int SCALE = 2;
	
	// creating jframe to manipulate the frame created in Game.java
	private JFrame f;
	
	// delearing a new instance of player
	private Player player;
	
	// setting up the thread
	private Thread thread;
	private boolean running;
	
	// for random numbers when needed
	private Random r;
	
	//setting up background and background movement
	Image background;
	private int yBackground;
	private int velYBackground;
	
	//this variable manages the loopings of the background
	private int loopCount;
	
	// no. of lives the player has
	private int lives;
	
	// setting up obstacle manager
	private ArrayList<Obstacle> obstacleList;
	private int spawnObstacle;
	private int obstacleSpawnTime;
	
	//setting up powerup manager
	private ArrayList<PowerUp> powerUpList;
	private boolean tinyPotionActive;
	private int tinyPotionTimer;
	private boolean slowPotionActive;
	private int slowPotionTimer;
	private int timeCounter;
	
	public GamePanel(JFrame f){
		//setting dimensions of jframe for the game
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		// adding a keylistener to the game
		f.addKeyListener(this);
		//creating a pointer to the jframe from game.java
		this.f = f;
		// init player
		player = new Player();
		//init thread
		thread = new Thread(this);
		//init random
		r = new Random();
		// starting thread
		thread.start();
	}
	
	private void init(){
		//loading backgorund image
		
		try {
			background = ImageIO.read(new File("res/textures/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//background = ImageLoader.getImage("background.jpg");
		//set variables to their start values
		yBackground = 0;
		velYBackground = 2;
		loopCount = 1;
		lives = 1;
		
		//init obstacleList
		obstacleList = new ArrayList<Obstacle>();
		spawnObstacle = 0;
		obstacleSpawnTime = 100;
		
		//init powerUpList
		powerUpList = new ArrayList<PowerUp>();
		tinyPotionActive = false;
		slowPotionActive = false;
		tinyPotionTimer = 0;
		slowPotionTimer = 0;
		timeCounter = 0;
	
		// setting the boolean in the game loop = true
		running = true;
	}
	
	public void run(){
		//setting up the variables before entering the game loop
		init();
		// gameloop
		while (running){
			// updates game logic
			update();
			// updating screen
			repaint();
			try {
				// pause thread
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		// increasing y coord from the background image
		yBackground += velYBackground;
		// if background is no longer on screan loopcount is incremented
		if (yBackground > getHeight() * loopCount) loopCount ++;
		// disallowing player to exit the screen to the right
		if (player.getX() > getWidth() - player.getImage().getWidth()) player.setX(getWidth() - player.getImage().getWidth());
		// disallowing player to exit the screen to the left
		if (player.getX() < 0) player.setX(0);
		
		spawnObstacle ++;
		// each (value of obstacelSpawnTime) a obstacle is added to the list
		// obstacle.java is called to generae it
		if (spawnObstacle > obstacleSpawnTime){
			obstacleList.add(new Obstacle(this, yBackground));
			spawnObstacle = 0;
			if (obstacleSpawnTime > 40 && Math.random() < 0.3){
				obstacleSpawnTime --;
			}
		}
		// attatching obstacles to background
		for (int i = 0 ; i < obstacleList.size() ; i++){
			obstacleList.get(i).y += velYBackground;
		}
		
		// spawning a powerup every 5000 score
		if (yBackground % 5000 == 0){
			powerUpList.add(new PowerUp(this, yBackground, r.nextInt(3)));
		}
		
		// attatching powerups to background
		for (int i = 0 ; i < powerUpList.size() ; i++){
			powerUpList.get(i).y += velYBackground;
		}
		
		// managing the potion duration
		timeCounter ++;
		if (timeCounter == 200){
			timeCounter = 0;
			if (tinyPotionActive){
				tinyPotionTimer --;
				if (tinyPotionTimer <= 0) tinyPotionActive = false;
				player.setSmallMode(tinyPotionTimer);
			}else{
				tinyPotionTimer = 0;
				player.setSmallMode(tinyPotionTimer);
			}
			if (slowPotionActive){
				slowPotionTimer --;
				if (slowPotionTimer <= 0) slowPotionActive = false;
				velYBackground = 1;
			}else{
				slowPotionTimer = 0;
				velYBackground = 2;
			}
		}
		checkForIntersection();
	}
	
	public void checkForIntersection(){
		// checks if player intersects with obstacle
		for (int i = 0 ; i < obstacleList.size() ; i ++){
			if (obstacleList.get(i).getRectangle().intersects(player.getRectangle())){
				lives --;
				if (lives <= 0){
					new MainMenu(yBackground);
					running = false;
					f.dispose();
				}
				obstacleList.remove(i);
				
			}
		}
		// checks if player intersects with powerup
		for (int i = 0 ; i < powerUpList.size() ; i ++){
			if (powerUpList.get(i).getRectangle().intersects(player.getRectangle())){
				if (powerUpList.get(i).getPowerUpType() == 0){
					lives ++;
				}else if (powerUpList.get(i).getPowerUpType() == 1) {
					tinyPotionActive = true;
					tinyPotionTimer = 15;
				}else if (powerUpList.get(i).getPowerUpType() == 2){
					slowPotionActive = true;
					slowPotionTimer = 10;
				}
				powerUpList.remove(i);
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// draw background twice
		g2.drawImage(background, 0, yBackground - (getHeight()*(loopCount -1)), getWidth(), getHeight(), null);
		g2.drawImage(background, 0, yBackground - (getHeight()*loopCount), getWidth(), getHeight(), null);
		
		//deaw Player
		g2.drawImage(player.getImage(), player.getX(), player.getY(), null);
		
		//g2.drawRect(player.getRectangle().x, player.getRectangle().y, player.getRectangle().width, player.getRectangle().height);
		//g2.drawRect(list.get(0).getRectangle().x, list.get(0).getRectangle().y, list.get(0).getRectangle().width, list.get(0).getRectangle().height);
		
		g2.setFont(new Font("Cambria", Font.BOLD, 20));
		g2.setColor(new Color(0,0,0));
		//score
		g2.drawString(String.valueOf(yBackground), 40, 40);
		//lives
		g2.drawString(String.valueOf(lives + " Life(s)"), getWidth() - g2.getFontMetrics().stringWidth(String.valueOf(lives + " Life(s)")) - 40, 40);
		
		//potion info
		g2.drawString(String.valueOf("Tiny Potion: " + tinyPotionTimer) + " sec", getWidth() - g2.getFontMetrics().stringWidth(String.valueOf("Tiny Potion: " + tinyPotionTimer + " sec")) - 40, 120);
		g2.drawString(String.valueOf("Slow Potion: " + slowPotionTimer) + " sec", getWidth() - g2.getFontMetrics().stringWidth(String.valueOf("Slow Potion: " + slowPotionTimer + " sec")) - 40, 80);
		
		// draw obstacles
		if (obstacleList != null && obstacleList.size() > 0){
			for (int i = 0 ; i < obstacleList.size() ; i++){
				if (obstacleList.get(i).y > getHeight() + 10){
					obstacleList.remove(i);
				}
				obstacleList.get(i).paint(g2);
			}
		}
		//draw powerups
		if (powerUpList != null && powerUpList.size() > 0){
			for (int i = 0 ; i < powerUpList.size() ; i++){
				if (powerUpList.get(i).y > getHeight() + 10){
					powerUpList.remove(i);
				}
				powerUpList.get(i).paint(g2);
			}
		}
	}
	
	// key manager
	int key = 0;
	int keyR = 0;
	
	public void keyPressed(KeyEvent k) {
		key = k.getKeyCode();
		keyR = 0;
		player.keyHandler(key, keyR);
	}
	public void keyReleased(KeyEvent k) {
		keyR = k.getKeyCode();
		key = 0;
		player.keyHandler(key, keyR);
	}
	public void keyTyped(KeyEvent k) {}
}

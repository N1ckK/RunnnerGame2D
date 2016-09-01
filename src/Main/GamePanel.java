package Main;

import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Menu.MainMenu;
import Menu.MainMenuContent;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 220;
	public static final int HEIGHT = 420;
	public static final int SCALE = 2;
	
	private JFrame f;
	
	private Player player;
	private Sound sound;
	
	private Thread thread;
	private boolean running;
	
	private Random r;
	
	BufferedImage background;
	private int yBackground;
	private int velYBackground;
	
	private int loopCount;
	
	private int lives;
	
	private ArrayList<Obstacle> obstacleList;
	private int spawnObstacle;
	private int obstacleSpawnTime;
	
	private ArrayList<PowerUp> powerUpList;
	private boolean tinyPotionActive;
	private int tinyPotionTimer;
	private boolean slowPotionActive;
	private int slowPotionTimer;
	private int timeCounter;
	
	public GamePanel(JFrame f){
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		f.addKeyListener(this);
		this.f = f;
		player = new Player();
		sound = new Sound();
		thread = new Thread(this);
		r = new Random();
		thread.start();
	}
	
	private void init(){
		try {
			background = ImageIO.read(new File("res/textures/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		yBackground = 0;
		velYBackground = 2;
		loopCount = 1;
		lives = 1;
		
		obstacleList = new ArrayList<Obstacle>();
		spawnObstacle = 0;
		obstacleSpawnTime = 100;
		
		powerUpList = new ArrayList<PowerUp>();
		tinyPotionActive = false;
		slowPotionActive = false;
		tinyPotionTimer = 0;
		slowPotionTimer = 0;
		timeCounter = 0;
	
		running = true;
	}
	
	public void run(){
		init();
		
		while (running){
			update();
			checkForIntersection();
			repaint();
			try {
				thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(){
		yBackground += velYBackground;
		if (yBackground > getHeight() * loopCount) loopCount ++;
		if (player.getX() > getWidth() - player.getImage().getWidth()) player.setX(getWidth() - player.getImage().getWidth());
		if (player.getX() < 0) player.setX(0);
		
		spawnObstacle ++;
		if (spawnObstacle > obstacleSpawnTime){
			obstacleList.add(new Obstacle(this, yBackground));
			spawnObstacle = 0;
			if (obstacleSpawnTime > 30 && Math.random() < 0.3){
				obstacleSpawnTime --;
			}
		}
		for (int i = 0 ; i < obstacleList.size() ; i++){
			obstacleList.get(i).y += velYBackground;
		}
		
		
		if (yBackground % 5000 == 0){
			powerUpList.add(new PowerUp(this, yBackground, r.nextInt(3)));
		}
		
		for (int i = 0 ; i < powerUpList.size() ; i++){
			powerUpList.get(i).y += velYBackground;
		}
		
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
	}
	
	public void checkForIntersection(){
		for (int i = 0 ; i < obstacleList.size() ; i ++){
			if (obstacleList.get(i).getRectangle().intersects(player.getRectangle())){
				lives --;
				if (!MainMenuContent.isInstanceOpen() && lives <= 0){
					new MainMenu(yBackground);
					running = false;
					f.dispose();
				}
				obstacleList.remove(i);
			}
		}
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
		g2.drawImage(background, 0, yBackground - (getHeight()*(loopCount -1)), getWidth(), getHeight(), null);
		
		g2.drawImage(background, 0, yBackground - (getHeight()*loopCount), getWidth(), getHeight(), null);
		g2.drawImage(player.getImage(), player.getX(), player.getY(), null);
		
		//g2.drawRect(player.getRectangle().x, player.getRectangle().y, player.getRectangle().width, player.getRectangle().height);
		//g2.drawRect(list.get(0).getRectangle().x, list.get(0).getRectangle().y, list.get(0).getRectangle().width, list.get(0).getRectangle().height);
		
		g2.setFont(new Font("Cambria", Font.BOLD, 20));
		g2.setColor(new Color(0,0,0));
		g2.drawString(String.valueOf(yBackground), 40, 40);
		g2.drawString(String.valueOf(lives + " Life(s)"), getWidth() - g2.getFontMetrics().stringWidth(String.valueOf(lives + " Life(s)")) - 40, 40);
		
		g2.drawString(String.valueOf("Tiny Potion: " + tinyPotionTimer) + " sec", getWidth() - g2.getFontMetrics().stringWidth(String.valueOf("Tiny Potion: " + tinyPotionTimer + " sec")) - 40, 120);
		g2.drawString(String.valueOf("Slow Potion: " + slowPotionTimer) + " sec", getWidth() - g2.getFontMetrics().stringWidth(String.valueOf("Slow Potion: " + slowPotionTimer + " sec")) - 40, 80);
		
		
		if (obstacleList.size() > 0){
			for (int i = 0 ; i < obstacleList.size() ; i++){
				if (obstacleList.get(i).y > getHeight() + 10){
					obstacleList.remove(i);
				}
				obstacleList.get(i).paint(g2);
			}
		}
		
		if (powerUpList.size() > 0){
			for (int i = 0 ; i < powerUpList.size() ; i++){
				if (powerUpList.get(i).y > getHeight() + 10){
					powerUpList.remove(i);
				}
				powerUpList.get(i).paint(g2);
			}
		}
	}
	
	public void keyPressed(KeyEvent k) {
		//if (k.getKeyCode() = KeyEvent.)
		player.keyPressed(k.getKeyCode());
		sound.playSound(0);
	}
	public void keyReleased(KeyEvent k) {
		player.keyReleased(k.getKeyCode());
	}
	public void keyTyped(KeyEvent k) {}
}

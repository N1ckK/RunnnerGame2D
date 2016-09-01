package Main;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private ArrayList<File> soundDB;
	
	public Sound(){
		soundDB = new ArrayList<File>();
		soundDB.add(new File("res/sounds/Bounce.wav"));
	}
	
	public void playSound(int sound){
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundDB.get(sound).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}

}

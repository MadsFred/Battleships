package data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum Sound {
//	HIT("res/audio/hit.wav"),
//	MISS("res/audio/miss.wav"),     // for eclipse project
//	SHOOT("res/audio/shoot.wav");
	
	HIT("audio/hit.wav"),
	MISS("audio/miss.wav"),         // for jar
	SHOOT("audio/shoot.wav");
	
	private Clip clip;

	private Sound(String filePath){
		try {
//			File url = new File(filePath); // for eclipse use
			URL url = this.getClass().getClassLoader().getResource(filePath); // for jar
			
			AudioInputStream in = AudioSystem.getAudioInputStream(url);
			
			clip = AudioSystem.getClip();
			
			clip.open(in);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void play(){
		if(clip.isRunning()){
			clip.stop();
		}
		clip.setFramePosition(0);
		clip.start();
	}
}

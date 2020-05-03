package de.deepchess.util;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioUtil {
	
	public static Clip PIECE_PUT;
	
	public static void load() {
		try {
			
			PIECE_PUT=loadAudioFile("piece_put.wav");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
	private static Clip loadAudioFile(String name) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(AudioUtil.class.getClassLoader().getResource("assets/sounds/"+name));
        clip.open(inputStream);
        return clip;
	}
	
	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}
	
}

package Sound;
import java.io.IOException;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;


public class SoundManager {
	
	public static Audio walk;
	public static Audio pick_flower;
	public static Audio digging;
	public static Audio punch;
	public static Audio squelch;
	public static Audio spider;
	public static Audio stick_crack;
	public static Audio chant;
	
	public static Music ambientMusic;
	
	public static void init(){
		try {
			walk = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/walk.wav"));
			pick_flower = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/pick_flower.ogg"));
			digging = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/digging.ogg"));
			punch = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/punch.ogg"));
			squelch = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/squelch.ogg"));
			spider = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/spider.ogg"));
			stick_crack = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/stick_crack.ogg"));
			chant = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sounds/chant.ogg"));
			ambientMusic = new Music("sounds/music/heart.ogg");
			
		} catch (IOException | SlickException e) {
			e.printStackTrace();
			System.out.println("ERROR: Cannot load audio file - " + e.getMessage());
		}
	}
	
	public static void loopMusic(Music music, float pitch, float volume){
		music.loop(pitch, volume);
	}
	
	public static void stopMusic(Music music){
		music.stop();
	}
	
	public static void playSound(Audio sound, float gain, boolean loop){
		sound.playAsSoundEffect(1, gain, loop);
	}
	
	public static void stopSound(Audio sound){
		sound.stop();
	}
	
	public static boolean isPlaying(Audio sound){
		return sound.isPlaying();
	}
	
}

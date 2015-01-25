import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Sound.SoundManager;


public class LostGame extends StateBasedGame {
	
	protected String name;
	
	public LostGame(String name) {
		super(name);
		this.name = name;
	}
	
	public void draw() {
		System.out.println(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		Play play = new Play();
		GameOver go = new GameOver();
		go.setPlayState(play);
		this.addState(play);
		this.addState(go);
        this.enterState(Play.STATE_PLAY);
        
        SoundManager.init();
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc;
		String name = "definitely not lost";
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("names.txt"));

			String line;
			ArrayList<String> lines = new ArrayList<String>();

			while ((line = reader.readLine()) != null)
				lines.add(line);

			name = lines.get(new Random().nextInt(lines.size()));
		} catch (Exception e) {
		}

		try {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			
			appgc = new AppGameContainer(new LostGame(name));
			appgc.setDisplayMode((int)gd.getDisplayMode().getWidth(), (int)gd.getDisplayMode().getHeight(), true);
			appgc.setTargetFrameRate(gd.getDisplayMode().getRefreshRate());
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}

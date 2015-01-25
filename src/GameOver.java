import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOver extends BasicGameState implements GameState {
	public static final int STATE_OVER = 2;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g)
			throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.black);
		
		g.drawString("Game Over", container.getWidth()/2, container.getHeight()/2);
		g.drawString("press Escape to exit or Enter to start again", container.getWidth()/2, container.getHeight()/2 + 30);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (arg0.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			arg0.exit();
		}
		if (arg0.getInput().isKeyDown(Input.KEY_ENTER)) {
			arg1.enterState(Play.STATE_PLAY);
			arg1.getState(1).init(arg0, arg1);
		}
	}

	@Override
	public int getID() {
		return STATE_OVER;
	}
}

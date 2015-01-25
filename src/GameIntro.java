import Model.AgentState;
import Model.GameSession;
import Player.PlayerUI;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameIntro extends BasicGameState implements GameState {
	public static final int STATE_INTRO = 0;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g)
			throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);

		Image logo = new Image("assets/logo.png");
		g.drawImage(logo,0,0);

		g.drawString("Press Enter to continue", container.getWidth() / 2 - 130, container.getHeight() / 2 + 20);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (arg0.getInput().isKeyDown(Input.KEY_ENTER)) {
			arg1.enterState(Play.STATE_PLAY);
			arg1.getState(1).init(arg0, arg1);
		}

	}

	@Override
	public int getID() {
		return STATE_INTRO;
	}
}

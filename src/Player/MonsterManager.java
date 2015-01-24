package Player;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import Model.Agent;
import TileSystem.TileSystem;

public class MonsterManager {

	Vector<MonsterUI> monsters = new Vector<MonsterUI>();
	Vector<Agent> monsterAgents;
	TileSystem ts;
	
	public MonsterManager(Vector<Agent> monsterAgentsIn, TileSystem tsIn) throws SlickException
	{
		monsterAgents = monsterAgentsIn;
		ts = tsIn;
	}
	
	public void render(Graphics g)
	{
		for (MonsterUI monster : monsters)
		{
			monster.render(g);
		}
		
	}
	
	public void update(float delta) throws SlickException
	{
		if (monsters.size() < 20)
		{
			Random randomGenerator = new Random();
			Agent monsterAgent = monsterAgents.get(randomGenerator.nextInt(monsterAgents.size()));
			monsters.addElement(new MonsterUI(monsterAgent, ts));
		}
		
		for (MonsterUI monster : monsters)
		{
			monster.update(delta);
		}
	}
	
}

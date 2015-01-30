package deserted.model;

import deserted.player.MonsterManager;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;

public interface IActionable {
	void beforeAction(GameSession gs, Agent agent, TileSystem ts, Tile tile);
	void afterAction(GameSession gs, Agent agent, TileSystem ts, Tile tile, MonsterManager monsterManager);
	boolean canPerform(GameSession gs, Agent agent, TileSystem ts, Tile tile);
}

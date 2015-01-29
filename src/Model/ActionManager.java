package Model;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;

import Player.MonsterManager;
import Sound.SoundManager;
import Sprite.SpriteType;
import TileSystem.Tile;
import TileSystem.TileSystem;
import TileSystem.TileSystem.TileId;

public class ActionManager {
	private ArrayList<Action> actions;

	public ActionManager() {
		this.actions = new ArrayList<Action>();

		// These are all hard-coded for now!
		this.actions.add(new Action("Pick Grass", "picking some grass", 6,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.id == TileId.GRASS) {
							SoundManager.playSound("pick_flower");
							gs.addItemByType(ItemType.GRASS);
							ts.setTileID(tile.x, tile.y, TileId.DIRT);
							agent.decFood(5);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.GRASS);
					}

				}));

		this.actions.add(new Action("Eat Fish", "eating a fish", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.FISH);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incFood(30);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.FISH) >= 1);
					}

				}));

		this.actions.add(new Action("Eat Cake", "eating cake", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.SNACK);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incFood(30);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.SNACK) >= 1);
					}

				}));

		this.actions.add(new Action("Eat Berries", "eating berries", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.BERRIES);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incFood(15);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.BERRIES) >= 1);
					}

				}));

		this.actions.add(new Action("Drink Water", "drinking water", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.WATER);
						gs.addItemByType(ItemType.LEAF);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incWater(20);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.WATER) >= 1);
					}

				}));

		this.actions.add(new Action("Drink Seawater", "drinking seawater", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.decWater(10);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.WATER);
					}

				}));

		this.actions.add(new Action("Drink Pondwater", "drinking pondwater", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						agent.incWater(20);
						if (tile.attrHealth > 0) {
							tile.attrHealth -= 2;
							if (tile.attrHealth == 0) {
								ts.setTileID(tile.x, tile.y, TileId.DIRT);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.POND);
					}

				}));

		this.actions.add(new Action("Take Dirt", "digging up dirt", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						SoundManager.playSound("digging");
						gs.addItemByType(ItemType.MUD);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.DIRT);
					}

				}));
		this.actions.add(new Action("Make Mud Brick", "making a mud brick", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.MUD);
						gs.removeItemByType(ItemType.GRASS);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						SoundManager.playSound("squelch");
						gs.addItemByType(ItemType.BRICK);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.MUD) >= 1 && gs
								.getItemCount(ItemType.GRASS) >= 1);
					}

				}));
		this.actions.add(new Action("Build Hut", "building a hut", 60,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.BRICK, 5);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						tile.addSprite(SpriteType.HUT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						if (gs.getItemCount(ItemType.BRICK) >= 5
								&& tile.id != TileId.WATER) {
							return true;
						}
						return false;
					}

				}));

		this.actions.add(new Action("Burn Corpse", "burning a corpse", 15,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.CORPSE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.MEAT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.CORPSE) > 0 && tile
								.hasSprite(SpriteType.FIRE));
					}
				}));

		this.actions.add(new Action("Start Fire", "starting a fire", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.FIRESTICK);
						gs.addItemByType(ItemType.STICK);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						if (tile.hasSprite(SpriteType.CORPSE))
							gs.addItemByType(ItemType.MEAT);
						if (tile.hasSprite(SpriteType.HUT))
							gs.addItemByType(ItemType.MUD);

						tile.clearAllSprites();
						tile.addSprite(SpriteType.FIRE);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						if (gs.getItemCount(ItemType.FIRESTICK) == 0) {
							return false;
						}
						return (tile.id != TileId.SNOW
								&& tile.id != TileId.WATER && tile.id != TileId.WALL);
					}
				}));

		this.actions.add(new Action("Salvage Parts", "salvaging parts", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.WRECKAGE)) {
							gs.addItemByType(ItemType.METAL);
							tile.getSpriteData(SpriteType.WRECKAGE).health -= 5;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.WRECKAGE));
					}
				}));

		this.actions.add(new Action("Light Stick", "lighting a stick", 20,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.STICK);
						gs.removeItemByType(ItemType.ROCK);
						gs.removeItemByType(ItemType.OIL);
						gs.removeItemByType(ItemType.CLOTH);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.FIRESTICK);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.STICK) >= 1
								&& gs.getItemCount(ItemType.ROCK) >= 1
								&& gs.getItemCount(ItemType.CLOTH) >= 1 && gs
								.getItemCount(ItemType.OIL) >= 1);
					}
				}));

		this.actions.add(new Action("Eat Corpsicle", "eating a corpsicle", 20,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.MEAT);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incFood(40);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.MEAT) >= 1);
					}

				}));

		this.actions.add(new Action("Eat Corpse", "eating a corpse", 60,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.CORPSE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						agent.incFood(20);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.CORPSE) >= 1);
					}

				}));

		this.actions.add(new Action("Take Corpse", "taking a corpse", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.CORPSE)) {
							gs.addItemByType(ItemType.CORPSE);
							tile.getSpriteData(SpriteType.CORPSE).health = 0;
							tile.addSprite(SpriteType.SKELETON);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.CORPSE));
					}

				}));

		this.actions.add(new Action("Get Rock", "getting a rock", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.attrHealth > 0) {
							SoundManager.playSound("digging");
							tile.attrHealth -= 5;
							if (tile.attrHealth <= 0) {
								ts.setTileID(tile.x, tile.y, TileId.DIRT);
							}
							gs.addItemByType(ItemType.ROCK);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.ROCK);
					}

				}));

		this.actions.add(new Action("Find Berries", "finding berries", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.SHRUB)) {
							SoundManager.playSound("pick_flower");
							gs.addItemByType(ItemType.BERRIES);

							tile.getSpriteData(SpriteType.SHRUB).health -= 5;
							if (tile.getSpriteData(SpriteType.SHRUB).health == 0) {
								RandomTileObject(TileId.GRASS,
										SpriteType.SHRUB, 1, false, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.SHRUB));
					}

				}));

		this.actions.add(new Action("Make Sail", "making a sail", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.CLOTH, 2);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.SAIL);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.CLOTH) >= 5);
					}

				}));

		this.actions.add(new Action("Make Cloth", "making some cloth", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.WEB, 5);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.CLOTH);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.WEB) >= 5);
					}

				}));

		this.actions.add(new Action("Chop Tree", "chopping a tree", 30,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						if (tile.hasSprite(SpriteType.TREE)) {
							RandomTileObject(TileId.GRASS, SpriteType.TREE, 1,
									true, ts);
							tile.getSpriteData(SpriteType.TREE).health = 0;
							gs.addItemByType(ItemType.PLANK, 5);
						}
						if (tile.hasSprite(SpriteType.PINE_TREE)) {
							RandomTileObject(TileId.ROCK, SpriteType.PINE_TREE,
									1, true, ts);
							tile.getSpriteData(SpriteType.PINE_TREE).health = 0;
							gs.addItemByType(ItemType.PLANK, 5);
						}
						if (tile.hasSprite(SpriteType.PALM_TREE)) {
							RandomTileObject(TileId.DIRT, SpriteType.PALM_TREE,
									1, true, ts);
							tile.getSpriteData(SpriteType.PALM_TREE).health = 0;
							gs.addItemByType(ItemType.PLANK, 5);
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

						return ((tile.hasSprite(SpriteType.PALM_TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE) || tile
								.hasSprite(SpriteType.TREE)) && gs
								.getItemCount(ItemType.AXE) >= 1);
					}

				}));

		this.actions.add(new Action("Find Artifacts", "finding artifacts", 60,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.ALIEN_ARTIFACT)) {
							gs.addItemByType(ItemType.ARTIFACT);
							tile.getSpriteData(SpriteType.ALIEN_ARTIFACT).health = 0;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.ALIEN_ARTIFACT));
					}

				}));
		this.actions.add(new Action("Get Water", "getting water", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.LEAF);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.WATER);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.LEAF) >= 1 && (tile.id == TileId.POND || tile.id == TileId.WATER));
					}

				}));

		this.actions.add(new Action("Get Oil", "getting oil", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.LEAF);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.OIL);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.id == TileId.TARPIT && gs
								.getItemCount(ItemType.LEAF) >= 1);
					}

				}));

		this.actions.add(new Action("Make Axe", "making an axe", 15,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.STICK);
						gs.removeItemByType(ItemType.METAL);
						gs.removeItemByType(ItemType.VINE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.AXE);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.STICK) >= 1
								&& gs.getItemCount(ItemType.METAL) >= 1 && gs
								.getItemCount(ItemType.VINE) >= 1);
					}

				}));

		this.actions.add(new Action("Make Spear", "making a spear", 15,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.STICK);
						gs.removeItemByType(ItemType.ROCK);
						gs.removeItemByType(ItemType.VINE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.SPEAR);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.STICK) >= 1
								&& gs.getItemCount(ItemType.ROCK) >= 1 && gs
								.getItemCount(ItemType.VINE) >= 1);
					}

				}));

		this.actions.add(new Action("Take Stick", "taking a stick", 3,
				new IActionable() {

					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE)
								|| tile.hasSprite(SpriteType.PALM_TREE)) {
							SoundManager.playSound("stick_crack");
							if (Math.random() < 0.9) {
								gs.addItemByType(ItemType.STICK);
							} else {
								gs.addItemByType(ItemType.LEAF);
							}

							int h = 0;
							if (tile.hasSprite(SpriteType.TREE))
								h = tile.getSpriteData(SpriteType.TREE).health--;
							if (tile.hasSprite(SpriteType.PINE_TREE))
								h = tile.getSpriteData(SpriteType.PINE_TREE).health--;
							if (tile.hasSprite(SpriteType.PALM_TREE))
								h = tile.getSpriteData(SpriteType.PALM_TREE).health--;

							if (h == 0) {
								if (tile.hasSprite(SpriteType.TREE))
									RandomTileObject(TileId.GRASS,
											SpriteType.TREE, 1, true, ts);
								if (tile.hasSprite(SpriteType.PINE_TREE))
									RandomTileObject(TileId.ROCK,
											SpriteType.PINE_TREE, 1, true, ts);
								if (tile.hasSprite(SpriteType.PALM_TREE))
									RandomTileObject(TileId.DIRT,
											SpriteType.PALM_TREE, 1, true, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.PALM_TREE)
								|| tile.hasSprite(SpriteType.PINE_TREE) || tile
								.hasSprite(SpriteType.TREE));
					}

				}));

		this.actions.add(new Action("Take Web", "taking some webs", 5,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.WEB);
						tile.getSpriteData(SpriteType.WEBBED_TREE).type = SpriteType.TREE;
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.WEBBED_TREE);
					}

				}));

		this.actions.add(new Action("Take Vine", "taking a vine", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.PALM_TREE)) {
							gs.addItemByType(ItemType.VINE);
							int h = (tile.getSpriteData(SpriteType.PALM_TREE).health -= 5);
							if (h == 0) {
								RandomTileObject(TileId.DIRT,
										SpriteType.PALM_TREE, 1, true, ts);
							}
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.PALM_TREE);
					}

				}));

		this.actions.add(new Action("Catch Fish", "catching a fish", 10,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.FISH);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.id == TileId.WATER
								&& gs.getItemCount(ItemType.SPEAR) >= 1;
					}

				}));

		this.actions.add(new Action("Sacrifice Another", "sacrificing someone",
				120, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.CORPSE);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {

						SoundManager.playSound("chant");
						gs.addItemByType(ItemType.FLIGHT);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.CORPSE) >= 1 && tile
								.hasSprite(SpriteType.ALTAR));
					}

				}));

		this.actions.add(new Action("Sacrifice Yourself",
				"sacrificing themselves", 120, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.addItemByType(ItemType.FLIGHT);
						agent.setState(AgentState.DEAD);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (tile.hasSprite(SpriteType.ALTAR));
					}

				}));

		this.actions.add(new Action("Sleep", "sleeping", 0, new IActionable() {
			@Override
			public void beforeAction(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				agent.setState(AgentState.SLEEPING);

			}

			@Override
			public void afterAction(GameSession gs, Agent agent, TileSystem ts,
					Tile tile, MonsterManager monsterManager) {
			}

			@Override
			public boolean canPerform(GameSession gs, Agent agent,
					TileSystem ts, Tile tile) {
				return tile.hasSprite(SpriteType.HUT);
			}

		}));

		this.actions.add(new Action("Explore Cave", "Exploring Cave", 50,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {

					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						if (tile.hasSprite(SpriteType.CAVE)) {
							Random r = new Random();
							if (r.nextDouble() < 0.7) {
								gs.addItemByType(ItemType.SAIL);
								gs.addItemByType(ItemType.OIL);
								gs.addItemByType(ItemType.WEB);
								gs.addItemByType(ItemType.AXE);
							} else {
								try {
									monsterManager.spawnMassiveMonster(tile.x,
											tile.y - 1);
								} catch (SlickException e) {
								}

							}
							tile.getSpriteData(SpriteType.CAVE).health = 0;
						}
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return tile.hasSprite(SpriteType.CAVE);
					}

				}));

		this.actions.add(new Action("Demo", "running a demo", 3,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.addItemByType(ItemType.VINE, 3);
						gs.addItemByType(ItemType.SAIL, 1);
						gs.addItemByType(ItemType.STICK, 10);
						gs.addItemByType(ItemType.METAL, 10);
						gs.addItemByType(ItemType.MUD, 5);
						gs.addItemByType(ItemType.PLANK, 25);
						gs.addItemByType(ItemType.OIL, 5);
						gs.addItemByType(ItemType.ARTIFACT, 1);
						gs.addItemByType(ItemType.FLIGHT, 1);
						gs.addItemByType(ItemType.CLOTH, 10);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return true;
					}

				}));

		this.actions.add(new Action("Build Raft", "building a raft", 180,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.VINE, 3);
						gs.removeItemByType(ItemType.SAIL, 1);
						gs.removeItemByType(ItemType.STICK, 10);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(1);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.VINE) >= 3
								&& gs.getItemCount(ItemType.SAIL) >= 1
								&& gs.getItemCount(ItemType.STICK) >= 10 && tile.id == TileId.WATER);
					}

				}));

		this.actions.add(new Action("Build Boat", "building a boat", 240,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.VINE, 3);
						gs.removeItemByType(ItemType.SAIL, 1);
						gs.removeItemByType(ItemType.STICK, 10);
						gs.removeItemByType(ItemType.METAL, 10);
						gs.removeItemByType(ItemType.MUD, 5);
						gs.removeItemByType(ItemType.PLANK, 25);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(2);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.VINE) >= 3
								&& gs.getItemCount(ItemType.SAIL) >= 1
								&& gs.getItemCount(ItemType.STICK) >= 10
								&& gs.getItemCount(ItemType.METAL) >= 10
								&& gs.getItemCount(ItemType.MUD) >= 5
								&& gs.getItemCount(ItemType.PLANK) >= 25

						&& tile.id == TileId.WATER);
					}

				}));

		this.actions.add(new Action("Build Plane", "building a plane", 300,
				new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.VINE, 3);
						gs.removeItemByType(ItemType.SAIL, 1);
						gs.removeItemByType(ItemType.STICK, 10);
						gs.removeItemByType(ItemType.METAL, 10);
						gs.removeItemByType(ItemType.MUD, 5);
						gs.removeItemByType(ItemType.PLANK, 25);
						gs.removeItemByType(ItemType.OIL, 5);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(3);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.VINE) >= 3
								&& gs.getItemCount(ItemType.SAIL) >= 1
								&& gs.getItemCount(ItemType.STICK) >= 10
								&& gs.getItemCount(ItemType.METAL) >= 10
								&& gs.getItemCount(ItemType.MUD) >= 5
								&& gs.getItemCount(ItemType.PLANK) >= 25
								&& gs.getItemCount(ItemType.OIL) >= 5
								&& gs.getItemCount(ItemType.FLIGHT) >= 1

						&& tile.id == TileId.SNOW);
					}

				}));

		this.actions.add(new Action("Build Spacecraft",
				"building a spacecraft", 360, new IActionable() {
					@Override
					public void beforeAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						gs.removeItemByType(ItemType.VINE, 3);
						gs.removeItemByType(ItemType.SAIL, 1);
						gs.removeItemByType(ItemType.STICK, 10);
						gs.removeItemByType(ItemType.METAL, 10);
						gs.removeItemByType(ItemType.MUD, 5);
						gs.removeItemByType(ItemType.PLANK, 25);
						gs.removeItemByType(ItemType.OIL, 5);
						gs.removeItemByType(ItemType.ARTIFACT, 1);
					}

					@Override
					public void afterAction(GameSession gs, Agent agent,
							TileSystem ts, Tile tile,
							MonsterManager monsterManager) {
						gs.setCompletionType(4);
						gs.setCompleted(true);
					}

					@Override
					public boolean canPerform(GameSession gs, Agent agent,
							TileSystem ts, Tile tile) {
						return (gs.getItemCount(ItemType.VINE) >= 3
								&& gs.getItemCount(ItemType.SAIL) >= 1
								&& gs.getItemCount(ItemType.STICK) >= 10
								&& gs.getItemCount(ItemType.METAL) >= 10
								&& gs.getItemCount(ItemType.MUD) >= 5
								&& gs.getItemCount(ItemType.PLANK) >= 25
								&& gs.getItemCount(ItemType.OIL) >= 5
								&& gs.getItemCount(ItemType.ARTIFACT) >= 1
								&& gs.getItemCount(ItemType.FLIGHT) >= 1

						&& tile.id == TileId.SNOW);
					}

				}));
	}

	public ArrayList<Action> getValidActions(GameSession gs, TileSystem ts,
			Tile tile, Agent agent) {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.getActionable().canPerform(gs, agent, ts, tile)) {
				validActions.add(action);
			}
		}
		return validActions;
	}

	private void RandomTileObject(TileId tileType, SpriteType spriteType,
			int treeCount, boolean preferGroupings, TileSystem ts) {
		Random randomGenerator = new Random();
		while (true) {
			int x = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			int y = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			Tile tile = ts.getTile(x, y);
			if (tile.id == tileType && tile.numSprites() == 0
					&& tile.variant == 0) {
				float surroundTree = 1;
				if (ts.getTile(x + 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x - 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y + 1).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y - 1).hasSprite(spriteType))
					surroundTree++;
				float num = (float) randomGenerator.nextInt(100);
				if (preferGroupings)
					num /= surroundTree;
				else
					num /= 1.25;
				num *= surroundTree;

				if (num > 50) {
					treeCount -= 1;
					tile.addSprite(spriteType);
				}
			}

			if (treeCount == 0)
				return;
		}

	}
}

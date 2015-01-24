package TileSystem;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import Sprite.GroundSprite;

public class TileSystem {
	
	public Camera camera;
	public float zoomLevel = 1;
	public int tileRes = 32;
	public int size;
	
	private Image tileMap;
	
	private final Color semi = new Color(0, 0, 0, 0.5f);
	
	public enum TileId{
		GRASS,
		DIRT,
		WATER
	}

	private Tile tiles[][];
	
	public TileSystem(int size){
		this.size = size;
		tiles = new Tile[size][size];
		camera = new Camera(0, 0);
		
		setTileMap("dg_edging132.gif");
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	tiles[x][y] = new Tile(TileId.GRASS, x, y, 1);
            }
		}
		tiles[5][5] = new Tile(TileId.GRASS, 5, 5, 0);
		tiles[6][5] = new Tile(TileId.GRASS, 6, 5, 2);
		tiles[7][5] = new Tile(TileId.GRASS, 7, 5, 2);
		tiles[8][5] = new Tile(TileId.GRASS, 8, 5, 2);
		tiles[9][5] = new Tile(TileId.GRASS, 9, 5, 2);
		tiles[10][5] = new Tile(TileId.GRASS, 10, 5, 2);
		tiles[7][6] = new Tile(TileId.WATER, 7, 5, 2);
		tiles[8][6] = new Tile(TileId.WATER, 8, 5, 2);
		tiles[9][6] = new Tile(TileId.WATER, 9, 5, 2);
		tiles[10][6] = new Tile(TileId.WATER, 10, 6, 2);
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	getTile(x, y).x = x;
             	getTile(x, y).y = y;
            }
		}
		
		//Change variants
		//GroundSprite.setVariants(tiles);
	}
	
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public Tile getTileFromScreen(int x, int y){
		Vector2f world = screenToWorldPos(x, y);
		return getTileFromWorld(world.getX(), world.getY());
	}
	
	public int getSize(){
		return size;
	}
	
	public Tile getTileFromWorld(float x, float y){
		if(x > size || x < 0 || y > size || y < 0)
			return null;
		return tiles[(int)x][(int)y];
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		float resTimesScale = tileRes * zoomLevel;
		return new Vector2f(((camera.x+scX)/resTimesScale), ((camera.y+scY)/resTimesScale));
	}
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		float resTimesScale = tileRes * zoomLevel;
		return new Vector2f((worldX *resTimesScale)-camera.x, (worldY *resTimesScale)-camera.y);
	}
	
	public void setZoom(float zoomLevel, Point windowSize){
		float zoomChange = zoomLevel - this.zoomLevel;
		float newZoom = zoomLevel;
		if (newZoom >= 2)
			newZoom = 2;
		if (newZoom <= 0.5f)
			newZoom = 0.5f;
		if(newZoom != this.zoomLevel){
			this.zoomLevel = newZoom;
			camera.x += ((tileRes*zoomChange)*(windowSize.getX()/(tileRes*newZoom)))/2;
			camera.y += ((tileRes*zoomChange)*(windowSize.getY()/(tileRes*newZoom)))/2;
		}
	}
	
	public void render(Graphics g){
		renderTiles(g);
		renderFog(g);
	}
	
	private void renderTiles(Graphics g){
		float resTimesScale = tileRes * zoomLevel;
		float finalX, finalY;
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)-camera.x;
            	finalY = (y*resTimesScale)-camera.y;
            	Point src = GroundSprite.getSprite(tiles[x][y].id, tiles[x][y].touching, tiles[x][y].variant);
            	g.drawImage(tileMap, finalX, finalY, finalX+resTimesScale, finalY+resTimesScale, src.getX(), src.getY(), src.getX()+tileRes, src.getY()+tileRes);
            }
        }
	}
	
	private void renderFog(Graphics g){
		float resTimesScale = tileRes * zoomLevel;
		float finalX, finalY;
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)-camera.x;
            	finalY = (y*resTimesScale)-camera.y;
            	switch(tiles[x][y].vis){
	            	case 0:
	            		g.setColor(Color.black);
	            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
	            		break;
	            	case 1:
	            		g.setColor(semi);
	            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
	            		break;
            	}
            }
		}
	}
	
	public void updateFog(Vector2f pos){
		int xp = (int)pos.x;
		int yp = (int)pos.y;
		for(int x = xp - 4; x < xp + 4; x++){
			for(int y = yp - 4; y < yp + 4; y++){
				
			}
		}
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public void setTileRes(int res){
		tileRes = res;
	}
	
	public void setTileMap(String fileName){
		try {
			tileMap = new Image("tiles/"+fileName);
			tileMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}

}

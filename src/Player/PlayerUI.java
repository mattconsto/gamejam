package Player;
import java.util.Enumeration;
import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.lwjgl.util.vector.Vector2f;

import Model.Agent;
import TileSystem.TileSystem;
import TileSystem.TileSystem.Tile;
import TileSystem.TileSystem.TileId;


public class PlayerUI {

	TileSystem ts;
	PathFinder p;
	public Agent agent;
	
	public Vector2f location = new Vector2f(50,50);
	Vector2f destination = new Vector2f(20,20);
	Vector<Tile> destinations = new Vector<Tile>();
	boolean atDestination = true;
	
	float playerWalkSpeedMS = 1.4f;		//average walk speed 1.4m per second
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	
	public PlayerUI(Agent agentIn, TileSystem tsIn){
		agent = agentIn;
		ts = tsIn;
		p = new PathFinder(ts);
	}
	
	public void moveto(float destinationX, float destinationY){
		atDestination = false;
		p = new PathFinder(ts);
		destination = new Vector2f(destinationX, destinationY);
		destinations = p.findPath(location, destination);
	}
	
	public void render(Graphics g){
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		g.setColor(new Color(255,0,0));
		g.fillOval(screenLocation.x-5,screenLocation.y-5, 10, 10);
		
		//Vector2f screenLocationDest = ts.worldToScreenPos(destination.x, destination.y);
		//g.setColor(new Color(0,255,0));
		//g.drawLine(screenLocation.x, screenLocation.y, screenLocationDest.x, screenLocationDest.y);
		
		g.setColor(new Color(0,0,255));
		Vector2f lastPoint = ts.worldToScreenPos(location.x, location.y);
		for(int i =destinations.size()-1; i>=0 ; i--)
		{
			Tile dest = destinations.get(i);
			Vector2f pos = new Vector2f(dest.x+0.5f, dest.y+0.5f);
            Vector2f worldPos = ts.worldToScreenPos(pos.x, pos.y);
            g.drawLine(worldPos.x, worldPos.y, lastPoint.x, lastPoint.y);
            lastPoint = worldPos;
		}
	}
	
	public void update(float deltaTime) {
		if (atDestination) return;
		
		
		//Some basic movement code - a bit elaborate tbh
		float deltaTimeS = (float)deltaTime;
		float distanceTravelled = (deltaTimeS * gameSpeed * playerWalkSpeedMS)/ tileSizeM ;
		
		Vector2f currentDestination = destination;
		Tile destTile = null;
		if (destinations.size() > 1)
		{
		    destTile = destinations.get(destinations.size()-1);
			currentDestination = new Vector2f(destTile.x+0.5f, destTile.y+0.5f);
		}
		
		//Move the player
		Vector2f directionVec = new Vector2f(currentDestination.x - location.x, currentDestination.y-location.y);
		float vecLength = (float)Math.sqrt((directionVec.x * directionVec.x) + (directionVec.y * directionVec.y));
		if (vecLength > distanceTravelled)
		{
			Vector2f directionVecUnit = new Vector2f(directionVec.x / vecLength, directionVec.y / vecLength);
			Vector2f directionInStep = new Vector2f(directionVecUnit.x * distanceTravelled, directionVecUnit.y * distanceTravelled);
		
			Vector2f nextLocation = new Vector2f(location.x + directionInStep.x, location.y+directionInStep.y);
			location = nextLocation;
		}
		else
		{
			location = currentDestination;
			if (destTile != null)
			{
				destinations.removeElement(destTile);
			}
			else
			{
				//If the last step then just set the location and alert listeners
				location = destination;
				atDestination = true;
				firePlayerReachedDestinationEvent();
			}
		
		}
		
		
	}
	
    protected Vector<PlayerReachedDestinationEvent> _listeners;
     
    public void addReachedDestinationEvent(PlayerReachedDestinationEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<PlayerReachedDestinationEvent>();
             
        _listeners.addElement(listener);
    }
    
    protected void firePlayerReachedDestinationEvent()
    {
        if (_listeners != null && !_listeners.isEmpty())
        {
            Enumeration<PlayerReachedDestinationEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	PlayerReachedDestinationEvent ev = e.nextElement();
                ev.reachedDestination(this, destination.x, destination.y);;
            }
        }
    }
     



    public class PathFinder
	{
    	public int distances[][];
    	private int size;
    	
    	public PathFinder(TileSystem ts)
    	{
    		size = ts.size;
    		distances = new int[size][size];
    		
    		for(int x = 0; x < size; x++){
                for(int y = 0; y < size; y++){
                	distances[x][y] = 9999;
                }
    		}
    		
    	}
    
    	public Vector<Tile> findPath(Vector2f start, Vector2f end)
    	{
    		Tile startTile = ts.getTileFromWorld(start.x,start.y);
    		Tile endTile = ts.getTileFromWorld(end.x, end.y);
   
    		if (startTile == null)
    			throw new IllegalArgumentException("Start tile is nothing !!!");
    		
    		if (endTile == null)
    			throw new IllegalArgumentException("End tile is nothing !!!");
    		
    		if (startTile.x == endTile.x && startTile.y == endTile.y) return new Vector<Tile>();
    		
    		setDistances(startTile, 0);
    		
    		Vector<Tile> tiles = getPath(startTile, endTile);
    		
    		return tiles;
    	}
    	
    	private Vector<Tile> getPath(Tile startTile, Tile endTile)
    	{
    		Vector<Tile> tiles = new Vector<Tile>();
    		
    		Tile currentTile = endTile;
    		do
    		{
    			tiles.add(currentTile);
    		    currentTile = getNextTile(currentTile);
    		   // System.out.println(currentTile.x + ", " + currentTile.y + " - " + distances[currentTile.x][currentTile.y]);
    		    if (currentTile == startTile) return tiles;
    		}while (true);
    	}

    	private Tile getNextTile(Tile currentTile)
    	{
    		int min = 9999;
    		Tile minTile = null;
    		if (currentTile.x >0)
    		{
    			Tile tile = ts.getTile(currentTile.x-1, currentTile.y);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.y >0)
    		{
    			Tile tile = ts.getTile(currentTile.x, currentTile.y-1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x >0 && currentTile.y >0)
    		{
    			Tile tile = ts.getTile(currentTile.x-1, currentTile.y-1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x < size-2)
    		{
    			Tile tile = ts.getTile(currentTile.x+1, currentTile.y);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.y < size-2)
    		{
    			Tile tile = ts.getTile(currentTile.x, currentTile.y+1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x < size-2 && currentTile.y < size-2)
    		{
    			Tile tile = ts.getTile(currentTile.x+1, currentTile.y+1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x >0 && currentTile.y < size-2)
    		{
    			Tile tile = ts.getTile(currentTile.x-1, currentTile.y+1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		if (currentTile.x < size-2 && currentTile.y >0)
    		{
    			Tile tile = ts.getTile(currentTile.x+1, currentTile.y-1);
    			int dist = distances[tile.x][tile.y];
    			if (dist < min)
    			{
    				 min=dist;
    				minTile = tile;
    			}
    		}
    		return minTile;
    	}
    	
    	private void setDistances(Tile startTile, int distance)
    	{
    		distances[startTile.x][startTile.y] = distance;
    		if (startTile.x >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x-1, startTile.y);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		if (startTile.y >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x, startTile.y-1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		if (startTile.x >0 && startTile.y >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x-1, startTile.y-1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		if (startTile.x < size-2)
    		{
    			Tile currentDest = ts.getTile(startTile.x+1, startTile.y);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		if (startTile.y < size-2)
    		{
    			Tile currentDest = ts.getTile(startTile.x, startTile.y+1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		if (startTile.x < size-2 && startTile.y < size-2)
    		{
    			Tile currentDest = ts.getTile(startTile.x+1, startTile.y+1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		
    		if (startTile.x >0  && startTile.y < size-2)
    		{
    			Tile currentDest = ts.getTile(startTile.x-1, startTile.y+1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    		
    		if (startTile.x < size-2 && startTile.y >0)
    		{
    			Tile currentDest = ts.getTile(startTile.x+1, startTile.y-1);
    			int moveValue = getTileMoveAbility(currentDest);
    			int currentValue = distance + moveValue;
    			if (currentValue < distances[currentDest.x][currentDest.y])
    				setDistances(currentDest, currentValue);
    		}
    	}
    	
    	private int getTileMoveAbility(Tile tileIn)
    	{
    		//This is how much is added to the distance variable per tile move bigger = slower
    		//negative = er no cannot do it
    		if (tileIn.id == TileId.WATER) return 9999;
    		if (tileIn.id == TileId.GRASS) return 99;
    		if (tileIn.id == TileId.DIRT) return 99;
			return 9999;
    	}
    	
	}

}

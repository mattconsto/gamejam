package Map;

import TileSystem.Tile;
import TileSystem.TileSystem;

import java.io.*;

/**
 * Created by andy on 24/01/15.
 */
public class SimpleMapLoader {
    private static final String filePath = "/maps/2.txt";

    public Tile[][] loadMap() throws FileNotFoundException {
        Tile[][] tiles = null;
        BufferedReader reader = null;
        try {
            InputStream in = getClass().getResourceAsStream(filePath); 
            reader = new BufferedReader(new InputStreamReader(in));

            // First line is the size (Assuming it's a square)
            int size = Integer.parseInt(reader.readLine());
            if (size < 1) {
                throw new Exception("Map has invalid grid size!");
            }

            tiles = new Tile[size][size];

            // Iterate through the file to set the map up.
            for (int x = 0; x < size; x++) {
                String line = reader.readLine();
                for (int y = 0; y < line.length(); y++) {
                    tiles[x][y] = createTile(line.charAt(y), x, y);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                //ohgodwhathashappened?
            }
        }
        return tiles;
    }

    private Tile createTile(char mapChar, int x, int y) {
        switch(mapChar) {
            case '@':
                return new Tile(TileSystem.TileId.WATER,x,y,0);
            case '+':
            return new Tile(TileSystem.TileId.DIRT,x,y,0);
            case '\'':
                return new Tile(TileSystem.TileId.GRASS,x,y,0);
            case ':':
            	return new Tile(TileSystem.TileId.ROCK,x,y,0);
            case ' ':
            	return new Tile(TileSystem.TileId.SNOW,x,y,0);
            default:
                return new Tile(TileSystem.TileId.DIRT,x,y,0);
        }
    }

}

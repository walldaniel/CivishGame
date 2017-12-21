package com.wall.civilization.map;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Map {

	private Random rand;

	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	
	private MapLayers layers;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer selectionLayer;
	private TiledMapTileLayer unitLayer;

	private Texture tileMap;
	private TiledMapTileSet tileset;
	private TextureRegion[] tiles;

	private int highlightedX = 0, highLightedY = 0;
	private Texture selectionTile;
	private int[][] numberLayer;

	public Map(int mapSize, OrthographicCamera cam) {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		// Create tile objects
		tileMap = new Texture(Tile.PLAINS_PATH);
		tiles = new TextureRegion[4];
		tiles[0] = new TextureRegion(tileMap, 0, 0, 128, 128);
		tiles[1] = new TextureRegion(tileMap, 0, 128, 128, 128);
		tiles[2] = new TextureRegion(tileMap, 128, 0, 128, 128);
		tiles[3] = new TextureRegion(tileMap, 128, 128, 128, 128);

		// Flip all the tiles so that they drawn properly
		for (TextureRegion t : tiles) {
			t.flip(false, true);
		}

		tileset = new TiledMapTileSet();
		for (int i = 0; i < tiles.length; i++)
			tileset.putTile(i, new StaticTiledMapTile(tiles[i]));

		tiledMap = new TiledMap();
		layers = tiledMap.getLayers();
		groundLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);
		numberLayer = new int[groundLayer.getHeight()][groundLayer.getWidth()]; // Used to remember which tiles are where more easily
		
		// Generate the tiles on the map
		generateMap();

		// Selection layer stuff
		selectionLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);
		layers.add(selectionLayer);
		selectionTile = new Texture("selection_tile.png");

		// Unit layer stuff, make sure it is in correct order
		unitLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);
		layers.add(unitLayer);
		
		// Set camera to tile map renderer
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
		renderer.setView(cam);
	}

	// Randomly chooses a tile based on what is above and to left
	private int randomTileBoth(int up, int left) {
		double[] tiles = new double[tileset.size() - 1];

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = rand.nextFloat() * 1.5f;
		}

		// Make connecting tiles more likely to be the same
		tiles[up] += rand.nextFloat() * 1.6f;
		tiles[left] += rand.nextFloat() * 1.6f;

		// Find which tiles has been randomly chosen
		int greatest = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] > tiles[greatest]) {
				greatest = i;
			}
		}

		return greatest;
	}

	// Returns random tile based on only one close tile
	private int randomTileOne(int n) {
		double[] tiles = new double[tileset.size() - 1];

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = rand.nextFloat();
		}

		// Make connecting tiles more likely to be the same
		tiles[n] += rand.nextFloat();

		// Find which tiles has been randomly chosen
		int greatest = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] > tiles[greatest]) {
				greatest = i;
			}
		}

		return greatest;
	}

	private void generateMap() {
		// Check to make sure map has been initialized
		if (tiledMap == null)
			return;

		// Create tiles on first layer
		Cell[] cells = new Cell[tileset.size()];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell();
			cells[i].setTile(tileset.getTile(i));
		}
		for (int i = 0; i < groundLayer.getHeight(); i++) {
			for (int j = 0; j < groundLayer.getWidth(); j++) {
				// If not on edges, then have some sort of generation
				if (i != 0 && j != 0) {
					// Randomly generate map based on what is up and to the left
					numberLayer[i][j] = randomTileBoth(numberLayer[i - 1][j], numberLayer[i][j - 1]);
					;
				} else if (i != 0) {
					numberLayer[i][j] = randomTileOne(numberLayer[i - 1][j]);
				} else if (j != 0) {
					numberLayer[i][j] = randomTileOne(numberLayer[i][j - 1]);
				} else {
					numberLayer[i][j] = rand.nextInt(tileset.size() - 1);
				}

			}
		}

		// Add oceans
		addOceans();

		// Add the cells to the ground layer
		for (int i = 0; i < groundLayer.getHeight(); i++) {
			for (int j = 0; j < groundLayer.getWidth(); j++) {
				groundLayer.setCell(i, j, cells[numberLayer[i][j]]);
			}
		}

		// Add the layer to the map
		layers.add(groundLayer);
	}

	private void addOceans() {
		// Between 1 and 5 oceans spawn
		for (int i = rand.nextInt(4) + 1; i > 0; i--) {
			oceanFill(rand.nextInt(groundLayer.getWidth()), rand.nextInt(groundLayer.getHeight()), 0f);
		}
	}

	private void oceanFill(int x, int y, double chance) {
		// First check to see if already ocean tile, then check if on edges,
		// Finally check if the chance passes
		if (numberLayer[x][y] != tileset.size() - 1 && x > 0 && y > 0 && x < numberLayer.length - 1
				&& y < numberLayer[0].length - 1 && rand.nextFloat() > chance) {
			numberLayer[x][y] = 3;

			oceanFill(x + 1, y, chance + Math.random() / 6f);
			oceanFill(x - 1, y, chance + Math.random() / 6f);
			oceanFill(x, y + 1, chance + Math.random() / 6f);
			oceanFill(x, y - 1, chance + Math.random() / 6f);

		}
	}

	public void changeUnitLocation(Texture texture, int oldX, int oldY, int newX, int newY) {
		unitLayer.setCell(oldX, oldY, null);
		Cell cell = new Cell();
		cell.setTile(new StaticTiledMapTile(new TextureRegion(texture)));
		unitLayer.setCell(newX, newY, cell);
	}
	
	// Draw the map to the camera/display
	public void drawMap(OrthographicCamera cam) {
		renderer.setView(cam);
		renderer.render();
	}

	// Set the selection layer tile above selected to one with high alpha nd white
	public void highlightSquare(int x, int y) {
		// First set old highlighted one back to nothing
		selectionLayer.setCell(highlightedX, highLightedY, null);

		// Set tile in selection layer to indicate which tile is selected
		Cell cell = new Cell();
		// Create tile with high alpha
		cell.setTile(new StaticTiledMapTile(new TextureRegion(selectionTile)));
		// Add to layer
		selectionLayer.setCell(x, y, cell);

		// Save the old x and y
		highlightedX = x;
		highLightedY = y;
	}
	
	// Check to see if unit is already there, probably more efficient this way
	public boolean checkLocation(int x, int y, Cell cell) {
		if(unitLayer.getCell(x, y).equals(cell)) {
			return true;
		}
		return false;
	}
	
	// Returns true if no unit is on location
	public boolean checkLocation(int x, int y) {
		if(unitLayer.getCell(x, y) == null)
			return true;
		
		return false;
	}
	
	// Removes the unit on a specific tile
	public void removeUnitLocation(int x, int y) {
		unitLayer.setCell(x, y, null);
	}
	
	// Draw a unit somewhere, if a possible unit is already there then don't
	public boolean setUnitLocation(int x, int y, Cell cell) {
		if(unitLayer.getCell(x, y) == null) {
			unitLayer.setCell(x, y, cell);
			return true;
		}
		
		return false;
	}
}

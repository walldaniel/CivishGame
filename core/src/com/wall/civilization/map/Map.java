package com.wall.civilization.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Map {

	private TiledMap tiledMap;
	private MapLayers layers;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileLayer selectionLayer;
	private TiledMapTileSet tileset;

	private OrthogonalTiledMapRenderer renderer;

	private Texture tileMap;
	private TextureRegion[] tiles;

	private int highlightedX = 0, highLightedY = 0;
	private Texture selectionTile;

	public Map(int mapSize, OrthographicCamera cam) {
		// Create tile objects
		tileMap = new Texture(Tile.PLAINS_PATH);
		tiles = new TextureRegion[2];
		tiles[0] = new TextureRegion(tileMap, 0, 0, 128, 128);
		tiles[1] = new TextureRegion(tileMap, 0, 128, 128, 128);

		tileset = new TiledMapTileSet();
		for (int i = 0; i < tiles.length; i++)
			tileset.putTile(i, new StaticTiledMapTile(tiles[i]));

		tiledMap = new TiledMap();
		layers = tiledMap.getLayers();
		groundLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);

		// Generate the tiles on the map
		generateMap();

		// Selection layer stuff
		selectionLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);
		layers.add(selectionLayer);
		selectionTile = new Texture("selection_tile.png");
		
		// Set camera to tile map renderer
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
		renderer.setView(cam);
	}

	private void generateMap() {
		// Check to make sure map has been initialized
		if (tiledMap == null)
			return;

		// Create tiles on first layer
		Cell plains = new Cell();
		plains.setTile(tileset.getTile(0));
		Cell water = new Cell();
		water.setTile(tileset.getTile(1));

		for (int i = 0; i < groundLayer.getWidth(); i++) {
			for (int j = 0; j < groundLayer.getHeight(); j++) {
				if (Math.random() < 0.5f)
					groundLayer.setCell(i, j, plains);
				else
					groundLayer.setCell(i, j, water);
			}
		}

		layers.add(groundLayer);
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
}

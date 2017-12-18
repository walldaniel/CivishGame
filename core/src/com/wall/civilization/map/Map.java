package com.wall.civilization.map;

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

	private TiledMap tiledMap;
	private MapLayers layers;
	private TiledMapTileLayer groundLayer;
	private TiledMapTileSet tileset;
	
	private OrthogonalTiledMapRenderer renderer;
	
	private Texture tileMap;
	private TextureRegion[] tiles;
	
	public Map(int mapSize, OrthographicCamera cam) {
		double time = System.currentTimeMillis();
		
		// Create tile objects
		tileMap = new Texture(Tile.PLAINS_PATH);
		tiles = new TextureRegion[2];
		tiles[0] = new TextureRegion(tileMap, 0, 0, 128, 128);
		tiles[1] = new TextureRegion(tileMap, 0, 128, 128, 128);
		
		tileset = new TiledMapTileSet();
		for(int i = 0; i < tiles.length; i++)
			tileset.putTile(i, new StaticTiledMapTile(tiles[i]));
		
		tiledMap = new TiledMap();
		layers = tiledMap.getLayers();
		groundLayer = new TiledMapTileLayer(mapSize, mapSize, 128, 128);
		
		// Generate the tiles on the map
		generateMap();
		
		// Set camera to tile map renderer
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
		renderer.setView(cam);
		
		System.out.println(System.currentTimeMillis() - time);
	}

	private void generateMap() {
		// Check to make sure map has been initialized
		if(tiledMap == null)
			return;
		
		// Create tiles on first layer
		Cell plains = new Cell();
		plains.setTile(tileset.getTile(0));
		Cell water = new Cell();
		water.setTile(tileset.getTile(1));
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if(Math.random() < 0.5f)
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
	
}

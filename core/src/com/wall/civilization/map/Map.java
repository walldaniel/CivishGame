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
	private TiledMapTileSet tileset;

	private OrthogonalTiledMapRenderer renderer;

	private Texture tileMap;
	private TextureRegion[] tiles;

	private HighlightedTile highlightedTile;

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

		// Set camera to tile map renderer
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
		renderer.setView(cam);

		// Create the Highlighted tile
		highlightedTile = new HighlightedTile(-1, -1, null);
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

	public void highlightSquare(int x, int y) {
		// Set the old tile back to normal, check if it has been made yet
		if (highlightedTile.x != -1 || highlightedTile.y != -1 || highlightedTile.cell != null)
			groundLayer.setCell(highlightedTile.x, highlightedTile.y, highlightedTile.cell);

		// Set the new new tile to what it is, and the highlighted tile to stuff
		highlightedTile.cell = (Cell) copy(groundLayer.getCell(x, y));
		highlightedTile.x = x;
		highlightedTile.y = y;

		Pixmap pixmap = highlightedTile.cell.getTile().getTextureRegion().getTexture().getTextureData().consumePixmap();
		int colorAffinity = 1;
		int colorBlade = 1;
		int colorEdge = 1;
		int colorGrip = 1;

		for (int i = 0; i < pixmap.getHeight(); i++) {
			for (int j = 0; j < pixmap.getWidth(); j++) {
				Color color = new Color();
				Color.rgba8888ToColor(color, pixmap.getPixel(x, y));
				
				color.set(color.r, color.g, color.b, 0.5f);
				
				pixmap.setColor(color);
				pixmap.fillRectangle(i, j, 1, 1);
			}
		}

		 groundLayer.setCell(x, y, highlightedTile.cell.getTile());
	}

	public Object copy(Object obj) {
		return obj;
	}

	private class HighlightedTile {
		public int x, y;
		public Cell cell;

		public HighlightedTile(int x, int y, Cell cell) {
			this.x = x;
			this.y = y;
			this.cell = cell;
		}
	}
}

package com.wall.civilization.map;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.map.Tile.TileType;

public class Map {

	private Tile[][] tiles;

	private Texture plains, hills, ocean;
	private Random rand;

	public Map(int x, int y) {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		tiles = new Tile[x][y];
		
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = new Tile(rand);
			}
		}
		
		plains = new Texture(Tile.PLAINS_PATH);
		hills = new Texture(Tile.HILLS_PATH);
		ocean = new Texture(Tile.OCEAN_PATH);
	}

	public void drawMap(SpriteBatch sb, float posX, float posY) {
		sb.begin();
		
		float currentX = posX, currentY = posY;
		for(Tile[] row : tiles) {
			for(Tile t : row) {
				
				switch(t.getTile()) {
				case PLAINS:
					sb.draw(plains, currentX, currentY, 64, 64);
					break;
				case HILLS:
					sb.draw(hills, currentX, currentY, 64, 64);
					break;
				case OCEAN:
					sb.draw(ocean, currentX, currentY, 64, 64);
					break;
				}
				currentX += Tile.WIDTH;
				
			}
			currentY += Tile.HEIGHT;
			currentX = posX;
		}
		
		sb.end();
	}

}

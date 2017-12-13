package com.wall.civilization.map;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.Civ;
import com.wall.civilization.map.Tile.TileType;

public class Map {

	public enum Orientation { NORMAL, REVERSED };
	
	private Tile[][] tiles;
	private Orientation[][] orientations;

	private Texture plains, hills, desert, oceanOne, oceanTwo;
	private Random rand;
	
	private boolean AnimatedTile = false;
	private float timeSinceLasteChange; // Changes which tile for ocean is displayed

	public Map(int x, int y) {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		tiles = new Tile[x][y];

		// Generate the hills and plains
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if (i != 0 && j != 0) {
					tiles[i][j] = new Tile(rand, tiles[i - 1][j].getTile(), tiles[i][j - 1].getTile());
				} else {
					tiles[i][j] = new Tile(rand);
				}
			}
		}
		// Generate the oceans
		addOceans(8);
		
		// Add orientation of tiles
		orientations = new Orientation[x][y];
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[i].length; j++) {
				orientations[i][j] = Orientation.values()[rand.nextInt(Orientation.values().length)];
			}
		}

		plains = new Texture(Tile.PLAINS_PATH);
		hills = new Texture(Tile.HILLS_PATH);
		desert = new Texture(Tile.DESERT_PATH);
		oceanOne = new Texture(Tile.OCEAN_ONE_PATH);
		oceanTwo = new Texture(Tile.OCEAN_TWO_PATH);
	}

	// Generates up to inputted number of oceans
	private void addOceans(int maxOceans) {
		for (int i = rand.nextInt(maxOceans) + 1; i >= 0; i--) {
			oceanFill(rand.nextInt(tiles.length), rand.nextInt(tiles[0].length), 0f);
		}
	}

	private void oceanFill(int x, int y, double chance) {

		if (x > 0 && y > 0 && x < tiles.length - 1 && y < tiles[0].length - 1) {
			// If already ocean then ignore
			if (tiles[x][y].getTile() != TileType.OCEAN && rand.nextFloat() > chance) {
				tiles[x][y].setTile(TileType.OCEAN);

				oceanFill(x + 1, y, chance + Math.random() / 4f);
				oceanFill(x - 1, y, chance + Math.random() / 6f);
				oceanFill(x, y + 1, chance + Math.random() / 4f);
				oceanFill(x, y - 1, chance + Math.random() / 5f);
			}
		}
	}

	public void drawMap(SpriteBatch sb, float posX, float posY, float dt) {
		float currentX = posX, currentY = posY;
		
		System.out.println("pos x - " + posX);
		System.out.println("pos y - " + posY);
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				// TODO:  Make sure that the tile is actually being shown
				if (true) {

					switch (tiles[i][j].getTile()) {
					case PLAINS:
						switch(orientations[i][j]) {
						case NORMAL:
							sb.draw(plains, currentX, currentY, plains.getWidth(), plains.getHeight(), 
									0, 0, plains.getWidth(), plains.getHeight(), false, false);
							break;
						case REVERSED:
							sb.draw(plains, currentX, currentY, plains.getWidth(), plains.getHeight(), 
									0, 0, plains.getWidth(), plains.getHeight(), true, false);
							break;
						}
						break;
					case HILLS:
						sb.draw(hills, currentX, currentY, plains.getWidth(), plains.getHeight(), 
								0, 0, hills.getWidth(), hills.getHeight(), false, false);
						break;
					case DESERT:
						switch(orientations[i][j]) {
						case NORMAL:
							sb.draw(desert, currentX, currentY, plains.getWidth(), plains.getHeight(), 
									0, 0, desert.getWidth(), desert.getHeight(), false, false);
							break;
						case REVERSED:
							sb.draw(desert, currentX, currentY, plains.getWidth(), plains.getHeight(), 
									0, 0, desert.getWidth(), desert.getHeight(), true, false);
							break;
						}
						break;
					case OCEAN:
						if(AnimatedTile)
							sb.draw(oceanOne, currentX, currentY);
						else 
							sb.draw(oceanTwo, currentX, currentY);
						break;
					}
					
//					System.out.println(currentX);
					currentX += plains.getWidth();
				}
			}
			currentY += plains.getHeight();
			currentX = posX;
		}

		// Used to determine the change in wave patterns
		timeSinceLasteChange += dt;
		if(timeSinceLasteChange > 0.5f) {
			timeSinceLasteChange -= 0.5f;
			AnimatedTile = !AnimatedTile;
		}
	}

}

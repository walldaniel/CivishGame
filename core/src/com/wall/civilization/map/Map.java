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
		addOceans();

		plains = new Texture(Tile.PLAINS_PATH);
		hills = new Texture(Tile.HILLS_PATH);
		ocean = new Texture(Tile.OCEAN_PATH);
	}

	private void addOceans() {
		for (int i = rand.nextInt(10) + 1; i >= 0; i--) {
			oceanFill(rand.nextInt(tiles.length), rand.nextInt(tiles[0].length), 0f);
		}
	}

	private void oceanFill(int x, int y, double chance) {

		try {
			if (x > 0 || y > 0 || x < tiles.length - 1 || y < tiles[0].length - 1) {
				// If already ocean then ignore
				if (tiles[x][y].getTile() != TileType.OCEAN && rand.nextFloat() > chance) {
					tiles[x][y].setTile(TileType.OCEAN);

					oceanFill(x + 1, y, chance + Math.random() / 4f);
					oceanFill(x - 1, y, chance + Math.random() / 6f);
					oceanFill(x, y + 1, chance + Math.random() / 4f);
					oceanFill(x, y - 1, chance + Math.random() / 5f);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	public void drawMap(SpriteBatch sb, float posX, float posY, float zoom) {
		sb.begin();

		float currentX = posX, currentY = posY;
		for (Tile[] row : tiles) {
			for (Tile t : row) {

				switch (t.getTile()) {
				case PLAINS:
					sb.draw(plains, currentX, currentY, 64 * zoom, 64 * zoom);
					break;
				case HILLS:
					sb.draw(hills, currentX, currentY, 64 * zoom, 64 * zoom);
					break;
				case OCEAN:
					sb.draw(ocean, currentX, currentY, 64 * zoom, 64 * zoom);
					break;
				}
				currentX += Tile.WIDTH * zoom;

			}
			currentY += Tile.HEIGHT * zoom;
			currentX = posX;
		}

		sb.end();
	}

}

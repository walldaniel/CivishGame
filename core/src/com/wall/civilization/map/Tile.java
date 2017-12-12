package com.wall.civilization.map;

import java.util.Random;

public class Tile {

	public enum TileType {
		PLAINS, HILLS, OCEAN
	};

	public static final String PLAINS_PATH = "plains.png";
	public static final String HILLS_PATH = "hills.png";
	public static final String OCEAN_PATH = "ocean.png";

	public final static int WIDTH = 64;
	public final static int HEIGHT = 64;

	private TileType tile;

	public Tile(TileType tileType) {
		this.tile = TileType.PLAINS;
	}
	
	// Chance based on one above and below
	public Tile(Random rand ,TileType left, TileType down) {
		// Used to try and get tiles to be somewhat similar
		double plains = Math.random() / 1.5f;
		double hills = Math.random()/ 1.5f;
		
		// Check to the left of tile
		if(left == TileType.PLAINS)
			plains += Math.random();
		else
			hills += Math.random();
		
		// Check the up
		if(down == TileType.PLAINS)
			plains += Math.random();
		else
			hills += Math.random();
		
		if(plains > hills)
			tile = TileType.PLAINS;
		else
			tile = TileType.HILLS;
	}

	// Completely random tile, but not ocean, ocean gen is later
	public Tile(Random rand) {
		tile = TileType.values()[rand.nextInt(2)];
	}

	public TileType getTile() {
		return tile;
	}
	
	public void setTile(TileType tile) {
		this.tile = tile;
	}
}

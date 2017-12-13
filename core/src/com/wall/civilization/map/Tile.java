package com.wall.civilization.map;

import java.util.Random;

public class Tile {

	public enum TileType {
		PLAINS, HILLS, DESERT, OCEAN, COAST
	};

	public static final String PLAINS_PATH = "plains.png";
	public static final String HILLS_PATH = "hills.jpg";
	public static final String DESERT_PATH = "desert.png";
	public static final String OCEAN_ONE_PATH = "oceans1.png";
	public static final String OCEAN_TWO_PATH = "oceans2.png";
	public static final String COAST_PATH = "coast.png";


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
		double desert = Math.random() / 1.5f;
		
		// Check to the left of tile
		if(left == TileType.PLAINS)
			plains += Math.random();
		else if(left == TileType.HILLS)
			hills += Math.random();
		else
			desert += Math.random();
		
		// Check the up
		if(down == TileType.PLAINS)
			plains += Math.random();
		else if(down == TileType.HILLS)
			hills += Math.random();
		else
			desert += Math.random();
		
		if(plains > hills && plains > desert)
			tile = TileType.PLAINS;
		else if(hills > plains && hills > desert)
			tile = TileType.HILLS;
		else
			tile = TileType.DESERT;
	}

	// Completely random tile, but not ocean, ocean gen is later
	public Tile(Random rand) {
		tile = TileType.values()[rand.nextInt(TileType.values().length - 2)];
	}

	public TileType getTile() {
		return tile;
	}
	
	public void setTile(TileType tile) {
		this.tile = tile;
	}
}

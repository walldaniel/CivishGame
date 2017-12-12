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

	public Tile(Random rand) {
		tile = TileType.values()[rand.nextInt(TileType.values().length)];
	}

	public TileType getTile() {
		return tile;
	}
}

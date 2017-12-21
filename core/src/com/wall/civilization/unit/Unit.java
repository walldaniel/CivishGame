package com.wall.civilization.unit;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.wall.civilization.map.Map;
import com.wall.civilization.states.PlayState;

public class Unit {

	private int health;
	private int x, y;
	private String name;
	private Cell cell;
	private int movement, maxMovement;

	public Unit(String name, int x, int y, UnitBackend units, Map map) {
		this.x = x;
		this.y = y;
		this.name = name;
		health = 100; // Starts at 100, can go both up and down
		cell = new Cell();
		cell.setTile(new StaticTiledMapTile(units.getImage(name)));
		
		
	}

	public Cell getCell() {
		return cell;
	}

	public int getHealth() {
		return health;
	}

	// Reset all variables to their default at a new turn
	public void newTurn() {
		movement = maxMovement;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean moveLeft(Map map) {
		// Check to see if their is movement left
		if (movement <= 0 && x > 0 && map.checkLocation(x, y)) {
			return false;
		}

		// Remove unit from current location
		map.removeUnitLocation(x, y);

		// Change the variables
		x--;
		movement--;

		// Reput unit on correct tile
		map.setUnitLocation(x, y, cell);

		return true;
	}

	public boolean moveRight(Map map) {
		// Check to see if their is movement left
		if (movement <= 0 && x < PlayState.MAPSIZE - 1 && map.checkLocation(x, y)) {
			return false;
		}

		// Remove unit from current location
		map.removeUnitLocation(x, y);

		// Change the variables
		x++;
		movement--;

		// Reput unit on correct tile
		map.setUnitLocation(x, y, cell);

		return true;
	}

	public boolean moveUp(Map map) {
		// Check to see if their is movement left
		if (movement <= 0 && y < PlayState.MAPSIZE - 1 && map.checkLocation(x, y)) {
			return false;
		}

		// Remove unit from current location
		map.removeUnitLocation(x, y);

		// Change the variables
		y++;
		movement--;

		// Reput unit on correct tile
		map.setUnitLocation(x, y, cell);

		return true;
	}

	public boolean moveDown(Map map) {
		// Check to see if their is movement left, and not going off screen
		if (movement <= 0 && y > 0 && map.checkLocation(x, y)) {
			return false;
		}

		// Remove unit from current location
		map.removeUnitLocation(x, y);

		// Change the variables
		y--;
		movement--;

		// Reput unit on correct tile
		map.setUnitLocation(x, y, cell);

		return true;
	}
}

package com.wall.civilization.unit;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class Civilization {

	private Texture texture;
	
	private ArrayList<Unit> units;
	private String name;
	
	public Civilization(String name) {
		this.name = name;
		
		units = new ArrayList<Unit>();
		
		texture = new Texture("unit.png");
	}
	
	public void addUnit() {
		units.add(new Unit(0, 0));
	}
	
	// Search for units at a certain coord
	public Unit getUnit(int x, int y) {
		for(int i = units.size() - 1;i >= 0; i--) {
			if(units.get(i).getX() == x && units.get(i).getY() == y) 
				return units.get(i);
		}
		
		return null;	// If unit not found return null
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void drawCivilization() {
		
	}
}

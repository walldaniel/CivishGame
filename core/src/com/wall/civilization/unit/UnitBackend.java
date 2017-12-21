package com.wall.civilization.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UnitBackend {
	
	private static final int UNIT_IMAGE_SIZE = 128;

	private final String[] unit_names = { "worker", "warrior" };
	private Unit[] units;
	private Texture texture;
	
	public UnitBackend(String file) {
		// First load the texture
		texture = new Texture(file);
		
		units = new Unit[unit_names.length];
		
		for(int i = 0; i < unit_names.length; i++) {
			units[i] = new Unit(texture, unit_names[i], i);
		}
	}
	
	public TextureRegion getImage(String name) {
		// Find what the units index is
		int i;
		for(i = 0; i < units.length; i++) {
			if(units[i].name.equals(name))
				return units[i].getTextureRegion();
		}
		
		// If couldn't find the unit return null;
		return null;
	}
	
	public class Unit {
		// Used for getting each unit
		String name;
		int index;
		
		// Contains what the unit looks like
		TextureRegion region;
		
		public Unit(Texture texture, String name, int index) {
			this.name = name;
			this.index = index;

			region = new TextureRegion(texture, 0, index * UNIT_IMAGE_SIZE, UNIT_IMAGE_SIZE, UNIT_IMAGE_SIZE);
			region.flip(false, true); 	// Make sure that image is orientated right way
		}
		
		public TextureRegion getTextureRegion() {
			return region;
		}
	}
	
}

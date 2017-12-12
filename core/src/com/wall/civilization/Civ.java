package com.wall.civilization;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.map.Map;

public class Civ extends ApplicationAdapter {
	public static final int MAP_SIZE = 50;
	
	SpriteBatch sb;
	Map map;
	
	float x = 0;
	float y = 0;
	
	float mouseX = 0;
	float mouseY = 0;
	
	float zoom = 2f;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		
		map = new Map(MAP_SIZE, MAP_SIZE);
	}

	public void update(float dt) {
		// Arrow keys map move
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x += dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x -= dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			y -= dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			y += dt * 125f;
		}
		
		// Get mouse scrolling
		if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {	// The equals key is same as plus
			if(zoom > 0.2f)
				zoom -= 0.02f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			if(zoom < 2.5f)
				zoom += 0.02f;
		}
		
		// Get mouse movement
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			x += Gdx.input.getDeltaX() * 2.1f;
			y -= Gdx.input.getDeltaY() * 2.1f;
		}
		
		mouseX = Gdx.input.getX();
		mouseY = Gdx.input.getY();
	}
	
	@Override
	public void render () {
		// Clear the screen
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		// Get updates
		update(Gdx.graphics.getDeltaTime());
		
		// Draw the map
		map.drawMap(sb, x, y, zoom);
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}

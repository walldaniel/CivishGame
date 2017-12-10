package com.wall.civilization;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.map.Map;

public class Civ extends ApplicationAdapter {
	SpriteBatch sb;
	Map map;
	
	float x = 0;
	float y = 0;
	
	float mouseX = 0;
	float mouseY = 0;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		
		map = new Map(10, 10);
	}

	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x -= dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x += dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			y += dt * 125f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			y -= dt * 125f;
		}
		
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
		map.drawMap(sb, x, y);
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}

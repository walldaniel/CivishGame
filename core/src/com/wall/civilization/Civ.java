package com.wall.civilization;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.map.Map;

public class Civ extends ApplicationAdapter {
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 480;
	
	public static final int MAP_SIZE = 30;
	
	private OrthographicCamera cam;
	
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
		
		// Stuff for camera
		cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT * (SCREEN_WIDTH / SCREEN_HEIGHT));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
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
			if(cam.zoom > 0.15f)
				cam.zoom -= 0.03f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			if(cam.zoom < 4f)
				cam.zoom += 0.03f;
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
		
		sb.begin();
		// Get updates
		update(Gdx.graphics.getDeltaTime());
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		
		// Draw the map
		map.drawMap(sb, x, y, Gdx.graphics.getDeltaTime());
		
		sb.end();
	}
	
	@Override
	public void dispose () {
		sb.dispose();
	}
}

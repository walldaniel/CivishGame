package com.wall.civilization;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wall.civilization.states.GameStateManager;
import com.wall.civilization.states.PlayState;

public class Civ extends ApplicationAdapter {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;

	private GameStateManager gsm;
	private SpriteBatch sb;
	
	@Override
	public void create() {
		sb = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new PlayState(gsm));
	}
	
	@Override
	public void render() {
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		gsm.peek().update(Gdx.graphics.getDeltaTime());
		gsm.peek().render(sb);
	}
	
	@Override
	public void dispose() {
		sb.dispose();
	}
}

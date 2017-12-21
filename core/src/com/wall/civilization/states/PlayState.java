package com.wall.civilization.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wall.civilization.Player;
import com.wall.civilization.map.Map;
import com.wall.civilization.unit.Unit;
import com.wall.civilization.unit.UnitBackend;

public class PlayState extends State {

	private Map map;

	private Vector2 mouseCoords;
	private Vector3 mapCoords;

	private float mapVelocity = 250f;
	public static final int MAPSIZE = 25;

	private boolean pressed = false;

	private UnitBackend units;
	private Player player;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Create the map
		map = new Map(MAPSIZE, cam);

		// Create the objects that store the coords of mouse and map
		mouseCoords = new Vector2();
		mapCoords = new Vector3();

		// Add the list of the units
		units = new UnitBackend("units.png");	// TODO change the string to a constant
		
		// Add a player with one unit to the map
		player = new Player();
		player.addUnit(new Unit("worker", 0, 0, units));
	}

	@Override
	protected void handleInput(float dt) {
		// Only move with either keys or mouse at one time
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			// Translate the map by how much the mouse is moved
			cam.translate(-Gdx.input.getDeltaX() * cam.zoom, -Gdx.input.getDeltaY() * cam.zoom);
		} else {
			// TODO: Make sure user cannot go outside of map

			// Arrow keys move map
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				cam.translate(-mapVelocity * dt * cam.zoom, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				cam.translate(mapVelocity * dt * cam.zoom, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				cam.translate(0, mapVelocity * dt * cam.zoom);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				cam.translate(0, -mapVelocity * dt * cam.zoom);
			}

		}

		// Zooming
		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) { // The equals key is same as plus
			if (cam.zoom > 0.75f)
				cam.zoom -= 0.03f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			if (cam.zoom < 8f)
				cam.zoom += 0.03f;
		}
		// System.out.println(cam.position);
	}

	@Override
	public void update(float dt) {
		handleInput(dt); // First get input from person

		// Get the coordinates of the mouse
		mouse.x = mapCoords.x = Gdx.input.getX();
		mouse.y = mapCoords.y = Gdx.input.getY();

		// Get coordinates of where mouse is on map
		cam.unproject(mapCoords);

		if (!pressed && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			pressed = true;
			int tileX = (int) mapCoords.x / 128;
			int tileY = (int) mapCoords.y / 128;

			// Check to make sure that the mouse is on the map
			if (tileX >= 0 && tileX < MAPSIZE && tileY >= 0 && tileY < MAPSIZE) {
				map.highlightSquare(tileX, tileY);
				
				// Check if their is a unit on the tile
//				if(unit == null) {
//					unit = civ.getUnit(tileX, tileY);
//				} else {
//					map.changeUnitLocation(civ.getTexture(), unit.getX(), unit.getY(), tileX, tileY);
//					unit.setX(tileX);
//					unit.setY(tileY);
//					
//					// Unselect the unit
//					unit = null;
//				}
			}
		} else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			pressed = false;
		}
		// Print out the current tile the mouse is selected on
		// System.out.println("Tile: " + (int) (mapCoords.x / 128) + "\t- " + (int)
		// (mapCoords.y / 128));
	}
	
	@Override
	public void render(SpriteBatch sb) {
		cam.update();
		sb.setProjectionMatrix(cam.combined);

		// Draw the map
		map.drawMap(cam);
	}

	@Override
	public void dispose() {

	}

}

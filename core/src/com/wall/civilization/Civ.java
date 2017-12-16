package com.wall.civilization;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wall.civilization.map.Map;
import com.wall.civilization.map.Tile;

public class Civ extends ApplicationAdapter {
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 480;

	public static final int MAP_SIZE = 30;

	private OrthographicCamera cam;

	SpriteBatch sb;
	// Map map;

	float x = 0;
	float y = 0;

	private Vector2 mouseCoords;
	private Vector3 mapCoords;

	private TiledMap map;
	private MapLayers layers;
	private TiledMapTileLayer layer1;
	private Texture tiles;
	private TextureRegion plains, forest;
	private OrthogonalTiledMapRenderer renderer;

	@Override
	public void create() {
		sb = new SpriteBatch();

		// Stuff for camera
		cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT * (SCREEN_WIDTH / SCREEN_HEIGHT));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		tiles = new Texture(Tile.PLAINS_PATH);
		plains = new TextureRegion(tiles, 0, 0, 128, 128);
		forest = new TextureRegion(tiles, 0, 128, 128, 128);
		map = new TiledMap();
		layers = map.getLayers();
		layer1 = new TiledMapTileLayer(30, 30, 128, 128);

		TiledMapTileLayer.Cell[] cells = { new TiledMapTileLayer.Cell(), new TiledMapTileLayer.Cell() };
		cells[0].setTile(new StaticTiledMapTile(plains));
		cells[1].setTile(new StaticTiledMapTile(forest));
		layer1.setCell(0, 1, cells[0]);
		for (int i = 1; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				layer1.setCell(i, j, cells[(int) (Math.random() * cells.length)]);
			}
		}

		layers.add(layer1);

		renderer = new OrthogonalTiledMapRenderer(map);
		renderer.setView(cam);
		
		mouseCoords = new Vector2();
		mapCoords = new Vector3();
		// map = new Map(MAP_SIZE, MAP_SIZE);
	}

//	@Override
//	public boolean mouseMoved(int screenX, int screenY) {
//
//	    //screenX, screenY - Mouse Coords
//
//	    Vector2 centerPosition = new Vector2(mouseX, mouseY));
//
//	    Vector3 worldCoordinates = new Vector3(screenX, y, screenY,0);
//	    cam.unproject(worldCoordinates);
//
//	    Vector2 mouseLoc = new Vector2(worldCoordinates.x, worldCoordinates.y);
//
//	    Vector2 direction = mouseLoc.sub(centerPosition);
//	    float mouseAngle  = direction.angle();
//	    setRotation(mouseAngle);
//
//	    return true;
//	}
	
	public void update(float dt) {
		// Get map movement, if mouse is activated don't let arrow keys move map
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			cam.translate(-Gdx.input.getDeltaX() * 2f * cam.zoom, -Gdx.input.getDeltaY() * 2f * cam.zoom);
		} else {
			// Arrow keys map move
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				cam.translate(-150f * dt * cam.zoom, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				cam.translate(150f * dt * cam.zoom, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				cam.translate(0, -150f * dt * cam.zoom);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				cam.translate(0, 150f * dt * cam.zoom);
			}
		}

		// Get zoom feature
		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS)) { // The equals key is same as plus
			if (cam.zoom > 1f)
				cam.zoom -= 0.03f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
			if (cam.zoom < 50f)
				cam.zoom += 0.03f;
		}

		mouseCoords.x = Gdx.input.getX();
		mouseCoords.y = Gdx.input.getY();
		
		mapCoords.x = mouseCoords.x;
		mapCoords.y = mouseCoords.y;
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		// Print out mouse coords
//		System.out.print("Tile: " + (int) ((mouseX + (cam.position.x - 300) - (64f * (cam.zoom - 1f))) / (128 / cam.zoom))
//				+ ", " + (int) ((mouseY + (cam.position.y -240) - (128f * (cam.zoom - 1f))) / (128 / cam.zoom)));
//
//		System.out.println("\t- " + cam.zoom + "\t- " + mouseX + "\t- " + mouseY + "\t- ");

		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		cam.update();
		renderer.setView(cam);
		renderer.render();
		cam.unproject(mapCoords);
		
		System.out.println("Tile: " + (int) (mapCoords.x / 128) + "\t- " + (int) (mapCoords.y / 128));

	}

	@Override
	public void dispose() {
		sb.dispose();
	}
}

package com.wall.civilization.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wall.civilization.Civ;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60;
		config.width = Civ.SCREEN_WIDTH;
		config.height = Civ.SCREEN_HEIGHT;
		new LwjglApplication(new Civ(), config);
	}
}

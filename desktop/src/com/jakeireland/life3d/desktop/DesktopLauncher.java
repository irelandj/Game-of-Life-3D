package com.jakeireland.life3d.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jakeireland.life3d.Life;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		int tileSize = 20;
		int xLength = 800/20;
		int yLength = 800/20;
		int zLength = 800/20;
		config.width = 800;
		config.height = 800;
		config.fullscreen = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;

		new LwjglApplication(new Life(tileSize, xLength, yLength, zLength), config);
	}
}

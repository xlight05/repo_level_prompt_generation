package de.exobros.exogdx.desktop;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopUtils {
	public static void startGame(ApplicationListener app, String title) {

		AppConfig cfg = new AppConfig("config.ini");
		try {
			cfg.load();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				cfg.save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		new LwjglApplication(app, cfg.getLwjglConfig(title));
	}
}

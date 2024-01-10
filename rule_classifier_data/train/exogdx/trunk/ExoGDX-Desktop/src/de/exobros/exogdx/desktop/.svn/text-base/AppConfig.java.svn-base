package de.exobros.exogdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class AppConfig extends Config {
	
	public AppConfig(String path) {
		super(path);	

		this.setProperty("xRes", "800");
		this.setProperty("yRes", "600");
		this.setProperty("fullscreen", "false");
		this.setProperty("vSync", "true");
		this.setProperty("multisamples", "2");
	}
	
	public int getXRes() {
		return Integer.valueOf(this.getProperty("xRes"));
	}

	public int getYRes() {
		return Integer.valueOf(this.getProperty("yRes"));
	}

	public boolean getVSync() {
		return Boolean.valueOf(this.getProperty("vSync"));
	}

	public boolean getFullscreen() {
		return Boolean.valueOf(this.getProperty("fullscreen"));
	}

	public int getMultisamples() {
		return Integer.valueOf(this.getProperty("multisamples"));
	}

	public void setXRes(int xRes) {
		this.setProperty("xRes", xRes + "");
	}

	public void setYRes(int yRes) {
		this.setProperty("yRes", yRes + "");
	}

	public void setVSync(boolean vSync) {
		this.setProperty("vSync", vSync + "");
	}

	public void setFullscreen(boolean fullscreen) {
		this.setProperty("fullscreen", fullscreen + "");
	}

	public void setMultisamples(int multisamples) {
		this.setProperty("multisamples", multisamples + "");
	}

	public LwjglApplicationConfiguration getLwjglConfig(String applicationTitle) {
		try {
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

			cfg.useGL20 = true;
			cfg.resizable = false;
			cfg.fullscreen = this.getFullscreen();
			cfg.width = this.getXRes();
			cfg.height = this.getYRes();
			cfg.vSyncEnabled = this.getVSync();
			cfg.samples = this.getMultisamples();
			cfg.title = applicationTitle;

			return cfg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

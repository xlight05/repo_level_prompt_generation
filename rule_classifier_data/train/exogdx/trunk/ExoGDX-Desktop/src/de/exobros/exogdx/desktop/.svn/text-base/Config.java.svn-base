package de.exobros.exogdx.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Config extends Properties {

	public String path;

	public Config(String path) {
		this.path = path;
	}

	public void load() throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			this.load(fis);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e2) {

			}
		}
	}

	public void save() throws IOException {
		OutputStream out = null;
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.delete();
			}
			f.createNewFile();
			out = new FileOutputStream(path);
			this.store(out, null);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void create() {
		try {
			this.load();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				this.save();
			} catch (IOException e1) {
				e1.printStackTrace();

			}
		}
	}
}

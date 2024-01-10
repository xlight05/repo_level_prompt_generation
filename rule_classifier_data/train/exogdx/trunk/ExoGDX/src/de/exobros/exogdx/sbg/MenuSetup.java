package de.exobros.exogdx.sbg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Exobros The Settings for Setting up a MenuState
 */
public class MenuSetup {

	/**
	 * The Width of the Buttons in percentage of the Screen. (f.e. 0.5f means
	 * half the Screen)
	 */
	public float width;
	/**
	 * The Height of the Buttons altogether in percentage of the Screen. (f.e.
	 * 0.5f means all the Buttons are distributed along half of the Screen
	 */
	public float height;
	/**
	 * The padding between the Buttons
	 */
	public int padding;
	/**
	 * The Background Texture
	 */
	public Texture background;

	/**
	 * Creates a Default MenuSetup (width = 0.5f, height = 0.5f, padding = 5,
	 * backgroundTexture: WHITE)
	 */
	public MenuSetup() {
		this.width = 0.5f;
		this.height = 0.5f;
		this.padding = 5;
		Pixmap pm = new Pixmap(1, 1, Format.RGBA8888);
		pm.setColor(Color.WHITE);
		pm.drawPixel(0, 0);
		background = new Texture(pm);
	}

	/**
	 * Creates a MenuSetup with the specific values
	 * 
	 * @param width
	 *            The Width of the Buttons in percentage of the Screen. (f.e.
	 *            0.5f means half the Screen)
	 * @param height
	 *            The Height of the Buttons altogether in percentage of the
	 *            Screen. (f.e. 0.5f means all the Buttons are distributed along
	 *            half of the Screen
	 * @param padding
	 *            The padding between the Buttons
	 * @param background
	 *            The Background Texture
	 */
	public MenuSetup(float width, float height, int padding, Texture background) {
		this.width = width;
		this.height = height;
		this.padding = padding;
		this.background = background;
	}
}
